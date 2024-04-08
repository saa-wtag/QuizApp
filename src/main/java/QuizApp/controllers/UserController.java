package QuizApp.controllers;

import javax.validation.Valid;
import QuizApp.model.user.User;
import QuizApp.model.user.UserInput;
import QuizApp.model.user.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import QuizApp.quizObjectMapper.QuizObjectMapper;
import QuizApp.services.user.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserInput userInput) {
        User user = QuizObjectMapper.convertUserInputToModel(userInput);
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        User user = userService.getUser(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> editUserDetails(@PathVariable int userId, @Valid @RequestBody UserUpdate updatedUser) {
        User user = QuizObjectMapper.convertUserUpdateToModel(updatedUser);
        User editedUser = userService.updateUserDetails(userId, user);
        return ResponseEntity.ok(editedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}

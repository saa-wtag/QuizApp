package QuizApp.services.user;


import QuizApp.model.user.User;
import QuizApp.repositories.UserRepository;
import QuizApp.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        user.setRole(User.UserRole.USER);
        return userRepository.save(user);
    }

    @Override
    public User updateUserDetails(int userId, User user) {
        User existingUser = getUser(userId);

        if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
            existingUser.setUserName(user.getUserName());
        }
        if (user.getUserEmail() != null && !user.getUserEmail().trim().isEmpty()) {
            existingUser.setUserEmail(user.getUserEmail());
        }
        if (user.getUserPassword() != null && !user.getUserPassword().trim().isEmpty()) {
            existingUser.setUserPassword(user.getUserPassword());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User getUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        if(Objects.isNull(user)){
            throw new NoSuchElementException("There is no such user!");
        }
        user.getQuizzes().size();
//        In the getUser method, after retrieving the User object from the repository, you can call user.getQuizzes().size() to initialize the quizzes collection.
//        This forces Hibernate to fetch the quizzes collection from the database within the transactional boundary, ensuring that the necessary data is loaded
//        before the session is closed.
//        By initializing the collection within the transactional method, you avoid the lazy loading exception when serializing the User object to JSON.
        return user;
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new NoSuchElementException("User not found with ID: " + userId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName);
    }

}

package QuizApp.services.user;


import QuizApp.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import QuizApp.repositories.user.UserDao;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User registerUser(User user) {
        user.setRole(User.UserRole.USER);
        return userDao.saveUser(user);
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

        return userDao.saveUser(existingUser);
    }

    @Override
    public User getUser(int userId) {
        User user = userDao.getUser(userId);;
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
        User user = userDao.getUser(userId);

        if (user != null) {
            userDao.deleteUser(user);
        } else {
            throw new NoSuchElementException("User not found with ID: " + userId);
        }
    }

}

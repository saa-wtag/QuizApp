package QuizApp.repositories.user;

import QuizApp.model.user.User;

public interface UserDao {
    User saveUser(User user);
    User getUser(int userId);
    void deleteUser(User user);
}

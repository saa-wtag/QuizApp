package QuizApp.services.user;

import QuizApp.model.user.User;

public interface UserService {
    User registerUser(User user);
    User updateUserDetails(int userId, User user);
    User getUser(int userId);
    void deleteUser(int userId);
}

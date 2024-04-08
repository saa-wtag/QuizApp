package QuizApp.repositories.quiz;

import QuizApp.model.quiz.Quiz;

import java.util.List;

public interface QuizDao {
    Quiz saveQuiz(Quiz quiz);
    Quiz getQuiz(int quizId);
    List<Quiz> findQuizzesByUserId(int userId);
    void deleteQuiz(int quizId);
}

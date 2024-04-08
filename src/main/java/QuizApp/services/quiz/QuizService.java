package QuizApp.services.quiz;

import QuizApp.model.quiz.Quiz;

import java.util.List;

public interface QuizService {
    Quiz createQuiz(int userId);
    Quiz getQuiz(int quizId);
    List<Quiz> listQuizzesForUser(int userId);
    Quiz submitAnswers(int quizId, List<Integer> answerIds);

    void deleteQuiz(int quizId);
}

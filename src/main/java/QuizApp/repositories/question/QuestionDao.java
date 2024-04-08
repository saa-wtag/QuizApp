package QuizApp.repositories.question;

import QuizApp.model.question.Question;

import java.util.List;

public interface QuestionDao {
    Question saveQuestion(Question question);
    Question getQuestion(int questionId);
    void deleteQuestion(Question question);
    List<Question> getRandomQuestions();
}

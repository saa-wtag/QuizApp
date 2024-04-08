package QuizApp.services.quiz;


import QuizApp.model.question.Question;
import QuizApp.model.quiz.Quiz;
import QuizApp.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import QuizApp.repositories.quiz.QuizDao;
import QuizApp.services.question.QuestionService;
import QuizApp.services.user.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizServiceImpl implements QuizService{
    private final QuizDao quizDao;
    private final UserService userService;
    private final QuestionService questionService;

    @Autowired
    public QuizServiceImpl(QuizDao quizDao, UserService userService, QuestionService questionService) {
        this.quizDao = quizDao;
        this.userService = userService;
        this.questionService = questionService;
    }
    @Override
    public Quiz createQuiz(int userId) {
        User user = userService.getUser(userId);
        List<Question> questions = questionService.getRandomQuestionsForQuiz();
        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setQuestions(questions);

        return quizDao.saveQuiz(quiz);
    }

    @Override
    public Quiz getQuiz(int quizId) {
        return quizDao.getQuiz(quizId);
    }

    @Override
    public List<Quiz> listQuizzesForUser(int userId) {

        return quizDao.findQuizzesByUserId(userId);
    }

    @Override
    public Quiz submitAnswers(int quizId, List<Integer> answerIds) {
        Quiz quiz = Optional.ofNullable(quizDao.getQuiz(quizId))
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found."));

        long score = quiz.getQuestions().stream()
                .flatMap(question -> question.getOptions().stream())
                .filter(option -> answerIds.contains(option.getOptionId()) && option.isIfCorrect())
                .count();

        quiz.setScore(score/4);
        return quizDao.saveQuiz(quiz);
    }

    @Override
    public void deleteQuiz(int quizId) {
        quizDao.deleteQuiz(quizId);
    }
}

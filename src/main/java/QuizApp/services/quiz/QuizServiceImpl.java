package QuizApp.services.quiz;


import QuizApp.exceptions.ObjectNotFoundException;
import QuizApp.model.question.Question;
import QuizApp.model.quiz.Quiz;
import QuizApp.model.user.User;
import QuizApp.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import QuizApp.services.question.QuestionService;
import QuizApp.services.user.UserService;

import java.util.List;


@Service
@Transactional
public class QuizServiceImpl implements QuizService{
    private final UserService userService;
    private final QuestionService questionService;
    private final QuizRepository quizRepository;

    @Autowired
    public QuizServiceImpl(UserService userService, QuestionService questionService, QuizRepository quizRepository) {

        this.userService = userService;
        this.questionService = questionService;
        this.quizRepository = quizRepository;
    }
    @Override
    public Quiz createQuiz(int userId) {
        User user = userService.getUser(userId);
        List<Question> questions = questionService.getRandomQuestionsForQuiz();
        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setQuestions(questions);

        return quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuiz(int quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new ObjectNotFoundException("Quiz not found"));
    }

    @Override
    public List<Quiz> listQuizzesForUser(int userId) {

        return quizRepository.findByUserUserId(userId);
    }

    @Override
    public Quiz submitAnswers(int quizId, List<Integer> answerIds) {
        Quiz quiz = getQuiz(quizId);

        long score = quiz.getQuestions().stream()
                .flatMap(question -> question.getOptions().stream())
                .filter(option -> answerIds.contains(option.getOptionId()) && option.isIfCorrect())
                .count();

        quiz.setScore(score/4);
        return quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(int quizId) {
        quizRepository.deleteById(quizId);
    }
}

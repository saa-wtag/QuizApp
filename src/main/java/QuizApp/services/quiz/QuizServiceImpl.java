package QuizApp.services.quiz;


import QuizApp.exceptions.ObjectNotFoundException;
import QuizApp.model.question.Question;
import QuizApp.model.quiz.Quiz;
import QuizApp.model.user.User;
import QuizApp.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public boolean isUserQuizOwner(int quizId, int userId) {
        return quizRepository.findById(quizId)
                .map(quiz -> quiz.getUser().getUserId() == userId)
                .orElse(false);
    }
    @Override
    @PreAuthorize("#userId == principal.id")
    public Quiz createQuiz(int userId) {
        User user = userService.getUser(userId);
        List<Question> questions = questionService.getRandomQuestionsForQuiz();
        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setQuestions(questions);

        return quizRepository.save(quiz);
    }

    @Override
    @PreAuthorize("@QuizServiceImpl.isUserQuizOwner(#quizId, principal.userId)")
    public Quiz getQuiz(int quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new ObjectNotFoundException("Quiz not found"));
    }

    @Override
    @PreAuthorize("#userId == principal.id or hasRole('ADMIN')")
    public List<Quiz> listQuizzesForUser(int userId) {

        return quizRepository.findByUserUserId(userId);
    }

    @Override
    @PreAuthorize("@QuizServiceImpl.isUserQuizOwner(#quizId, principal.userId)")
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
    @PreAuthorize("#userId == principal.id or hasRole('ADMIN')")
    public void deleteQuiz(int quizId) {
        quizRepository.deleteById(quizId);
    }
}

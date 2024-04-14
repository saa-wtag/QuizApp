package QuizApp.controllers;

import javax.validation.Valid;

import QuizApp.model.quiz.AnswerInput;
import QuizApp.model.quiz.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import QuizApp.services.quiz.QuizService;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Quiz> createQuiz(@PathVariable int userId) {
        Quiz quiz = quizService.createQuiz(userId);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int quizId) {
        Quiz quiz = quizService.getQuiz(quizId);
        if (quiz != null) {
            return ResponseEntity.ok(quiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Quiz>> listQuizzesForUser(@PathVariable int userId) {
        List<Quiz> quizzes = quizService.listQuizzesForUser(userId);
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping("/submit/{quizId}")
    public ResponseEntity<Quiz> submitAnswers(@PathVariable int quizId, @Valid @RequestBody AnswerInput answers) {
        Quiz quiz = quizService.submitAnswers(quizId, answers.getAnswerIds());
        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable int quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }
}

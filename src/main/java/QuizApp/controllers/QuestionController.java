package QuizApp.controllers;

import javax.validation.Valid;
import QuizApp.model.question.Question;
import QuizApp.model.question.QuestionInput;
import QuizApp.model.question.QuestionUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import QuizApp.quizObjectMapper.QuizObjectMapper;
import QuizApp.services.question.QuestionService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @PostMapping("/")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionInput questionInput) {
        Question question = QuizObjectMapper.convertQuestionInputToModel(questionInput);
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.ok().body(createdQuestion);
    }


    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestionDetails(@PathVariable int questionId, @Valid @RequestBody QuestionUpdate questionUpdate) {
        Question question = QuizObjectMapper.convertQuestionUpdateToModel(questionUpdate);
        Question updatedQuestion = questionService.updateQuestionDetails(questionId, question);
        return ResponseEntity.ok().body(updatedQuestion);
    }


    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestion(@PathVariable int questionId) {
        Question question = questionService.getQuestion(questionId);
        return ResponseEntity.ok(question);
    }


    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

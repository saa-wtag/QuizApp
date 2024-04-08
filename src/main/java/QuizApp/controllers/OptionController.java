package QuizApp.controllers;


import QuizApp.model.option.Option;
import QuizApp.model.option.OptionInput;
import QuizApp.model.option.OptionUpdate;
import QuizApp.model.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import QuizApp.services.option.OptionService;
import QuizApp.services.question.QuestionService;
import QuizApp.quizObjectMapper.QuizObjectMapper;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/options")
public class OptionController {
    private final QuestionService questionService;
    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService, QuestionService questionService) {
        this.optionService = optionService;
        this.questionService = questionService;
    }

    @PostMapping("/")
    public ResponseEntity<Option> setOption(@RequestBody @Valid OptionInput optionInput) {
        Question question = questionService.getQuestion(optionInput.getQuestionId());
        Option option = QuizObjectMapper.convertOptionInputToModel(optionInput);
        option.setQuestion(question);
        Option savedOption = optionService.saveOption(option);
        return ResponseEntity.ok(savedOption);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Option>> getOptionsByQuestionId(@PathVariable int questionId) {
        List<Option> options = optionService.getOptionsByQuestionId(questionId);

        return ResponseEntity.ok(options);
    }

    @PutMapping("/{optionId}")
    public ResponseEntity<Option> updateOption(@PathVariable int optionId, @Valid @RequestBody OptionUpdate optionUpdate) {
        Question question;
        if(optionUpdate.getQuestionId()>0){
            question = questionService.getQuestion(optionUpdate.getQuestionId());

        }
        else{
            question = optionService.getQuestionByOptionId(optionId);
        }
        optionUpdate.setQuestion(question);
        Option updatedOption = optionService.updateOption(optionId, QuizObjectMapper.convertOptionUpdateToModel(optionUpdate));
        return ResponseEntity.ok(updatedOption);
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable int optionId) {
        optionService.deleteOption(optionId);
        return ResponseEntity.noContent().build();
    }
}

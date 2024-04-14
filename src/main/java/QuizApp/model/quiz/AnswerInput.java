package QuizApp.model.quiz;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class AnswerInput {

    @Size(min = 5, max = 5, message = "Exactly 5 answers must be provided")
    private List<Integer> answerIds;

}

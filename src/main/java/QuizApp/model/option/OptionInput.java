package QuizApp.model.option;

import org.hibernate.validator.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import QuizApp.model.question.Question;

@Getter
@Setter
public class OptionInput {

    @NotBlank(message = "Option details must be provided")
    private String optionDetails;

    @NotBlank(message = "Option's correctness must be provided")
    private boolean ifCorrect;

    @NotBlank(message = "Option's question id must be provided")
    private int questionId;

    private Question question;
}

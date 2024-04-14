package QuizApp.model.option;

import org.hibernate.validator.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import QuizApp.model.question.Question;


import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OptionInput {

    @NotBlank(message = "Option details must be provided")
    private String optionDetails;

    @NotNull(message = "Option's correctness must be provided")
    private boolean ifCorrect;

    @NotNull(message = "Option's question id must be provided")
    private Integer questionId;

    private Question question;
}

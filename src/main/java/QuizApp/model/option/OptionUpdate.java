package QuizApp.model.option;

import lombok.Getter;
import lombok.Setter;
import QuizApp.model.question.Question;

@Getter
@Setter
public class OptionUpdate {

    private String optionDetails;
    private boolean ifCorrect;
    private int questionId;
    private Question question;
}


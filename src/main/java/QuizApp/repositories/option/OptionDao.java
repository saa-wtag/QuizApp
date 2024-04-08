package QuizApp.repositories.option;

import QuizApp.model.option.Option;
import QuizApp.model.question.Question;

import java.util.List;

public interface OptionDao {
    Option saveOption(Option option);
    Option getOption(int optionId);
    List<Option> getOptionsByQuestionId(int questionId);
    void deleteOption(Option option);

    Question getQuestionByOptionId(int optionId);
}

package QuizApp.services.option;

import QuizApp.model.option.Option;
import QuizApp.model.question.Question;

import java.util.List;

public interface OptionService {
    List<Option> getOptionsByQuestionId(int questionId);

    Option saveOption(Option option);
    Option updateOption(int optionId, Option option);
    void deleteOption(int optionId);

    Question getQuestionByOptionId(int optionId);
}

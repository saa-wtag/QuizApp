package QuizApp.services.option;


import QuizApp.model.option.Option;
import QuizApp.model.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import QuizApp.repositories.option.OptionDao;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class OptionServiceImpl implements OptionService{
    private final OptionDao optionDao;

    @Autowired
    public OptionServiceImpl(OptionDao optionDao) {
        this.optionDao = optionDao;
    }

    @Override
    public List<Option> getOptionsByQuestionId(int questionId) {
        List<Option> options = optionDao.getOptionsByQuestionId(questionId);
        for (Option option : options) {
            option.getQuestion().getQuesId();
        }
        return options;
    }

    @Override
    public Option saveOption(Option option) {
        return optionDao.saveOption(option);
    }

    @Override
    public Option updateOption(int optionId, Option option) {
        Option existingOption = optionDao.getOption(optionId);
        if (existingOption != null) {
            if (option.getOptionDetails() != null && !option.getOptionDetails().trim().isEmpty()) {
                existingOption.setOptionDetails(option.getOptionDetails());
            }
            if (option.getQuestion() != null) {
                existingOption.setQuestion(option.getQuestion());
            }
            existingOption.setIfCorrect(option.isIfCorrect());
            return optionDao.saveOption(existingOption);
        } else {
            throw new NoSuchElementException("Option not found with ID: " + optionId);
        }
    }

    @Override
    public void deleteOption(int optionId) {
        Option option = optionDao.getOption(optionId);
        if (option != null) {
            optionDao.deleteOption(option);
        } else {
            throw new NoSuchElementException("Option not found with ID: " + optionId);
        }
    }

    @Override
    public Question getQuestionByOptionId(int optionId) {
        return optionDao.getQuestionByOptionId(optionId);
    }
}

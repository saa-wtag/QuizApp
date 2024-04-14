package QuizApp.services.option;


import QuizApp.exceptions.ObjectNotFoundException;
import QuizApp.model.option.Option;
import QuizApp.model.question.Question;
import QuizApp.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class OptionServiceImpl implements OptionService{
    private final OptionRepository optionRepository;

    @Autowired
    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public List<Option> getOptionsByQuestionId(int questionId) {
        List<Option> options = optionRepository.findByQuestionQuesId(questionId);
        for (Option option : options) {
            option.getQuestion().getQuesId();
        }
        return options;
    }

    @Override
    public Option saveOption(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public Option updateOption(int optionId, Option option) {
        Option existingOption = optionRepository.findById(optionId).orElseThrow(() -> new ObjectNotFoundException("Option not found"));;
        if (existingOption != null) {
            if (option.getOptionDetails() != null && !option.getOptionDetails().trim().isEmpty()) {
                existingOption.setOptionDetails(option.getOptionDetails());
            }
            if (option.getQuestion() != null) {
                existingOption.setQuestion(option.getQuestion());
            }
            existingOption.setIfCorrect(option.isIfCorrect());
            return optionRepository.save(existingOption);
        } else {
            throw new NoSuchElementException("Option not found with ID: " + optionId);
        }
    }

    @Override
    public void deleteOption(int optionId) {
        Option option = optionRepository.findById(optionId).orElseThrow(() -> new ObjectNotFoundException("Option not found"));;
        if (option != null) {
            optionRepository.delete(option);
        } else {
            throw new NoSuchElementException("Option not found with ID: " + optionId);
        }
    }

    @Override
    public Question getQuestionByOptionId(int optionId) {
        return optionRepository.findQuestionByOptionId(optionId);
    }
}

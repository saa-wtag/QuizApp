package QuizApp.services.question;


import QuizApp.exceptions.ObjectNotFoundException;
import QuizApp.model.question.Question;
import QuizApp.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestionDetails(int questionId, Question question) {
        Question dbQuestion = getQuestion(questionId);
        if (Objects.nonNull(dbQuestion)) {
            dbQuestion.setQuesDetails(question.getQuesDetails());
            dbQuestion.getOptions().forEach(option -> {
            });
            return questionRepository.save(dbQuestion);
        } else {
            throw new NoSuchElementException("There is no such question!");
        }
    }
    @Override
    public Question getQuestion(int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ObjectNotFoundException("Question not found"));;
        if (Objects.isNull(question)) {
            throw new NoSuchElementException("There is no such question!");
        }
        question.getOptions().size();
        return question;
    }

    @Override
    public void deleteQuestion(int questionId) {
        Question question = getQuestion(questionId);
        if (question != null) {
            questionRepository.delete(question);
        } else {
            throw new NoSuchElementException("Question not found with ID: " + questionId);
        }
    }

    @Override
    public List<Question> getRandomQuestionsForQuiz() {
        return questionRepository.findRandomQuestions();
    }
}

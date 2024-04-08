package QuizApp.services.question;


import QuizApp.model.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import QuizApp.repositories.question.QuestionDao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{
    private final QuestionDao questionDao;

    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question createQuestion(Question question) {
        return questionDao.saveQuestion(question);
    }

    //    @Override
//    public Question updateQuestionDetails(int questionId, Question question) {
//        Question dbQuestion = questionDao.getQuestion(questionId);
//        if (Objects.nonNull(dbQuestion)) {
//            dbQuestion.setQuesDetails(question.getQuesDetails());
//            return questionDao.saveQuestion(dbQuestion);
//        } else {
//            throw new NoSuchElementException("There is no such question!");
//        }
//    }
//
    @Override
    public Question updateQuestionDetails(int questionId, Question question) {
        Question dbQuestion = questionDao.getQuestion(questionId);
        if (Objects.nonNull(dbQuestion)) {
            dbQuestion.setQuesDetails(question.getQuesDetails());
            dbQuestion.getOptions().forEach(option -> {
            });
            return questionDao.saveQuestion(dbQuestion);
        } else {
            throw new NoSuchElementException("There is no such question!");
        }
    }
    @Override
    public Question getQuestion(int questionId) {
        Question question = questionDao.getQuestion(questionId);
        if (Objects.isNull(question)) {
            throw new NoSuchElementException("There is no such question!");
        }
        question.getOptions().size();
        return question;
    }

    @Override
    public void deleteQuestion(int questionId) {
        Question question = questionDao.getQuestion(questionId);
        if (question != null) {
            questionDao.deleteQuestion(question);
        } else {
            throw new NoSuchElementException("Question not found with ID: " + questionId);
        }
    }

    @Override
    public List<Question> getRandomQuestionsForQuiz() {
        return questionDao.getRandomQuestions();
    }
}

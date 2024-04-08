package QuizApp.repositories.question;

import QuizApp.model.question.Question;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class QuestionDaoImpl implements QuestionDao{
    private final SessionFactory sessionFactory;
    private Logger logger;

    @Autowired
    public QuestionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @Override
    public Question saveQuestion(Question question) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(question);
            return question;
        } catch (HibernateException e) {
            logger.info("Question saving operation failed");
            throw new HibernateException(e);
        }
    }

    @Override
    public Question getQuestion(int questionId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Question.class, questionId);
        } catch (HibernateException e) {
            logger.info("Question search failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteQuestion(Question question) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(question);
        } catch (HibernateException e) {
            logger.info("Question deletion failed!");
            throw new HibernateException(e);
        }
    }

    @Override
    public List<Question> getRandomQuestions() {
        Session session = sessionFactory.getCurrentSession();
        Query<Question> query = session.createQuery("FROM Question ORDER BY RAND()", Question.class);
        query.setMaxResults(5);
        return query.list();
    }
}

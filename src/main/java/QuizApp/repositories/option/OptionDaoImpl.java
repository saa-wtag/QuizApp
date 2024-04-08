package QuizApp.repositories.option;

import QuizApp.model.option.Option;
import QuizApp.model.question.Question;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class OptionDaoImpl implements OptionDao{
    private final SessionFactory sessionFactory;
    private Logger logger;


    public OptionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @Override
    public Option saveOption(Option option) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(option);
            return option;
        } catch (HibernateException e) {
            logger.info("Option saving operation failed");
            throw new HibernateException(e);
        }
    }

    @Override
    public Option getOption(int optionId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Option.class, optionId);
    }
    @Override
    public List<Option> getOptionsByQuestionId(int questionId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "FROM Option o WHERE o.question.quesId = :questionId";
            Query query = session.createQuery(hql);
            query.setParameter("questionId", questionId);
            return query.list();
        } catch (Exception e) {
            logger.info("Failed to retrieve options for question ID: " + questionId);
            throw new RuntimeException("Failed to retrieve options", e);
        }
    }

    @Override
    public void deleteOption(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(option);
    }


    @Override
    public Question getQuestionByOptionId(int optionId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT o.question FROM Option o WHERE o.id = :optionId";
            Query<Question> query = session.createQuery(hql, Question.class);
            query.setParameter("optionId", optionId);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.info("Failed to retrieve question for option ID: " + optionId);
            throw new RuntimeException("Failed to retrieve question", e);
        }
    }

}

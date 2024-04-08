package QuizApp.repositories.user;

import QuizApp.model.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;
@Repository
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;
    private Logger logger;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User saveUser(User user) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(user);
            return user;
        }
        catch (HibernateException e)
        {
            logger.info("User registration failed!");
            throw new HibernateException(e);
        }
    }

    @Override
    public User getUser(int userId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(User.class, userId);
        } catch (HibernateException e) {
            logger.info("User search failed!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(user);
        } catch (HibernateException e) {
            logger.info("User deletion failed!");
            throw new HibernateException(e);
        }
    }
}

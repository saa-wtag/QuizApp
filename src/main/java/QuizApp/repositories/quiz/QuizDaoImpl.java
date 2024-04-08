package QuizApp.repositories.quiz;

import QuizApp.model.quiz.Quiz;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class QuizDaoImpl implements QuizDao{
    private final SessionFactory sessionFactory;

    @Autowired
    public QuizDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(quiz);
        return quiz;
    }

    @Override
    public Quiz getQuiz(int quizId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Quiz.class, quizId);
    }

    @Override
    public List<Quiz> findQuizzesByUserId(int userId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Quiz> query = session.createQuery("FROM Quiz q WHERE q.user.id = :userId", Quiz.class);
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public void deleteQuiz(int quizId) {
        Session session = sessionFactory.getCurrentSession();
        Quiz quiz = session.byId(Quiz.class).load(quizId);
        if (quiz != null) {
            session.delete(quiz);
        }
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = getSessionFactory();
    private  String sql;

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(64) NOT NULL, lastName VARCHAR(64) NOT NULL, " +
                "age TINYINT NOT NULL)";

        Query query = session.createSQLQuery(sql).addEntity(User.class);

        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        sql = "DROP TABLE IF EXISTS user";

        Query query = session.createSQLQuery(sql).addEntity(User.class);

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));
            transaction.commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        sql = "DELETE FROM database.user where id";

        Query query = session.createSQLQuery(sql).addEntity(User.class);
        System.out.println("Пользователь с id " + id + " удален.");
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {

        List<User> allUser = new ArrayList<>();
        try (final Session session = Util.getSessionFactory().openSession()) {
            allUser = (List<User>) session.createQuery("FROM User").list();
            System.out.println("Лист создан");
        }
        return allUser;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE user;";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Таблица user очищена");
    }
}

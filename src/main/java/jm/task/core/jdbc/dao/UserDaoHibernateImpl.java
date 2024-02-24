package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private static Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User (id int AUTO_INCREMENT PRIMARY KEY, " +
                    "name varchar(150), lastname varchar(150), age int);").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("An error occurred while creating the table" + e.getMessage());
        }
    }


    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User;").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("An error occurred while deleting the table" + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("An error occurred while saving the user" + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("An error occurred while removing the user" + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
        session.beginTransaction();
        list = session.createQuery("FROM User").getResultList();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("An error occurred while getting the user" + e.getMessage());
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("An error occurred while clearing the table" + e.getMessage());
        }
    }
}

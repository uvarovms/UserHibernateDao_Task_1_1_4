package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        if (session.createSQLQuery("CHECK TABLE USER")
                .addEntity(User.class).executeUpdate() != -1) {
            session.createSQLQuery("CREATE TABLE USER" +
                            "(id int PRIMARY KEY AUTO_INCREMENT," +
                            "name VARCHAR(100) not null, " +
                            "lastName VARCHAR(100) not null, " +
                            "age int not null)")
                    .addEntity(User.class).executeUpdate();
        }
        tx.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS User")
                .addEntity(User.class).executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory factory = Util.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(new User(name, lastName, age));
        tx.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory factory = Util.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM User WHERE id =: id")
                .setParameter("id", id)
                .executeUpdate();
        System.out.println("Удален пользователь id = " + id);
        tx.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory factory = Util.getSessionFactory();
        Session session = factory.openSession();
        List<User> userList = session.createQuery("FROM User ").list();
        for (User usr : userList) {
            System.out.println(usr);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        tx.commit();
    }
}

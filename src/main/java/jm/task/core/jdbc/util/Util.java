package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;

public class Util {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                Properties properSettings = new Properties();
                properSettings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/run_sql_study");
                properSettings.put(Environment.USER, "root");
                properSettings.put(Environment.PASS, "root");

                config.addAnnotatedClass(User.class);
                config.setProperties(properSettings);

                ServiceRegistry serviceReg = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();

                sessionFactory = config.buildSessionFactory(serviceReg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

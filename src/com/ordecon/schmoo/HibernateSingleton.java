package com.ordecon.schmoo;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.io.File;

/**
 * @author Ivan Stojic
 */
public class HibernateSingleton {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static Session session;

    private static void initializeHibernate() {
        configuration = new Configuration().configure(new File("hibernate.cfg.xml"));
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            initializeHibernate();
        }

        return configuration;
    }

    public static Session getSession() {
        if (session == null) {
            initializeHibernate();
        }

        return session;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initializeHibernate();
        }

        return sessionFactory;
    }
}

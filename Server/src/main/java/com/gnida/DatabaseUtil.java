package com.gnida;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public enum DatabaseUtil {
    INSTANCE;

    private final SessionFactory factory;

    DatabaseUtil() {
        factory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
    }

    public Session openSession() {
        return factory.openSession();
    }
}

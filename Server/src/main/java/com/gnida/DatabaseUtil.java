package com.gnida;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseUtil {
    private static SessionFactory factory;

    DatabaseUtil() {
    }
    private static SessionFactory CreateFactory() {
        if (factory == null) {
            factory = new Configuration().configure("/hibernate.cfg.xml")
                    .buildSessionFactory();
        }
        return factory;
    }

    public static Session openSession() {
        if(factory == null) {
            CreateFactory();
        }
        return factory.openSession();
    }
}

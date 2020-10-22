package com.vertigo.util;

import com.vertigo.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Set;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {

        try {

            Configuration configuration = new Configuration();

            configuration.configure("hibernate.cfg.xml");

            configuration.addAnnotatedClass(ErsReimbursement.class);
            configuration.addAnnotatedClass(ErsReimbursementStatus.class);
            configuration.addAnnotatedClass(ErsReimbursementType.class);
            configuration.addAnnotatedClass(ErsUser.class);
            configuration.addAnnotatedClass(ErsUserRole.class);

            Reflections reflections = new Reflections("com.vertigo.models");
            Set<Class<? extends Object>> entities = reflections.getTypesAnnotatedWith(Entity.class);
            entities.forEach(configuration::addAnnotatedClass);

            return configuration.buildSessionFactory();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }

    }

    public static SessionFactory getSessionFactory() {
        return (sessionFactory == null) ? sessionFactory = buildSessionFactory() : sessionFactory;
    }

}

package com.vertigo.repositories;

import com.vertigo.models.ErsUser;
import com.vertigo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class ErsUserRepository {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public ErsUserRepository() {
        super();
    }

    public ErsUserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<ErsUser> findUserByUsernameAndPassword(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from ErsUser where username = :un and password = :pw", ErsUser.class)
                .setParameter("un", username)
                .setParameter("pw", password)
                .getResultList()
                .stream()
                .findFirst();
    }

}

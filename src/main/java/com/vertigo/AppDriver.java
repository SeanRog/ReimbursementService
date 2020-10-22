package com.vertigo;

import com.vertigo.models.ErsUser;
import com.vertigo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AppDriver {

    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();
            Query query = session.createQuery("from ErsUser");
            List<ErsUser> ersUsers = query.getResultList();
            ersUsers.forEach(System.out::println);

        }

    }

}

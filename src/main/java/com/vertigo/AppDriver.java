package com.vertigo;

import com.vertigo.models.ErsReimbursement;
import com.vertigo.models.ErsUser;
import com.vertigo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AppDriver {

    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();
            Query query = session.createQuery("From ErsUser where id = :id", ErsUser.class);
            query.setParameter("id", 4L);
            List<ErsUser> ersUsers = query.getResultList();
            for(ErsUser user : ersUsers) {
                System.out.println(user.getUserRole().getRoleName());
            }

            query = session.createQuery("from ErsReimbursement order by id", ErsReimbursement.class);
            List<ErsReimbursement> reimbursements = query.getResultList();
            for(ErsReimbursement reimbursement : reimbursements) {
                System.out.println(reimbursement);
            }

        }

    }

}

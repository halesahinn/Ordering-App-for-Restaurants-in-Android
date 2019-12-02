package org.cse486.service;

import org.cse486.config.HibernateUtil;
import org.cse486.domain.Desk;
import org.cse486.domain.MenuItem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "deskDao")
@ApplicationScoped
public class DeskDao {
    public List<Desk> getDesks() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select desk from  Desk desk");
        tx.commit();
        List list = query.list();

        session.close();
        return list;
    }

    public Desk addNewDesk(Desk desk) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(desk);
        tx.commit();
        session.close();
        return desk;
    }

    public void delete(Desk desk) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(desk);
        tx.commit();
        session.close();
    }

    public Desk findDesk(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select d from Desk d where id = ?");
        query.setLong(0, id);
        List list = query.list();
        tx.commit();
        session.close();
        if (list != null && list.size() > 0)
            return (Desk) list.get(0);
        else
            return null;


    }

}

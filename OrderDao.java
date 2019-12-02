package org.cse486.service;

import org.cse486.config.HibernateUtil;
import org.cse486.domain.MenuOrder;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "orderDao")
@ApplicationScoped
public class OrderDao {
    public List<MenuOrder> getCurrentOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select mc from MenuOrder mc");
        tx.commit();
        List list = query.list();
        session.close();
        return list;
    }

    public void save(MenuOrder order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(order);


        tx.commit();
        session.close();
    }
}

package org.cse486.service;

import org.cse486.config.HibernateUtil;
import org.cse486.domain.MenuCategory;
import org.cse486.domain.MenuItem;
import org.cse486.domain.MenuOrder;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "menuCategoryDao")
@ApplicationScoped
public class MenuCategoryDao {

    public List<MenuItem> getMenuItems(Long categoryId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select mc from MenuItem mc where mc.menuCategory.id = ?");
        query.setLong(0, categoryId);
        tx.commit();
        List list = query.list();

        session.close();
        return list;
    }

    public MenuItem getMenuItem(Long menuItemId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select mi from MenuItem mi where mi.id = ?");
        query.setLong(0, menuItemId);
        tx.commit();

        List list = query.list();
        session.close();

        if (list.size() > 0)
            return (MenuItem) list.get(0);
        else
            return null;

    }

    public List<MenuCategory> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select mc from MenuCategory mc");
        tx.commit();
        List list = query.list();
        session.close();
        return list;
    }

    public List<MenuCategory> getChildNodes(long parentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select mc from MenuCategory mc where mc.parent.id = ?");
        query.setLong(0, parentId);
        tx.commit();

        List list = query.list();

        session.close();
        return list;
    }

    public MenuCategory getRoot() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select mc from MenuCategory mc where mc.parent = null");
        query.setFirstResult(0);
        query.setMaxResults(1);
        tx.commit();

        List list = query.list();
        session.close();

        if (list.size() > 0)
            return (MenuCategory) list.get(0);
        else
            return null;


    }

    public MenuOrder insertOrder(long menuItemId, long deskId) {
        MenuItem menuItem = getMenuItem(menuItemId);
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (menuItem != null) {
            MenuOrder order = new MenuOrder();
            order.setTime(new Date());
            order.setOrder(menuItem);
            order.setDeskId(deskId);

            Transaction tx = session.beginTransaction();
            session.persist(order);
            session.close();
            return order;
        }

        session.close();

        return null;
    }

    public MenuItem insertMenuItem(MenuItem menuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        session.persist(menuItem);
        tx.commit();

        session.close();
        return menuItem;
    }

    public void deleteMenuItem(MenuItem menuItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction tx = session.beginTransaction();
        session.delete(menuItem);
        tx.commit();

        session.close();
    }
}

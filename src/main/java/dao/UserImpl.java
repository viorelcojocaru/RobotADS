/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Viorel
 */
public class UserImpl implements UserIntf{

    private static UserImpl instance;

    private UserImpl() {
    }

    public static UserImpl getInstance() {
        if (instance == null) {
            instance = new UserImpl();
        }
        return instance;
    }

    public List<User> getUserList() throws HibernateException{
        List<User> userList;
        List obiects;
        Session session = HibernateUtil.getSessionFactory().openSession();
        obiects = session.createQuery("FROM User").list();
        Transaction tx = session.beginTransaction();
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
        userList = new ArrayList<User>();
        for (Iterator iterator = obiects.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            userList.add(user);
        }
        if (userList!=null && !userList.isEmpty()) {
            return userList;
        }
        return null;
    }

    public User getUserById(int id) throws HibernateException {
        List obiects;
        Session session = HibernateUtil.getSessionFactory().openSession();
        obiects = session.createQuery("FROM User WHERE id=" + id).list();
        Transaction tx = session.beginTransaction();
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
        if (!obiects.isEmpty()) {
            User user = (User) obiects.get(0);
            return user;
        }
        return null;
    }

    public void saveUser(User user) throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public void updateUser(User user) throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(user);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public void deleteUser(User user) throws HibernateException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }
}

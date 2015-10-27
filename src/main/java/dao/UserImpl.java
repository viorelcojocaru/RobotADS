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
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Viorel
 */
public class UserImpl implements UserIntf{

    private static UserImpl instance;

    public UserImpl() {
    }

    public static UserImpl getInstance() {
        if (instance == null) {
            instance = new UserImpl();
        }
        return instance;
    }

    public List<User> getUserList() {
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
        if (userList.size() > 0) {
            return userList;
        }
        return null;
    }

    public User getUserById(int id) {
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

    public void saveUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public void updateUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(user);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public void deleteUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {

//        User user  = new User();
//		user.setName("nume");
//         user.setPassword("123sa4562722kjhjwef");
//         user.setUserUrl("url");
//         UserIntf userBC =UserImpl.getInstance();
//         userBC.saveUser(user);
         
//              System.out.println(  getInstance().getAdsList());
//        getInstance().deleteAds(ads);
//            System.out.println(  getInstance().getAdsById(5));

//        int id = 1;
//        User user = new User();
//        UserIntf userBC =UserImpl.getInstance();
//      
//        user =userBC. getUserById(id);
//        if (user != null) {
//            System.out.println(user);
//        } else {
//            System.out.println("no such Ads with id=" + id);
//        }

//           getInstance().deleteAds(ads);
    }

}

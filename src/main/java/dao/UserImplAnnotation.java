/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.User;
import org.hibernate.HibernateException;
import util.HibernateUtil;
import util.HibernateUtilAnnotation;
import org.hibernate.Session;

/**
 *
 * @author Olga
 */
public class UserImplAnnotation implements UserIntf {

    private static UserImplAnnotation instance;

    private UserImplAnnotation() {
    }

    public static UserImplAnnotation getInstance() {
        if (instance == null) {
            instance = new UserImplAnnotation();
        }
        return instance;
    }

    public List<User> getUserList() throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveUser(User user) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateUser(User user) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteUser(User user) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public User getUserById(int id) throws HibernateException {
        Session session = HibernateUtilAnnotation.getSessionFactory().openSession();
        Object obiect = session.load(User.class, new Long(id));
        User user = (User) obiect;
        session.close();
        HibernateUtil.shutdown();
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
    
}

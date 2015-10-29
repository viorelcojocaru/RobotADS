/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.User;
import org.hibernate.HibernateException;

public interface UserIntf {

    public List<User> getUserList() throws HibernateException;

    public void saveUser(User user) throws HibernateException;

    public void updateUser(User user) throws HibernateException;

    public void deleteUser(User user) throws HibernateException;
    
    public User getUserById(int id) throws HibernateException;
}

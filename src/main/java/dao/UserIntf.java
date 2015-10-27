/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.User;

public interface UserIntf {

    public List<User> getUserList();

    public void saveUser(User ads);

    public void updateUser(User ads);

    public void deleteUser(User ads);
    
    public User getUserById(int id);
}

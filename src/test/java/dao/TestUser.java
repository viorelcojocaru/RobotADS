/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author Olga
 */
public class TestUser {    
   

    private void saveUserTest(User user) {
        UserIntf userBC = UserImpl.getInstance();
        assert (user != null);
        userBC.saveUser(user);
    }

    private void updateUserTest(User user) {
        UserIntf userBC = UserImpl.getInstance();
        assert (user != null);
        userBC.updateUser(user);
    }

    private void deleteUserTest(User user) {
        UserIntf userBC = UserImpl.getInstance();
        assert (user != null);
        userBC.deleteUser(user);
    }
    
    private User getUserByIdTest(int id) {        
        UserIntf userBC = UserImpl.getInstance();
        User user = new User();
        user = userBC.getUserById(id);
        assert (user!=null);        
        return user;
    }
    
  public List<User> getUserListTest(){
        UserIntf userBC = UserImpl.getInstance();
        List<User> list=new ArrayList<User>();
        list=userBC.getUserList();
        assert(list!=null);
        assert(!list.isEmpty()); 
        return null;
    }
    
    public static void main(String[] args) {
      TestUser test=new TestUser();
      /*
      if (user != null) {
            System.out.println(user.getId());
            System.out.println(user.getLogin());
            System.out.println(user.getPassword());
            System.out.println(user.getRedirectUrl());
        } else {
            System.out.println("no such Ads with id=" + id);
        }*/ 
           
    }
            
}

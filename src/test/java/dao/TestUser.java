/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.User;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Olga
 */
public class TestUser {

    @Test
    private void saveUserTest(User user) {
        UserIntf userBC = UserImpl.getInstance();
        assert (user != null);
        userBC.saveUser(user);
    }

    @Test
    private void updateUserTest(User user) {
        UserIntf userBC = UserImpl.getInstance();
        assert (user != null);
        userBC.updateUser(user);
    }

    @Test
    private void deleteUserTest(User user) {
        UserIntf userBC = UserImpl.getInstance();
        assert (user != null);
        userBC.deleteUser(user);
    }

    @Test
    private User getUserByIdTest(int id) {
        UserIntf userBC = UserImpl.getInstance();
        User user = new User();
        user = userBC.getUserById(id);
        assert (user != null);
        Assert.assertEquals("balishoi", user.getLogin());
        return user;
    }

    @Test
    public List<User> getUserListTest() {
        UserIntf userBC = UserImpl.getInstance();
        List<User> list = new ArrayList<User>();
        list = userBC.getUserList();
        assert (list != null);
        assert (!list.isEmpty());
        return null;
    }

}

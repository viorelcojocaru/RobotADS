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
    public void saveUserTest() {
        User user=new User();
        user.setLogin("Viorel");
        user.setPassword("123");
        user.setRedirectUrl("http://redirect");
        UserIntf userBC = UserImpl.getInstance();
//        userBC.saveUser(user);      
//        Long id = user.getId();
//        Assert.assertNotNull(id);        
//        Assert.assertEquals(2, user.getId());
//        Assert.assertEquals("Viorel", user.getLogin());
//        Assert.assertEquals("123", user.getPassword());
//        String urlStr="http://redirect";
//        Assert.assertEquals(urlStr, user.getRedirectUrl());
    }

    @Test
    public void updateUserTest() {
        UserIntf userBC = UserImpl.getInstance();
        User user =new User();
        int id=2;
        user = userBC.getUserById(id);
        user.setLogin("ViorelTestUpdate");
        user.setPassword("123TestUpdate");
        user.setRedirectUrl("http://redirectTestUpdate");
        userBC.updateUser(user);
        User userUpdated=userBC.getUserById(id);
        Assert.assertEquals(id, userUpdated.getId());
        Assert.assertEquals("ViorelTestUpdate", userUpdated.getLogin());
        Assert.assertEquals("123TestUpdate", userUpdated.getPassword());
        String urlStr="http://redirectTestUpdate";
        Assert.assertEquals(urlStr, userUpdated.getRedirectUrl());
    }
/*
    @Test
    public void deleteUserTest(User user) {
        UserIntf userBC = UserImpl.getInstance();
        assert (user != null);
        userBC.deleteUser(user);
    }
*/
    @Test
    public void getUserByIdTest() {
        int id=1;
        UserIntf userBC = UserImpl.getInstance();
        User user;
        user = userBC.getUserById(id);
        assert (user != null);
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("balishoi", user.getLogin());
        Assert.assertEquals("123456789", user.getPassword());
        String urlStr="?targetUrl=L2gtdmlsbGUtZGUtbW9udHJlYWwvMTcwMDI4MT9zaXRlTG9jYWxlPWVuX0NBXkY3RUhPcTk5amp2MENIMmw0ejQ4RVE9PQ--";
        Assert.assertEquals(urlStr, user.getRedirectUrl());
        
    }

    @Test
    public void getUserListTest() {
        UserIntf userBC = UserImpl.getInstance();
        List<User> list = new ArrayList<User>();
        list = userBC.getUserList();
        assert (list != null);
        assert (!list.isEmpty());
    }

}

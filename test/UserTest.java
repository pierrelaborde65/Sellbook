import controllers.ProductController;
import controllers.UserController;
import models.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dv on 08/11/2016.
 */
public class UserTest {

    /* ----------------- TEST User ---------------------------*/

    User u = new User(null,"test","test@test.com",1,null,null,1,null,null,null,null,"0",null,null);


    @Test
    public void TestGetUsers(){
        u.save();
        Assert.assertNotNull("No User Found",User.find.all());
    }

    @Test
    public void TestGetUserByID() {
        u.save();
        Assert.assertNotNull("No User Found",User.find.byId(u.getId()));
    }

    @Test
    public void addUser(){
        int nb = u.find.all().size();
        Assert.assertEquals(nb,u.find.all().size());
        u.save();
        Assert.assertEquals(nb+1,u.find.all().size());
    }

    @Test
    public void deleteUser(){
        int nb = u.find.all().size();
        Assert.assertEquals(nb,u.find.all().size());
        u.delete();
        Assert.assertEquals(nb-1,u.find.all().size());
    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import models.Order;
import models.Product;
import models.ProductInShoppingCart;
import models.User;
import org.junit.*;
import controllers.ProductController;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import javax.validation.constraints.AssertTrue;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

/**
 * Created by dv on 19/11/2016.
 */
public class OrderTest {
    /* ----------------- TEST Order ---------------------------*/

    User su = new User(null,"test","test@test.com",1,null,null,1,null,null,null,null,"0",null,null);
    Product pr = new Product(null,null,"test",null,1,1,null);
    User seller = new User(null,"test","test@test.com",1,null,null,1,null,null,null,null,"1",null,null);
    Order o = new Order(null,su.getId(),seller.getId(),pr.getIdProduct(),"",1,1,"test");


    @Test
    public void TestGetOrders(){
        o.save();
        Assert.assertNotNull("No Order Found",Order.find.all());
    }

    @Test
    public void TestGetOrderByID() {
        o.save();
        Assert.assertNotNull("No Order Found",Order.find.byId(o.getIdOrder()));
    }

    @Test
    public void addOrder(){
        int nb = o.find.all().size();
        Assert.assertEquals(nb,o.find.all().size());
        o.save();
        Assert.assertEquals(nb+1,o.find.all().size());
    }

    @Test
    public void deleteOrder(){
        int nb = o.find.all().size();
        Assert.assertEquals(nb,o.find.all().size());
        o.delete();
        Assert.assertEquals(nb-1,o.find.all().size());
    }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
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
public class CartTest {

     /* ----------------- TEST Cart ---------------------------*/

    User u = new User(null,"test","test@test.com",1,null,null,1,null,null,null,null,"0",null,null);
    Product pr = new Product(null,null,"test",null,1,1,null);
    ProductInShoppingCart p = new ProductInShoppingCart(null,1,u,pr);


    @Test
    public void TestGetProducts(){
        p.save();
        Assert.assertNotNull("No Product Line Found",ProductInShoppingCart.find.all());
    }

    @Test
    public void TestGetProductLineByID() {
        p.save();
        Assert.assertNotNull("No Product Line Found",ProductInShoppingCart.find.byId(p.getId()));
    }

    @Test
    public void addProductLineToShoppingCart(){
        int nb = p.find.all().size();
        Assert.assertEquals(nb,p.find.all().size());
        p.save();
        Assert.assertEquals(nb+1,p.find.all().size());
    }

    @Test
    public void deleteProductLineInShoppingCart(){
        int nb = p.find.all().size();
        Assert.assertEquals(nb,p.find.all().size());
        p.delete();
        Assert.assertEquals(nb-1,p.find.all().size());
    }
}

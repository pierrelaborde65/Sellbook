import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import models.Product;
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
 * Created by meier on 08/11/2016.
 */
public class ProductTest {


    /* ----------------- TEST Product ---------------------------*/
    //no Products

    Product p = new Product(null,null,"test",null,1,1,null);


    @Test
    public void TestGetProducts(){
        p.save();
        Assert.assertNotNull("No Product Found",Product.find.all());
    }

    @Test
    public void TestGetProductByID() {
        p.save();
        Assert.assertNotNull("No Product Found",Product.find.byId(p.getIdProduct()));
    }

    @Test
    public void addProduct(){
        int nb = p.find.all().size();
        Assert.assertEquals(nb,p.find.all().size());
        p.save();
        Assert.assertEquals(nb+1,p.find.all().size());
    }

    @Test
    public void deleteProduct(){
        int nb = p.find.all().size();
        Assert.assertEquals(nb,p.find.all().size());
        p.delete();
        Assert.assertEquals(nb-1,p.find.all().size());
    }

}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
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

    /*
    @Test
    public void TestGetProducts(){
        ProductController pc = new ProductController();
        Assert(pc.getProducts(), "No Product Found");
    }

    @Test
    public void TestGetProduct() {

    }

    @Test
    public void addProduct(){
        ProductController pc = new ProductController();
        pc.addProduct();
    }

    @Test
    public void getProducts(){

    }

    @Test
    public void getProduct(){


    }
    */



}

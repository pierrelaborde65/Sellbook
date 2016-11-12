package controllers;

import com.google.inject.Inject;
import models.Product;
import models.User;
import controllers.UserController;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import java.util.Map;

/**
 * Created by meier on 07/11/2016.
 */
public class ProductController extends Controller {

    /**
     *
     * @return
     */
    public Result newProduct() {
        return ok(newProduct.render(UserController.getStatusUserText()));
    }

    /**
     *
     * @return
     */
    public Result userAllProducts() {
        return ok(allProducts.render(UserController.getStatusUserText()));
    }

    @Inject
    FormFactory formFactory;

    /** add a Product
     *
     * @return add a Product in JSon file and redirect on the page "newProduct"
     */
    public Result addProduct() {
        System.out.println("ENTER DANS ADD PROD");
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("nameProduct")[0];
        String description = values.get("descriptionProduct")[0];
        String price = values.get("priceSeller")[0];
        String quantity = values.get("quantityStock")[0];
        String id = values.get("id")[0];

        System.out.println(name);
        System.out.println(description);
        System.out.println(price);
        System.out.println(quantity);
        System.out.println(id);

/*
        User seller = User.find.byId(Long.valueOf(id));
        if(seller.getStatusUser() != 2) {
            return notFound("The person is not a seller");
        }
        else {*/
        Product product = new Product(null, Integer.parseInt(id),name, description, Float.parseFloat(price), Integer.parseInt(quantity));
        return created("The product has been created");

    }

    /** Get All Products
     *
     * @return IF there isn't any Product, return "Product Not Found"
     * else return the list of Products
     */
    public Result getProducts() {
        if (Product.find.all().isEmpty()) {
            return notFound("No Product Found");
        }else {
            return ok(Json.toJson(Product.find.all()));
        }
    }

    /**
     * Get a Product with his ID
     * @param id the id of a Product
     * @return IF Product doesn't exist, return "Product Not Found"
     * Else return 200 OK
     */
    public Result getProduct(Long id) {
        if (Product.find.byId(id) == null) {
            return notFound("This Product does not exist.");
        } else {
            return ok(Json.toJson(Product.find.byId(id)));
        }
    }

    /**
     * DELETE a Product in the database with his ID
     * @param id The id of a Product
     * @return If Product doesn't exist in the dababase, return "Product Not Found"
     * Else return <b>200 Ok</b>
     */

    /*
    public Result deleteProduct(Long id) {
        if(Product.find.byId(id) == null) {
            return notFound("Product not found.");
        }
        else {
            Product.find.deleteById(id);
            return ok("The person has been deleted");
        }
    }*/

}

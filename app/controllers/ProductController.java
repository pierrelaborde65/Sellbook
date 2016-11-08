package controllers;

import com.google.inject.Inject;
import models.Product;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import views.html.product;

/**
 * Created by meier on 07/11/2016.
 */
public class ProductController extends Controller {

    /**
     *
     * @return
     */
    public Result newProduct() {
        return ok(product.render("LES PRODUITS"));
    }

    @Inject
    FormFactory formFactory;

    /** add a Product
     *
     * @return add a Product in JSon file and redirect on the page "newProduct"
     */
    public Result addProduct() {
        Product product = formFactory.form(Product.class).bindFromRequest().get();
        System.out.println(product);
        product.save();
        return redirect(routes.ProductController.newProduct());
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

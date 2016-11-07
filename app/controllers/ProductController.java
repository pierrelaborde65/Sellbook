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


    public Result product() {
        return ok(product.render("LES PRODUITS"));
    }

    @Inject
    FormFactory formFactory;


    public Result addProduct() {
        Product product = formFactory.form(Product.class).bindFromRequest().get();
        System.out.println(product);
        product.save();
        return redirect(routes.ProductController.product());
    }


    public Result getProducts() {
        return ok(Json.toJson(Product.find.all()));
    }

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

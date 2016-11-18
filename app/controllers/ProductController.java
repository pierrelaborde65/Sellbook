package controllers;

import com.google.inject.Inject;
import models.Product;
import models.User;
import controllers.UserController;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

import java.util.List;
import java.util.Map;

import static tyrex.util.Configuration.console;

/**
 * Created by meier on 07/11/2016.
 */
public class ProductController extends Controller {

    /** PRODUCT CREATION - SELLER
     *
     * @return 200 - Product Created
     */
    public Result newProduct() {
        return ok(newProduct.render(UserController.getStatusUserText()));
    }

    /**
     *
     * @return 200 - Product Created
     */
    public Result newProductAdmin() {
        return ok(newProductAdmin.render(UserController.getStatusUserText()));
    }

    /** PRODUCT SELECTION
     *
     * @return 200 - All Products
     */
    public Result userAllProducts() {
        return ok(allProducts.render(UserController.getStatusUserText()));
    }

    /** OWN PRODUCT SELECTION - SELLER
     *
     * @return 200 - All Seller's Products
     */
    public Result myProductSeller() {
        return ok(myProducts.render(UserController.getStatusUserText()));
    }


    @Inject
    FormFactory formFactory;

    /**  ADD Product - SELLER
     *
     * @return Add a Product associated to the Seller into the Database : 200 - Product Created
     */
    public Result addProduct() {
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
        Product product = new Product(null, id,name, description, Float.parseFloat(price), Integer.parseInt(quantity));
        return created("The product has been created");

    }

    /** ADD Product - ADMIN
     *
     * @return Add a Product associated to the Admin into the database : 200 - Product Created
     */
    public Result addProductAdmin() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String idSeller = values.get("idSeller")[0];
        String name = values.get("nameProduct")[0];
        String description = values.get("descriptionProduct")[0];
        String price = values.get("priceSeller")[0];
        String quantity = values.get("quantityStock")[0];

/*
        User seller = User.find.byId(Long.valueOf(id));
        if(seller.getStatusUser() != 2) {
            return notFound("The person is not a seller");
        }
        else {*/
        Product product = new Product(null, idSeller,name, description, Float.parseFloat(price), Integer.parseInt(quantity));
        return created("The product has been created");

    }

    /** GET PRODUCTS - ALL
     *
     * @return IF there isn't any Product, return 404 - "No Products Found"
     * else return 200 - all Products
     */
    public Result getProducts() {
        if (Product.find.all().isEmpty()) {
            return notFound("No Products Found");
        }else {
            return ok(Json.toJson(Product.find.all()));
        }
    }

    /**
     * GET PRODUCT BY ID - ALL
     * @param id  (Long) the id of a Product
     * @return IF the Product doesn't exist, return 404 - "This Product does not exist"
     * Else return 200 - Product
     */
    public Result getProduct(Long id) {
        if (Product.find.byId(id) == null) {
            return notFound("This Product does not exist.");
        } else {
            return ok(Json.toJson(Product.find.byId(id)));
        }
    }


    /**
     * GET PRODUCTS BY SELLER ID - SELLER
     * @param id (Long) the id of a Seller
     * @return IF the Seller doesn't exist, return 404 - "This Seller does not exist."
     * Else return 200 - All Seller's Products
     */
    public Result getMyProducts(Long id) {
        if (Product.find.where().like("idSeller", id.toString()) == null) {
            return notFound("This Seller does not exist.");
        } else {
            return ok(Json.toJson(Product.find.where().like("idSeller", id.toString()).findList()));
        }
    }

    /**
     * SEARCH PRODUCT - ALL
     * @return 200 - Product
     *
     */
    public Result searchProduct() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String idSeller = values.get("idSeller")[0];
        String nameProduct = values.get("nameProduct")[0];
        if (Integer.parseInt(idSeller) == 0 && nameProduct == null) {
            return ok(Json.toJson(Product.find.all()));
        } else  if (Integer.parseInt(idSeller) != 0 && nameProduct == null){
            return ok(Json.toJson(Product.find.where().like("idSeller", idSeller.toString()).findList()));
        } else  if (Integer.parseInt(idSeller) == 0 && nameProduct != null){
            return ok(Json.toJson(Product.find.where().like("nameProduct", "%"+nameProduct+"%").findList()));
        } else {
            return ok(Json.toJson(Product.find.where().like("idSeller", idSeller.toString()).like("nameProduct", "%"+nameProduct+"%").findList()));
        }
    }

    public Result updateProduct() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String idProduct = values.get("idProduct")[0];
        System.out.println(idProduct);
        long id = Long.parseLong(idProduct);
        String nameProduct = values.get("nameProduct")[0]; 
        String descriptionProduct = values.get("descriptionProduct")[0];
        String priceSeller = values.get("priceSeller")[0];
        String quantityStock = values.get("quantityStock")[0];

        if(Product.find.byId(id) == null) {
            return notFound("Product not found.");
        }else{

            Product product = Product.find.byId(id);
            product.nameProduct = nameProduct;
            product.descriptionProduct = descriptionProduct;
            product.priceSeller =  Float.valueOf(priceSeller);
            product.quantityStock = Integer.valueOf(quantityStock);
            product.save();

            return ok("The Product has been updated");
        }

    }





    /**
     * DELETE PRODUCT BY ID
     * @param id The Product id
     * @return IF Product doesn't exist in the dababase, return 404 - "Product Not Found"
     * Else return 200 - "The Product has been deleted"
     */
    public Result deleteProduct(Long id) {
        if(Product.find.byId(id) == null) {
            return notFound("Product not found.");
        }else{
            Product.find.deleteById(id);
            return ok("The Product has been deleted");
        }
    }

}

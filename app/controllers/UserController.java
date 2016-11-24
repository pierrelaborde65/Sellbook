package controllers;

import com.avaje.ebean.enhance.agent.SysoutMessageOutput;
import com.google.inject.Inject;
import models.Product;
import models.ProductInShoppingCart;
import models.User;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static tyrex.util.Configuration.console;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UserController extends Controller {
    @Inject FormFactory formFactory;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        //User user = new User(null,"a", "admin@a.com",15,"ad","ad",25,null,BCrypt.hashpw("aaaaaa", BCrypt.gensalt()),null,null,"2",null,null);
        return ok(index.render(getStatusUserText()));
}

    /** USER CREATION
     *
     * @return 200 - User Created
     */
    public Result newSU() {
        return ok(newSU.render(getStatusUserText()));
    }

    /** SELLER CREATION
     *
     * @return 200 - Seller Created
     */
    public Result newSeller() {
        return ok(newSeller.render(getStatusUserText()));
    }

    /** USER SELECTION
     *
     * @return 200 - All Users
     */
    public Result allSU() {
        return ok(allSU.render(getStatusUserText()));
    }

    /** SELLER SELECTION
     *
     * @return 200 - All Sellers
     */
    public Result allSellers() {
        return ok(allSellers.render(getStatusUserText()));
    }

    /** GET USERS - ALL
     *
     * @return IF there isn't any Users, return 404 - "No Users Found"
     * else return 200 - all Users
     */
    public Result getUsers() {
        return ok(Json.toJson(User.find.all()));
    }

    /** GET SELLERS - ALL
     *
     * @return IF there isn't any Sellers, return 404 - "No Sellers Found"
     * else return 200 - all Sellers
     */
    public Result getSellers() {
        return ok(Json.toJson(User.find.where().like("statusUser", "1").findList()));
    }

    /** GET SIMPLE USERS - ALL
     *
     * @return IF there isn't any Simple Users, return 404 - "No Simple Users Found"
     * else return 200 - all Simple Users
     */
    public Result getSimpleUsers() {
        return ok(Json.toJson(User.find.where().like("statusUser", "0").findList()));
    }

    /**
     * GET USERS BY ID - ALL
     * @param id  (Long) the id of a User
     * @return IF the User doesn't exist, return 404 - "User does not exist"
     * Else return 200 - User
     */
    public Result getUser(Long id) {
        if(User.find.byId(id) == null) {
            return notFound("User does not exist.");
        }
        else {
            return ok(Json.toJson(User.find.byId(id)));
        }
    }

    /**
     * GET STATUT - ALL
     *
     * @return IF the User doesn't exist, return "None"
     * Else IF getStatusUser = 0 return "SU"
     *      IF getStatusUser = 1 return "SC"
     *      IF getStatusUser = 2 return "Admin"
     */
    public static String getStatusUserText() {
        if((request().cookies().get("id") !=null) && (request().cookies().get("token") !=null)){
            Long id = Long.valueOf(request().cookies().get("id").value());
            String token = request().cookies().get("token").value();
            User user = User.find.byId(id);
            if(user != null){
                if (BCrypt.checkpw(token, user.getToken())) {
                    if (user.getStatusUser().equals("0"))
                        return "SU";
                    if (user.getStatusUser().equals("1"))
                        return "SC";
                    if (user.getStatusUser().equals("2"))
                        return "Admin";
                }

            }
        }
        return "None";
    }

    /**
     * DELETE USER BY ID
     * @param id The User id
     * @return IF User doesn't exist in the dababase, return 404 - "User does not exist."
     * Else return 200 - "The User has been succesfully deleted"
     */
    public Result deleteUser(Long id) {
        if(User.find.byId(id) == null) {
            return notFound("User does not exist.");
        }
        else {
            User.find.deleteById(id);
            return ok("The user has been succesfully deleted");
        }
    }


    /**
     * LOGIN
     *
     * @return IF email or password does not match return 404 - "email or password incorrect"
     * ELSE 200 - OK Cookie Created with ID + Token
     */
    public Result login() {
        // Get information from the login form
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String email = form.get("email")[0];
        String password = form.get("password")[0];
        // Search and take users in the database with this email ( maximum 1 because email is unique in the DB)
        List<User> users = User.find.where().like("email", email).findList();
        if(users.size() == 0) {
            return notFound("email or password incorrect");
        }
        else {
            String passwordHashed = users.get(0).getPassword();
            // check if it is the good password
            if (!BCrypt.checkpw(password, passwordHashed)) {
                return notFound("email or password incorrect");
            } else {
                // Generate a random token and give it to the User to identify him
                String token = UUID.randomUUID().toString();
                //crypte the token
                String tokenCrypted= BCrypt.hashpw(token, BCrypt.gensalt());
                users.get(0).setToken(tokenCrypted);
                // save the user with the new token in the database
                users.get(0).save();

                // get the status of the user to redirect him to the good homepage
                return ok(index.render(getStatusUserText())).withCookies(new Http.Cookie("token", token, 86400 , null, null, false, false)).withCookies(new Http.Cookie("id", users.get(0).getId().toString(), 86400 , null, null, false, false));
            }
        }
    } 


    /**
     * AUTHENTIFICATION
     * @return IF ID & Token Match 200 - OK
     * Else return 404 - "Your connection is expired or invalid. Please log in again"
     */
    public Result isConnected() {
        if((request().cookies().get("id") !=null) && (request().cookies().get("token") !=null)){
            Long id = Long.valueOf(request().cookies().get("id").value());
            String token = request().cookies().get("token").value();
            User user = User.find.byId(id);
            if (user != null){
                if (BCrypt.checkpw(token, user.getToken())) {
                    return ok(Json.toJson(user));
                }
            }
        }
        return notFound("Your connection is expired or invalid. Please log in again");
    }

    /**
     * LOG OUT
     * @return IF ID & Token Match 200 - OK & discard Cookie
     * Else return 404 - "Log out error"
     */
    public Result logout() {
        // Get attribute from the form
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        Long id = Long.valueOf(values.get("id")[0]);
        String token = values.get("token")[0];
        //LIKE -----------------------------------------------------------------------------------
        //User user = User.find.where().like("id", id).like("token", token).findUnique();
        User user = User.find.byId(id);
        if (BCrypt.checkpw(token, user.getToken())) {
            response().discardCookie("token");
            response().discardCookie("id");
            return ok();
        }
        else {
            return notFound("Log out error");
        }
    }

    /**
     * SIMPLE USER REGISTRATION
     *
     * @return IF email already used return 422 - "This email is already used"
     * ELSE 200 - Created
     */
    public Result registerSU() {
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String name = form.get("name")[0];
        String email = form.get("email")[0];
        String numberAddress = form.get("numberAddress")[0];
        String streetAddress = form.get("streetAddress")[0];
        String cityAddress = form.get("cityAddress")[0];
        String postCodeAddress = form.get("postCodeAddress")[0];
        String phoneNumber = form.get("phoneNumber")[0];
        String status = "0";
        String password = BCrypt.hashpw(form.get("password")[0], BCrypt.gensalt());
        // check if email is not already used
        User checkEmailUser = User.find.where().like("email", email).findUnique();
        if (checkEmailUser != null)
            return status(422,"This email is already used");
        else {
            User user = new User(null, name, email, Integer.parseInt(numberAddress), streetAddress, cityAddress, Integer.parseInt(postCodeAddress), phoneNumber, password, null, null, status, null, null);
            user.save();
            return created();
        }
    }


    /**
     * SELLER REGISTRATION
     *
     * @return IF email already used return 422 - "This email is already used"
     * ELSE 200 - Created
     */
    public Result registerSC() {
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String name = form.get("name")[0];
        String email = form.get("email")[0];
        System.out.println(email);
        String numberAddress = form.get("numberAddress")[0];
        String streetAddress = form.get("streetAddress")[0];
        String cityAddress = form.get("cityAddress")[0];
        String postCodeAddress = form.get("postCodeAddress")[0];
        String phoneNumber = form.get("phoneNumber")[0];
        String siret = form.get("siret")[0];
        String descriptionSeller = form.get("descriptionSeller")[0];
        String password = BCrypt.hashpw(form.get("password")[0], BCrypt.gensalt());
        String status = "1";
        // check if email not already used
        User checkEmailUser = User.find.where().like("email", email).findUnique();
        if (checkEmailUser != null)
            return status(422,"This email is already used");
        else {
            User user = new User(null, name, email, Integer.parseInt(numberAddress), streetAddress, cityAddress, Integer.parseInt(postCodeAddress), phoneNumber, password, siret, descriptionSeller, status, null, null);
            user.save();
            return created();
        }
    }

    /**
     * SEARCH SIMPLE USER - ALL
     * @return 200 - Users
     *
     */
    public Result searchSU() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String nameSU = values.get("nameSU")[0];
        if (nameSU == null){
            return ok(Json.toJson(User.find.where().like("statusUser", "0").findList()));
        } else {
            return ok(Json.toJson(User.find.where().like("statusUser", "0").like("name", "%"+nameSU+"%").findList()));
        }
    }


    /**
     * SEARCH SELLER - ALL
     * @return 200 - Sellers
     *
     */
    public Result searchSeller() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String nameSeller = values.get("nameSeller")[0];
        if (nameSeller == null){
            return ok(Json.toJson(User.find.where().like("statusUser", "1").findList()));
        } else {
            return ok(Json.toJson(User.find.where().like("statusUser", "1").like("name", "%"+nameSeller+"%").findList()));
        }
    }

    /**
     * UPDATE SIMPLE USER
     * @return IF Simple User exists : 200 - "The Simple User has been updated"
     * ELSE 404 - "User not found"
     *
     */
    public Result updateSimpleUser() {
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String idSU = form.get("id")[0];
        long id = Long.parseLong(idSU);
        String name = form.get("name")[0];
        String email = form.get("email")[0];
        String numberAddress = form.get("numberAddress")[0];
        String streetAddress = form.get("streetAddress")[0];
        String cityAddress = form.get("cityAddress")[0];
        String postCodeAddress = form.get("postCodeAddress")[0];
        String phoneNumber = form.get("phoneNumber")[0];

        if(User.find.byId(id) == null) {
            return notFound("User not found.");
        }else{

            User su = User.find.byId(id);
            su.setName(name);
            su.setEmail(email);
            su.setNumberAddress(Integer.valueOf(numberAddress));
            su.setStreetAddress(streetAddress);
            su.setCityAddress(cityAddress);
            su.setPostCodeAddress(Integer.valueOf(postCodeAddress));
            su.setPhoneNumber(phoneNumber);
            su.save();

            return ok("The Simple User has been updated");
        }

    }

    /**
     * UPDATE SELLER
     * @return IF Seller exists : 200 - "The Seller has been updated"
     * ELSE 404 - "Seller not found"
     *
     */
    public Result updateSeller() {
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String idSC = form.get("id")[0];
        long id = Long.parseLong(idSC);
        String siret = form.get("siret")[0];
        String name = form.get("name")[0];
        String email = form.get("email")[0];
        String numberAddress = form.get("numberAddress")[0];
        String streetAddress = form.get("streetAddress")[0];
        String cityAddress = form.get("cityAddress")[0];
        String postCodeAddress = form.get("postCodeAddress")[0];
        String phoneNumber = form.get("phoneNumber")[0];
        String descriptionSeller = form.get("descriptionSeller")[0];

        if(User.find.byId(id) == null) {
            return notFound("Seller not found.");
        }else{

            User sc = User.find.byId(id);
            sc.setName(name);
            sc.setSiret(siret);
            sc.setEmail(email);
            sc.setNumberAddress(Integer.valueOf(numberAddress));
            sc.setStreetAddress(streetAddress);
            sc.setCityAddress(cityAddress);
            sc.setPostCodeAddress(Integer.valueOf(postCodeAddress));
            sc.setPhoneNumber(phoneNumber);
            sc.setDescriptionSeller(descriptionSeller);
            sc.save();

            return ok("The seller has been updated");
        }

    }

    /**
     * ADD TO SHOPPING CART - SU
     * @return IF User id does not match : 404 - "User doesn't exist"
     * ELSE IF Product id does not match : 404 - "Product doesn't exist"
     * ELSE 200 - OK
     */
    public Result addToCart(){
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String idProduct = values.get("idProduct")[0];
        String idUser = values.get("idUser")[0];
        String quantityDesired = values.get("quantityDesired")[0];

        User user = User.find.byId(Long.valueOf(idUser));
        if (user == null)
            return notFound("User doesn't exist");
        Product product = Product.find.byId(Long.valueOf(idProduct));
        if (product == null){
            return notFound("Product doesn't exist");
        }
        user.addToShoppingCart(new ProductInShoppingCart(null, Integer.valueOf(quantityDesired),user,product));
        return ok(Json.toJson(user));

    }

    /**
     * SHOW CART
     * @return 200 - OK
     */
    public Result cart() {
        return ok(cart.render(getStatusUserText()));
    }

    /**
     * GET CART BY USER ID
     * @param id
     * @return IF the id user does not match : 404 - "User does not exist"
     * ELSE 200 - OK
     */
    public Result getCartUser(Long id) {
        User user = User.find.byId(id);
        if(user == null) {
            return notFound("User does not exist.");
        }
        else {
            ArrayNode shoppingCart = Json.newArray();
            for (int i = 0; i< user.getShoppingCart().size(); i++) {
                User seller = User.find.byId(Long.valueOf(user.getShoppingCart().get(i).getReferenceProduct().getIdSeller()));
                ObjectNode shoppingCartLine = Json.newObject();
                shoppingCartLine.put("id", user.getShoppingCart().get(i).getId());
                shoppingCartLine.put("idProduct", user.getShoppingCart().get(i).getReferenceProduct().getIdProduct());
                shoppingCartLine.put("name", user.getShoppingCart().get(i).getReferenceProduct().getNameProduct());
                shoppingCartLine.put("nameSeller", seller.getName());
                shoppingCartLine.put("description", user.getShoppingCart().get(i).getReferenceProduct().getDescriptionProduct());
                shoppingCartLine.put("price", user.getShoppingCart().get(i).getReferenceProduct().getPriceSeller());
                shoppingCartLine.put("quantity", user.getShoppingCart().get(i).getQuantity());
                shoppingCart.add(shoppingCartLine);
            }
            return ok(Json.toJson(shoppingCart));
        }
    }

    public Result deleteFromCart(Long id) {
        System.out.println("DELETE FROM CART");
        ProductInShoppingCart LineCart = ProductInShoppingCart.find.byId(id);
        if(LineCart == null) {
            System.out.println("11111");
            return notFound("LineCart does not exist.");
        }
        else {
            //check line with user
            if(request().cookies().get("id") !=null){
                if(Long.valueOf(request().cookies().get("id").value()) == LineCart.getReferenceUser().getId()){
                    ProductInShoppingCart.find.deleteById(id);
                    return ok("The Cart Line has been succesfully deleted");
                }
            }
        }
        return notFound("Cookies Error");
    }



}

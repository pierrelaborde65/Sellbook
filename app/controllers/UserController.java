package controllers;

import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UserController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("  "));
    }

    @Inject
    FormFactory formFactory;

    public Result getUsers() {
        return ok(Json.toJson(User.find.all()));
    }

    public Result getUser(Long id) {
        if(User.find.byId(id) == null) {
            return notFound("User does not exist.");
        }
        else {
            return ok(Json.toJson(User.find.byId(id)));
        }
    }

    public Result deleteUser(Long id) {
        if(User.find.byId(id) == null) {
            return notFound("User does not exist.");
        }
        else {
            User.find.deleteById(id);
            return ok("The person has been succesfully deleted");
        }
    }

    public Result login() {
        // Get information from the login form
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String email = form.get("email")[0];
        String password = form.get("password")[0];
        System.out.println(email);
        System.out.println(password);
        // Search and take users in the database with this email ( maximum 1 because email is unique in the DB)
        List<User> users = User.find.where().like("email", "%"+email+"%").findList();
        if(users.size() == 0) {
            return notFound("email or password incorrect");
        }
        else {
            String passwordHashed = users.get(0).getPassword();
            // check if it is the good password
            if (!BCrypt.checkpw(password, passwordHashed)) {
                return notFound("email or password incorrect");
            } else {
                // Generate a random token and give it to the user to identify him
                String token = UUID.randomUUID().toString();
                users.get(0).setToken(token);
                // save the user with the new token in the database
                users.get(0).save();
                // get the status of the user to redirect him to the good homepage
                return ok(index.render("Welcome")).withCookies(new Http.Cookie("token", token, 86400 , null, null, false, false)).withCookies(new Http.Cookie("id", users.get(0).getId().toString(), 86400 , null, null, false, false));
            }
        }
    }


    /**
     * Check if the user session is valid or not
     * @param id The ID of the person
     * @param token The token of the person
     * @return If the user has a valid session, return <b>200 Ok</b> <br/>
     * Else return 404 Not Found<
     */
    public Result isConnected(int id, String token) {
        System.out.println("ENTER");
        User user = User.find.where().like("id", "%"+id+"%").like("token", "%"+token+"%").findUnique();

//        System.out.println("isConnected(id, token) FROM PersonController.java -- isExist="+isExist);
        if(user != null) {
            System.out.println("conected");
            return ok(Json.toJson(user));
        }else {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            return notFound("Your connection is expired or invalid. Please log in again");
        }
    }

    /**
     * Log out. Delete cookies and set the value of token in the database to null
     * @return If the person doesn't exist, return 404 Not Found
     * Else return ok
     */
    public Result logout() {
        // Get attribute from the form
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String id = values.get("id")[0];
        String token = values.get("token")[0];
        System.out.println("Tentative logout");
        //LIKE -----------------------------------------------------------------------------------
        User user = User.find.where().like("id", "%"+id+"%").like("token", "%"+token+"%").findUnique();
        if(user != null) {
            response().discardCookie("token");
            response().discardCookie("id");
            return ok();
        }
        else {
            return notFound("Log out error");
        }
    }

    public Result registerSU() {
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String name = form.get("name")[0];
        String email = form.get("email")[0];
        String numberAddress = form.get("numberAddress")[0];
        String streetAddress = form.get("streetAddress")[0];
        String cityAddress = form.get("cityAddress")[0];
        String postCodeAddress = form.get("postCodeAddress")[0];
        String phoneNumber = form.get("phoneNumber")[0];
        int status = 0;
        String password = BCrypt.hashpw(form.get("password")[0], BCrypt.gensalt());
        // check if email not already used
        User user2 = User.find.where().like("email", "%"+email+"%").findUnique();
        if (user2 != null)
            return notFound("Email already used");
        else {
            User user = new User(null, name, email, Integer.parseInt(numberAddress), streetAddress, cityAddress, Integer.parseInt(postCodeAddress), phoneNumber, password, null, null, status, null);
            user.save();
            return created();
        }
    }

    public Result registerSC() {
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String name = form.get("name")[0];
        String email = form.get("email")[0];
        String numberAddress = form.get("numberAddress")[0];
        String streetAddress = form.get("streetAddress")[0];
        String cityAddress = form.get("cityAddress")[0];
        String postCodeAddress = form.get("postCodeAddress")[0];
        String phoneNumber = form.get("phoneNumber")[0];
        String siret = form.get("siret")[0];
        String descriptionSeller = form.get("descriptionSeller")[0];
        String password = BCrypt.hashpw(form.get("password")[0], BCrypt.gensalt());
        int status;
        // check if email not already used
        User userTest = User.find.where().like("email", "%"+email+"%").findUnique();
        if (userTest != null)
            return notFound("Email already used");
        else {
           /*-------------Check SIRET*/
            if (siret == null)
                status = 1;
            else
                status = 2;
            User user = new User(null, name, email, Integer.parseInt(numberAddress), streetAddress, cityAddress, Integer.parseInt(postCodeAddress), phoneNumber, password, siret, descriptionSeller, status, null);
            user.save();
            return created();
        }
    }

}

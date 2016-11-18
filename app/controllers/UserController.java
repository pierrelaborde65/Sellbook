package controllers;

import com.avaje.ebean.enhance.agent.SysoutMessageOutput;
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

import static tyrex.util.Configuration.console;

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
        /*User user = new User(null, "admin", "ad@gmail.com", 0, null, null, 0, null, BCrypt.hashpw("aaaaaa", BCrypt.gensalt()), null, null, "2", null);
        User user2 = new User(null, "seller", "s@s.com", 0, null, null, 0, null, BCrypt.hashpw("okokok", BCrypt.gensalt()), null, null, "1", null);
        user.save();
        user2.save();
        User user3 = new User(null, "seller", "ss@ss.com", 0, null, null, 0, null, BCrypt.hashpw("okokok", BCrypt.gensalt()), null, null, "1", null);
        user3.save();*/
        return ok(index.render(getStatusUserText()));
    }

    public Result newSU() {
        return ok(newSU.render(getStatusUserText()));
    }

    public Result newSeller() {
        return ok(newSeller.render(getStatusUserText()));
    }

    public Result allSU() {
        return ok(allSU.render(getStatusUserText()));
    }

    public Result allSellers() {
        return ok(allSellers.render(getStatusUserText()));
    }

    @Inject
    FormFactory formFactory;

    public Result getUsers() {
        return ok(Json.toJson(User.find.all()));
    }

    public Result getSellers() {
        return ok(Json.toJson(User.find.where().like("statusUser", "1").findList()));
    }

    public Result getSimpleUsers() {
        return ok(Json.toJson(User.find.where().like("statusUser", "0").findList()));
    }


    public Result getUser(Long id) {
        if(User.find.byId(id) == null) {
            return notFound("User does not exist.");
        }
        else {
            return ok(Json.toJson(User.find.byId(id)));
        }
    }


    public static String getStatusUserText() {
        if((request().cookies().get("id") !=null) && (request().cookies().get("token") !=null)){
            Long id = Long.valueOf(request().cookies().get("id").value());
            String token = request().cookies().get("token").value();
            //User user = User.find.where().like("id", id).like("token", token).findUnique();
            User user = User.find.byId(id);
            if (user.getToken().equals(token)) {
                if (user.getStatusUser().equals("0"))
                    return "SU";
                if (user.getStatusUser().equals("1"))
                    return "SC";
                if (user.getStatusUser().equals("2"))
                    return "Admin";
            }
        }
        return "None";
    }
    public Result deleteUser(Long id) {
        if(User.find.byId(id) == null) {
            return notFound("User does not exist.");
        }
        else {
            User.find.deleteById(id);
            return ok("The user has been succesfully deleted");
        }
    }

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
                users.get(0).setToken(token);
                // save the user with the new token in the database
                users.get(0).save();
                // get the status of the user to redirect him to the good homepage
                return ok(index.render(getStatusUserText())).withCookies(new Http.Cookie("token", token, 86400 , null, null, false, false)).withCookies(new Http.Cookie("id", users.get(0).getId().toString(), 86400 , null, null, false, false));
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
    public Result isConnected(Long id, String token) {
        //User user = User.find.where().like("id", String.valueOf(id)).like("token", token).findUnique();
        System.out.println("ID USER : "+id);
        System.out.println("TOKEN USER : "+token);
        User user = User.find.byId(id);
        System.out.println("USER : "+user);
        System.out.println("USER TOKEN: "+user.getToken());
        if(user.getToken().equals(token)) {
            return ok(Json.toJson(user));
        }else {
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
        Long id = Long.valueOf(values.get("id")[0]);
        String token = values.get("token")[0];
        System.out.println("Tentative logout");
        //LIKE -----------------------------------------------------------------------------------
        //User user = User.find.where().like("id", id).like("token", token).findUnique();
        User user = User.find.byId(id);
        if(user.getToken().equals(token)) {
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
        String status = "0";
        String password = BCrypt.hashpw(form.get("password")[0], BCrypt.gensalt());
        // check if email is not already used
        User user2 = User.find.where().like("email", email).findUnique();
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
        User userTest = User.find.where().like("email", email).findUnique();
        if (userTest != null)
            return notFound("Email already used");
        else {
            User user = new User(null, name, email, Integer.parseInt(numberAddress), streetAddress, cityAddress, Integer.parseInt(postCodeAddress), phoneNumber, password, siret, descriptionSeller, status, null);
            user.save();
            return created();
        }
    }

    /**
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

            return ok("The user has been updated");
        }

    }

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
            return notFound("User not found.");
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

            return ok("The user has been updated");
        }

    }

}

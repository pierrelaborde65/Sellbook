package controllers;

import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import views.html.index;
import views.html.register;

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
        return ok(index.render());
    }

    @Inject
    FormFactory formFactory;

    public Result addUser() {
        User user = formFactory.form(User.class).bindFromRequest().get();
        System.out.println(user);
        user.save();
        return redirect(routes.UserController.index());
    }

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
        // Get attribute from the form
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String email = values.get("email")[0];
        String password = values.get("password")[0];

        // Check if exist in the database
        List<User> isExist = User.find.where().like("email", "%"+email+"%").like("password", "%"+password+"%").findList();
        if(isExist.size() == 0) {
            return notFound("email or password incorrect");
        }
        else {
            // Generate random token
            String token = UUID.randomUUID().toString();
            // Set his token in the database
            isExist.get(0).setToken(token);
            isExist.get(0).save();
            return ok(Json.toJson(isExist.get(0)))
                    .withCookies(new Http.Cookie("token", token, null, "/", "localhost", false, false))
                    .withCookies(new Http.Cookie("id", isExist.get(0).getId().toString(), null, "/", "localhost", false, false));
        }

    }

    public Result register() {
        return ok(register.render());
    }

    /*
    public Result createUser() {
        // Get the value of the POST and stock in variable the values
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("name")[0];
        String email = values.get("email")[0];
        int numberAddress = Integer.parseInt(values.get("numberAddress")[0]);
        String streetAddress = values.get("streetAddress")[0];
        String cityAddress = values.get("cityAddress")[0];
        int postCodeAddress = Integer.parseInt(values.get("postCodeAddress")[0]);
        String phoneNumber = values.get("phoneNumber")[0];
        String password = values.get("password")[0];
        String siret = null;
        String descriptionSeller = null;
        int statusUser = 1;
        String token = null;

        // Test if the user is already in the database before the save, if no return 201 created, else return 409 conflict
        User userAlreadyExist = User.find.where().like("email", "%"+email+"%").findUnique();
        if(userAlreadyExist == null) {
            // Create the person in the database with the informations
            User user = new User(null, name, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, password, siret, descriptionSeller, statusUser, token);
            return ok(index.render());
        }
        else {
            return status(409, "This email is already used !");
        }
    }
    */


    public void initializeBD() {
        User SU = new User(null, "NameSU", "su@gmail.com", 13, "rue du Pic", "Barry", 65380, "0606060606", "passwordSU", null, null, 1, null);
        User SC = new User(null, "NameSC", "sc@gmail.com", 27, "rue de la rue", "Mtp", 34090, "0628801747", "passwordSC", null, null, 2, null);
        User ADMIN = new User(null, "NameAdmin", "admin@gmail.com", 8, "rue du boulevard", "ASA", 40800, "0607080910", "passwordADMIN", null, null, 3, null);
    }

}

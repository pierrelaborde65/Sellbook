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
        return ok(index.render(" "));
    }

    @Inject
    FormFactory formFactory;

    public Result addUser() {
        User user = formFactory.form(User.class).bindFromRequest().get();
        System.out.println(user);
        /*-------------VERIF SIRET*/
        if (user.getSiret()== null)
            user.setStatusUser(1);
        else
            user.setStatusUser(2);
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
        // Get information from the login form
        final Map<String, String[]> form = request().body().asFormUrlEncoded();
        String email = form.get("email")[0];
        String password = form.get("password")[0];

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
                int statusUser = users.get(0).getStatusUser();
                if (statusUser == 1){
                    // we redirect him to the Homepage of SU and we set 2 cookies to identify the user when he navigates on the app
                    return ok(homepageSU.render())
                            .withCookies(new Http.Cookie("token", token, 604800, "/", "localhost", false, false))
                            .withCookies(new Http.Cookie("id", users.get(0).getId().toString(), 604800, "/", "localhost", false, false));
                }
                else if (statusUser == 2){
                    // we redirect him to the Homepage of SC
                    return ok(homepageSC.render())
                            .withCookies(new Http.Cookie("token", token, 604800, "/", "localhost", false, false))
                            .withCookies(new Http.Cookie("id", users.get(0).getId().toString(), 604800, "/", "localhost", false, false));
                }
                else {
                    // we redirect him to the Homepage of admin
                    return ok(homepageAdmin.render())
                            .withCookies(new Http.Cookie("token", token, 604800, "/", "localhost", false, false))
                            .withCookies(new Http.Cookie("id", users.get(0).getId().toString(), 604800, "/", "localhost", false, false));
                }
            }
        }
    }

    public Result registerSU() {
        return ok(registerSU.render());
    }

    public Result registerSC() {
        return ok(registerSC.render());
    }

}

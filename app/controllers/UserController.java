package controllers;

import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;

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
        return ok(index.render("Hello world !"));
    }

    @Inject
    FormFactory formFactory;

    public Result addUser() {
        User user = formFactory.form(User.class).bindFromRequest().get();
        System.out.println(user);
        user.save();
        return redirect(routes.PersonController.index());
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
}

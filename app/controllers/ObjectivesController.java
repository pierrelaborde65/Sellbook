package controllers;

import com.google.inject.Inject;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.newObjectiveView;
import models.Objective;

import java.util.Map;

/**
 * Created by Fati on 13/11/2016.
 */
public class ObjectivesController extends Controller{
    public Result newObjective() {
        return ok(newObjectiveView.render("Diary Objectives"));
    }

    @Inject
    FormFactory formFactory;

    /** add an objective
     *
     * @return objective created
     */

    public Result addObjective() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String id = values.get("id")[0];
        String nameObjective = values.get("nameObjective")[0];
        String descriptionObjective = values.get("descriptionObjective")[0];


        System.out.println(nameObjective);
        System.out.println(descriptionObjective);

        Objective objective = new Objective(Integer.parseInt(id), Integer.parseInt(id),nameObjective, descriptionObjective);
        return created("The diary '" + nameObjective + "'has been created");
    }

    public Result getObjectives() {
        if (Objective.find.all().isEmpty()) {
            return notFound("No objective Found");
        }else {
            return ok(Json.toJson(Objective.find.all()));
        }
    }

// Get objective by id
    public Result getObjective(Long id) {
        if (Objective.find.byId(id) == null) {
            return notFound("This objective does not exist");
        } else {
            return ok(Json.toJson(Objective.find.byId(id)));
        }
    }

// Get Diary's objectives
    public Result getDiaryObjectives(Long id) {
        if (Objective.find.where().like("idDiary", id.toString()) == null) {
            return notFound("No Diary to show");
        } else {
            return ok(Json.toJson(Objective.find.where().like("idDiary", id.toString()).findList()));
    }
}
    // Delete objective
    public Result deleteObjective(Long id) {
        if(Objective.find.byId(id) == null) {
            return notFound("This objective does not exist");
        }
        else {
            Objective.find.deleteById(id);
            return ok("Objective deleted");
        }
    }
}
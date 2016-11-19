package controllers;


import com.google.inject.Inject;
import models.Diary;
import models.User;
import org.joda.time.DateTime;
import play.data.FormFactory;
import play.data.format.Formats;
import play.libs.Json;
import java.util.Date;
import java.util.Map;
import play.mvc.*;
import views.html.myDiaries;
import views.html.newDiaryView;
import views.html.updateDiary;

/**
 * Created by Fati on 11/11/2016.
 */
public class DiaryController extends Controller {
    public Result newDiary() {
        return ok(newDiaryView.render(UserController.getStatusUserText()));
    }

    public Result userDiaries() {
        return ok(myDiaries.render(UserController.getStatusUserText()));
    }

    @Inject
    FormFactory formFactory;

    /** add a diary
     *
     * @return add a diary
     */

    public Result addDiary() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String id = values.get("id")[0];
        String titleDiary = values.get("titleDiary")[0];
        String descriptionDiary = values.get("descriptionDiary")[0];
        String dateDiary = values.get("dateDiary")[0];

        System.out.println(titleDiary);
        System.out.println(descriptionDiary);
        System.out.println(dateDiary);
        Diary diary = new Diary(null,Long.parseLong(id),titleDiary, descriptionDiary, dateDiary);
        return created("The diary '" + titleDiary + "'has been created");
    }
    /** Get all diaries
     *
     * @return List of diaries or "No diary found"
     */

    public Result getDiaries() {
        if (Diary.find.all().isEmpty()) {
            return notFound("No diary Found");
        }else {
            return ok(Json.toJson(Diary.find.all()));
        }
    }

    /** Get a Diary by Id
     * @param id the id of a Diary
     * @return
     */
    public Result getDiary(Long id) {
        if (Diary.find.byId(id) == null) {
            return notFound("Wanted Diary does not exist");
        } else {
            return ok(Json.toJson(Diary.find.byId(id)));
        }
    }



    public Result getMyDiaries(Long id) {
        if (Diary.find.where().like("idUser", id.toString()) == null) {
            return notFound("No Diary to show");
        } else {
            return ok(Json.toJson(Diary.find.where().like("idUser", id.toString()).findList()));
        }
    }

    public Result showMyDiaries() {
        //@routes.DiaryController.getMyDiaries(Long.valueOf(request().cookies().get("id").value()))
        return ok(myDiaries.render(UserController.getStatusUserText()));
    }

    public Result showUpdateForm(Long id) {
        return ok(updateDiary.render(UserController.getStatusUserText()));
    }

    /** Update a Diary
     *
     * @return new diary
     */
    public Result updateDiary() {
        // Get the value of the form
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String idDiary = values.get("idDiary")[0];
        System.out.println(idDiary);
        long id = Long.parseLong(idDiary);
        String newTitle = values.get("newTitle")[0];
        String newDescription = values.get("newDescription")[0];

        if (Diary.find.byId(id) == null) {
            return notFound("Diary not found.");
        } else {
            Diary diary = Diary.find.byId(id);
            diary.setTitleDiary(newTitle);
            diary.setDescriptionDiary(newDescription);
            diary.save();
            return ok("The Diary has been updated");
        }

    }


    /** Delete a Diary
     *
     * @return Diary deleted
     */

    public Result deleteDiary(Long id) {
        if(Diary.find.byId(id) == null) {
            return notFound("Wanted Diary does not exist");
        }
        else {
            Diary.find.deleteById(id);
            return ok("This Diary has been deleted");
        }
    }


}

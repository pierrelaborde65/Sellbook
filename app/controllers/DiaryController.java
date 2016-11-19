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
     * @param id the id of a Product
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

    public Result showUpdateForm() {
        return ok(updateDiary.render(UserController.getStatusUserText()));
    }

    /** Update a Diary
     *
     * @return new diary
     */
    public Result updateDiary(Long id) {
        // Get the diary in the database, if not exist return 404 not found
        Diary updateDiary = Diary.find.byId(id);
        if(updateDiary == null) {
            return notFound("Diary not found");
        }
        // If exist, get the value of the form and do the update in the database
        else {
            // Get the value of the form
            final Map<String, String[]> values = request().body().asFormUrlEncoded();
            String newTitle = values.get("newTitle")[0];
            String newDescription = values.get("newDescription")[0];
            DateTime date =DateTime.now();
            String dateDiary=date.toString("dd/MM/yy HH:mm");
            // Test if not empty, make the update, else nothing
            if(!newTitle.isEmpty())
                updateDiary.setTitleDiary(newTitle);
            if(!newDescription.isEmpty())
                updateDiary.setDescriptionDiary(newDescription);
           // updateDiary.setDateDiary(dateDiary);
            updateDiary.save();
            return ok(Json.toJson(Diary.find.byId(id)));
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

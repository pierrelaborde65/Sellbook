package models;
import com.avaje.ebean.Model;
import javax.persistence.*;

/**
 * Created by Fati on 11/11/2016.
 */
@Entity
public class Diary extends com.avaje.ebean.Model {

    public static Model.Finder<Long, Diary> find = new Model.Finder<Long, Diary>(Diary.class);

    @Id @GeneratedValue
    public Long idDiary;

    //user's diary
    public Long idUser;

    // diary's title
    public String titleDiary;

    // diary's description
    public String descriptionDiary;

    // diary's date
    public String dateDiary;

    public Diary(){}

    public Diary(Long idDiary, Long idUser, String titleDiary, String descriptionDiary, String dateDiary){
        this.idDiary = idDiary;
        this.idUser = idUser;
        this.titleDiary = titleDiary;
        this.descriptionDiary = descriptionDiary;
        this.dateDiary = dateDiary;
        this.save();
    }

    public static Finder<Long,Diary> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, Diary> find) {
        Diary.find = find;
    }

    public Long getIdDiary() {
        return idDiary;
    }

    public void setId(Long id) {
        this.idDiary = id;
    }

    public String getTitleDiary() {
        return titleDiary;
    }

    public void setTitleDiary(String title) {this.titleDiary = title;}

    public String getDescriptionDiary() {
        return descriptionDiary;
    }

    public void setDescriptionDiary(String description) {this.descriptionDiary = description;}

    public String getDateDiary(){ return dateDiary;}
}

package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
/**
 * Created by Fati on 11/11/2016.
 */
@Entity
public class Objective extends com.avaje.ebean.Model {

    public static Model.Finder<Long, Objective> find = new Model.Finder<Long, Objective>(Objective.class);

    @Id
    @GeneratedValue
    public long idObjective;

    //Diary related to the objectif
    public long idDiary;

    // objective's name
    public String nameObjective;

    // objective's description
    public String descriptionObjective;

    public Objective(long idObjective, long idDiary, String nameObjective, String descriptionObjective){
        this.idObjective=idObjective;
        this.idDiary=idDiary;
        this.nameObjective=nameObjective;
        this.descriptionObjective=descriptionObjective;
    }

    public static Finder<Long,Objective> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, Objective> find) {
        Objective.find = find;
    }

    public Long getIdObjective() {
        return idObjective;
    }

    public void setId(Long id) {
        this.idObjective = id;
    }

    public String getNameObjective() {
        return nameObjective;
    }

    public void setNameObjective(String name) {
        this.nameObjective = name;
    }

    public String getDescriptionObjective() {
        return descriptionObjective;
    }

    public void setDescriptionObjective(String description) {
        this.descriptionObjective = description;
    }


}

package models;
import javax.persistence.*;

/**
 * Created by Pierre on 07/11/2016.
 */
public class Person extends com.avaje.ebean.Model {

    @Id
    public String id;

    public String name;
}

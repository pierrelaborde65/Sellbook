package models;
import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Pierre on 07/11/2016.
 */
@Entity
public class Person extends com.avaje.ebean.Model {

    public static Model.Finder<Long, Person> find = new Model.Finder<Long,Person>(Person.class);

    @Id @GeneratedValue
    public Long id;

    public String ok;

    public String name;
}

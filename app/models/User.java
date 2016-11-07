package models;
import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Pierre on 07/11/2016.
 */
@Entity
public class User extends com.avaje.ebean.Model {

    public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(User.class);

    @Id @GeneratedValue
    public Long id;

    public String name;
}

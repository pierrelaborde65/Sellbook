package models;
import javax.persistence.*;

/**
 * Created by Pierre on 07/11/2016.
 */
@Entity
public class Person extends com.avaje.ebean.Model {
    @Id @GeneratedValue
    public Long id;

    public String name;
}

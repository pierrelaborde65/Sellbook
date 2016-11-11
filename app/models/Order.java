package models;

import com.avaje.ebean.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Pierre on 11/11/2016.
 */
public class Order extends com.avaje.ebean.Model {

    public static Model.Finder<Long, Order> find = new Model.Finder<Long, Order>(Order.class);

    @Id
    @GeneratedValue
    Long idOrder;
    String dateOrder;
    String stateOrder;
    @ManyToOne
    Long idUser;

    // Constructors
    public Order() {
    }

    public Order(Long idOrder, String dateOrder, String stateOrder, Long idUser) {
        this.idOrder = idOrder;
        this.dateOrder = dateOrder;
        this.stateOrder = stateOrder;
        this.idUser = idUser;
    }

    // getters and setters

    public static Finder<Long, Order> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, Order> find) {
        Order.find = find;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getStateOrder() {
        return stateOrder;
    }

    public void setStateOrder(String stateOrder) {
        this.stateOrder = stateOrder;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }


}

package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by Pierre on 11/11/2016.
 */
@Entity
@Table(name = "Order_Sellbook")
public class Order extends com.avaje.ebean.Model {

    public static Model.Finder<Long, Order> find = new Model.Finder<Long, Order>(Order.class);

    @Id @GeneratedValue Long idOrder;

    @ManyToOne
    Long idUser;
    @ManyToOne
    Long idSeller;
    @ManyToOne
    Long idProduct;

    // Date of the Order
    String dateOrder;

    // State of the Order (Check payment / Notified to SC / Sent)
    String stateOrder;

    // Quantity of the product ordered
    int quantityOrder;

    // Price for the Product
    float priceOrder;



    // Empty Constructors
    public Order() {
    }

    /**
     * Order Constructor
     * @param idOrder
     * @param idUser
     * @param idSeller
     * @param idProduct
     * @param dateOrder
     * @param quantityOrder
     * @param priceOrder
     * @param stateOrder
     */
    public Order(Long idOrder, Long idUser, Long idSeller, Long idProduct, String dateOrder, int quantityOrder, float priceOrder, String stateOrder) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idSeller = idSeller;
        this.idProduct = idProduct;
        this.dateOrder = dateOrder;
        this.quantityOrder = quantityOrder;
        this.priceOrder = priceOrder;
        this.stateOrder = stateOrder;
        this.save();
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

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public float getPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(float priceOrder) {
        this.priceOrder = priceOrder;
    }

    public Long getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(Long idSeller) {
        this.idSeller = idSeller;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }
}

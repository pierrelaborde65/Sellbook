package models;
import com.avaje.ebean.Model;
import javax.persistence.*;

/**
 * Created by Johannes on 07/11/2016.
 */
@Entity
public class Product extends com.avaje.ebean.Model {

    public static Model.Finder<Long, Product> find = new Model.Finder<Long, Product>(Product.class);

    @Id @GeneratedValue
    public Long idProduct;

    //Product's Seller
    public String idSeller;

    //name of Product
    public String nameProduct;

    /* Description of Product */
    public String descriptionProduct;

    //price of Product
    public float priceSeller;

    //Quantity in stock
    public int quantityStock;

    //image
    public String imageProduct;



    public Product(){


    }

    public Product(Long idProduct, String idSeller, String name, String descProd, float price, int qtyStock/*, String image*/){
        this.idProduct = idProduct;
        this.idSeller = idSeller;
        this.nameProduct = name;
        this.descriptionProduct = descProd;
        this.priceSeller = price;
        this.quantityStock = qtyStock;
        //this.imageProduct = image;
        this.save();
    }




    /*
    public save(){

    }
    */

}

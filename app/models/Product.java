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

    //Product's Seller id
    public String idSeller;

    // name of the Product
    public String nameProduct;

    // description of the Product
    public String descriptionProduct;

    // price of the Product
    public float priceSeller;

    // Quantity in stock for the Product
    public int quantityStock;

    // image for the Product
    public String imageProduct;


    // Empty Constructor
    public Product(){


    }

    // Constructor
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

}

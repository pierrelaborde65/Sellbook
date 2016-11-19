package models;
import com.avaje.ebean.Model;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
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

    // lines of Shopping Cart for a Product
    @OneToMany(mappedBy = "referenceProduct")
    @JsonManagedReference
    List<ProductInShoppingCart> shoppingCart = new ArrayList<ProductInShoppingCart>();


    // Empty Constructor
    public Product(){

    }

    /**
     * CONSTRUCTOR
     * @param idProduct
     * @param idSeller
     * @param name
     * @param descProd
     * @param price
     * @param qtyStock
     * @param shoppingCart
     */
    public Product(Long idProduct, String idSeller, String name, String descProd, float price, int qtyStock/*, String image*/,List<ProductInShoppingCart> shoppingCart){
        this.idProduct = idProduct;
        this.idSeller = idSeller;
        this.nameProduct = name;
        this.descriptionProduct = descProd;
        this.priceSeller = price;
        this.quantityStock = qtyStock;
        //this.imageProduct = image;
        this.save();
    }

    // GETTERS & SETTERS
    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(String idSeller) {
        this.idSeller = idSeller;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public float getPriceSeller() {
        return priceSeller;
    }

    public void setPriceSeller(float priceSeller) {
        this.priceSeller = priceSeller;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public List<ProductInShoppingCart> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ProductInShoppingCart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}

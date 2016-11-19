package models;
import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
/**
 * Created by meier on 18/11/2016.
 */
@Entity
public class ProductInShoppingCart extends Model {

        public static Model.Finder<Long, ProductInShoppingCart> find = new Model.Finder<Long,ProductInShoppingCart>(ProductInShoppingCart.class);



        @Id @GeneratedValue Long id;

        // Quantity in Shopping Cart line
        int quantity;

        // User who own the Shopping cart line
        @ManyToOne @JsonBackReference User referenceUser;

        // Product concerned by the Shopping cart line
        @ManyToOne @JsonBackReference Product referenceProduct;

    /**
     * CONSTRUCTOR
     * @param id
     * @param quantity
     * @param referenceUser
     * @param referenceProduct
     */
        public ProductInShoppingCart(Long id, int quantity, User referenceUser, Product referenceProduct) {
            this.id = id;
            this.quantity = quantity;
            this.referenceUser = referenceUser;
            this.referenceProduct = referenceProduct;
            this.save();
        }

        // GETTERS & SETTERS

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public User getReferenceUser() {
        return referenceUser;
    }

        public void setReferenceUser(User referenceUser) {
        this.referenceUser = referenceUser;
    }

        public Product getReferenceProduct() {
        return referenceProduct;
    }

        public void setReferenceProduct(Product referenceProduct) {
        this.referenceProduct = referenceProduct;
    }
}

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
 * Created by Pierre on 07/11/2016.
 */
@Entity
@Table(name = "User_Sellbook")
public class User extends com.avaje.ebean.Model {

    public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(User.class);

    @Id @GeneratedValue
    Long id;

    // Name of the User
    String name;

    // Email of the User
    @Column(unique=true)
    String email;

    // Number Address of the User
    int numberAddress;

    // Street Address of the User
    String streetAddress;

    // City Address of the User
    String cityAddress;

    // Postcode of the User
    int postCodeAddress;

    // Phone number of the User
    String phoneNumber;

    // Password of the User
    String password;

    // Siret of the User (Seller)
    String siret;

    // Description of the Seller
    String descriptionSeller;

    // Status of the User (0 : SU / 1 : SC / 2 : Admin)
    String statusUser;

    // Token (Cookie) of the User
    String token;

    // Shopping Cart lines of the User
    @OneToMany(mappedBy = "referenceUser")
    @JsonManagedReference
    List<ProductInShoppingCart> shoppingCart = new ArrayList<ProductInShoppingCart>();

    // Empty Constructor
    public User() {
    }

    /**
     * CONSTRUCTOR
     * @param id
     * @param name
     * @param email
     * @param numberAddress
     * @param streetAddress
     * @param cityAddress
     * @param postCodeAddress
     * @param phoneNumber
     * @param password
     * @param siret
     * @param descriptionSeller
     * @param statusUser
     * @param token
     * @param shoppingCart
     */
    public User(Long id, String name, String email, int numberAddress, String streetAddress, String cityAddress, int postCodeAddress, String phoneNumber, String password, String siret, String descriptionSeller, String statusUser, String token, List<ProductInShoppingCart> shoppingCart) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.numberAddress = numberAddress;
        this.streetAddress = streetAddress;
        this.cityAddress = cityAddress;
        this.postCodeAddress = postCodeAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.siret = siret;
        this.descriptionSeller = descriptionSeller;
        this.statusUser = statusUser;
        this.token = token;
        this.shoppingCart = shoppingCart;
        this.save();
    }

    // GETTERS & SETTERS

    public static Finder<Long, User> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, User> find) {
        User.find = find;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberAddress() {
        return numberAddress;
    }

    public void setNumberAddress(int numberAddress) {
        this.numberAddress = numberAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public int getPostCodeAddress() {
        return postCodeAddress;
    }

    public void setPostCodeAddress(int postCodeAddress) {
        this.postCodeAddress = postCodeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password)/* throws AppException */{
       /*
        if (password == null) {
            throw new AppException("empty.password");
        }*/
        this.password = password;
                //BCrypt.hashpw(password, BCrypt.gensalt());
        //this.password = password;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getDescriptionSeller() {
        return descriptionSeller;
    }

    public void setDescriptionSeller(String descriptionSeller) {
        this.descriptionSeller = descriptionSeller;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ProductInShoppingCart> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ProductInShoppingCart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addToShoppingCart(ProductInShoppingCart product){
        this.shoppingCart.add(product);
    }


}

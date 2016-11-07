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
    Long id;
    String name;
    @Column(unique=true)
    String email;
    int numberAddress;
    String streetAddress;
    String cityAddress;
    int postCodeAddress;
    String phoneNumber;
    String password;
    String siret;
    String descriptionSeller;
    int statusUser;
    String token;

    public User() {
    }

    public User(Long id, String name, String email, int numberAddress, String streetAddress, String cityAddress, int postCodeAddress, String phoneNumber, String password, String siret, String descriptionSeller, int statusUser, String token) {
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
    }

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

    public void setPassword(String password) {
        this.password = password;
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

    public int getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(int statusUser) {
        this.statusUser = statusUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

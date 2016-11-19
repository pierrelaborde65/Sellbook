package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import models.Product;
import models.User;
import models.Order;
import models.ProductInShoppingCart;
import controllers.UserController;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import java.util.Date;
import views.html.*;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Map;

import static tyrex.util.Configuration.console;
/**
 * Created by Pierre on 11/11/2016.
 */
public class OrderController extends Controller{
    @Inject
    FormFactory formFactory;



    public Result order() {
        return ok(order.render(UserController.getStatusUserText()));
    }


    public Result orderAdmin() {
        return ok(orderAdmin.render(UserController.getStatusUserText()));
    }

    public Result myOrderSeller() {
        return ok(orderSeller.render(UserController.getStatusUserText()));
    }

    /**
     * Order creation
     * @param id
     * @return IF enough quantity (quantity stock >= quantity orderer) : 200 - OK
     * ELSE 422 - "Not enough products"
     */
    public Result makeOrder(Long id) {
        ProductInShoppingCart Linecart = ProductInShoppingCart.find.byId(id);

        //Checking


        Product product = Product.find.byId(Linecart.getReferenceProduct().getIdProduct());
        if((product.getQuantityStock()- Linecart.getQuantity())>=0) {
            //reduce quantiy product
            product.setQuantityStock(product.getQuantityStock() - Linecart.getQuantity());

            String format = "dd/MM/yy H:mm:ss";
            SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
            Date date = new java.util.Date();
            Order order = new Order(null,
                    Linecart.getReferenceUser().getId(),
                    Long.valueOf(Linecart.getReferenceProduct().getIdSeller()),
                    Linecart.getReferenceProduct().getIdProduct(),
                    formater.format(date),
                    Linecart.getQuantity(),
                    Linecart.getReferenceProduct().getPriceSeller(),
                    "check payment"
            );

            product.save();
            return ok(Json.toJson(order));
        }else{
            return status(422,"Not enough products");
        }
    }

    /**
     * Get Order by User ID
     * @param idUser
     * @return IF user does not exist : 404 - "User does not exist."
     * ELSE 200 - OK
     */
    public Result getOrderUser(Long idUser) {
        User user = User.find.byId(idUser);
        List <Order> ordersUser = Order.find.where().like("idUser", idUser.toString()).findList();
        if(user == null) {
            return notFound("User does not exist.");
        }
        else {
            ArrayNode orders = Json.newArray();
            for (int i = 0; i< ordersUser.size(); i++) {
                User seller = User.find.byId(ordersUser.get(i).getIdSeller());
                Product product = Product.find.byId(ordersUser.get(i).getIdProduct());
                ObjectNode order = Json.newObject();
                order.put("id", ordersUser.get(i).getIdOrder());
                order.put("date", ordersUser.get(i).getDateOrder());
                order.put("nameProduct", product.getNameProduct());
                order.put("nameSeller", seller.getName());
                order.put("descriptionProduct",product.getDescriptionProduct());
                order.put("price",ordersUser.get(i).getPriceOrder()) ;
                order.put("quantity",ordersUser.get(i).getQuantityOrder()) ;
                order.put("state", ordersUser.get(i).getStateOrder());
                orders.add(order);
            }
            return ok(orders);
        }

    }

    public Result getAllOrderWithState(String state) {
        List <Order> ordersAdmin = Order.find.where().like("stateOrder", state).findList();
        if(ordersAdmin == null) {
            return notFound("There is no order ");
        }
        else {
            ArrayNode orders = Json.newArray();
            for (int i = 0; i< ordersAdmin.size(); i++) {
                User seller = User.find.byId(ordersAdmin.get(i).getIdSeller());
                Product product = Product.find.byId(ordersAdmin.get(i).getIdProduct());
                ObjectNode order = Json.newObject();
                order.put("id", ordersAdmin.get(i).getIdOrder());
                order.put("date", ordersAdmin.get(i).getDateOrder());
                order.put("nameProduct", product.getNameProduct());
                order.put("nameSeller", seller.getName());
                order.put("descriptionProduct",product.getDescriptionProduct());
                order.put("price",ordersAdmin.get(i).getPriceOrder()) ;
                order.put("quantity",ordersAdmin.get(i).getQuantityOrder()) ;
                order.put("state", ordersAdmin.get(i).getStateOrder());
                orders.add(order);
            }
            return ok(orders);
        }

    }


    public Result updateState(Long idOrder, String state){
        Order order = Order.find.byId(idOrder);
        order.setStateOrder(state);
        order.save();
        return ok("State updated");

    }

    public Result declinedOrder(Long idOrder){
        Order order = Order.find.byId(idOrder);
        //maj quantity
        Product product = Product.find.byId(order.getIdProduct());
        product.setQuantityStock(product.getQuantityStock()+ order.getQuantityOrder());
        product.save();

        Order.find.deleteById(idOrder);
        return ok("order declined");

    }

    public Result getOrdersSeller(Long idSeller){
        List <Order> ordersSeller = Order.find.where().like("idSeller", idSeller.toString()).findList();

        if(ordersSeller == null) {
            return notFound("There is no order ");
        }
        else {
            ArrayNode orders = Json.newArray();
            for (int i = 0; i< ordersSeller.size(); i++) {
                User buyer = User.find.byId(ordersSeller.get(i).getIdUser());

                Product product = Product.find.byId(ordersSeller.get(i).getIdProduct());
                ObjectNode order = Json.newObject();
                order.put("id", ordersSeller.get(i).getIdOrder());
                order.put("date", ordersSeller.get(i).getDateOrder());
                order.put("nameProduct", product.getNameProduct());
                order.put("nameBuyer",buyer.getName() );
                order.put("descriptionProduct",product.getDescriptionProduct());
                order.put("price",ordersSeller.get(i).getPriceOrder()) ;
                order.put("quantity",ordersSeller.get(i).getQuantityOrder()) ;
                order.put("state", ordersSeller.get(i).getStateOrder());
                orders.add(order);
            }
            return ok(orders);
        }
    }


}

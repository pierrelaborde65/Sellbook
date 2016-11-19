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
            return notFound("Not enough products");
        }
    }

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

}

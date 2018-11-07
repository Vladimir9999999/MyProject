package utils;

import Models.QrCode;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityAccountUsers;
import spring.entity.EntityOrders;
import spring.interfaces.OrderDao;

import java.util.HashMap;

public class QrHandler {

    private QrCode code;
    private WebApplicationContext ctx;
    private OrderDao orderDao;

    public QrHandler(QrCode code, WebApplicationContext ctx) {

        this.code = code;
        this.ctx = ctx;

       orderDao = ctx.getBean("jpaOrder", OrderDao.class);
    }

    public String readUser(long userId) {
        String ret;


        ShopUserAdder adder = new ShopUserAdder(ctx);

        long shopId = code.getHost();
        ret = adder.addShop(shopId, userId);

        return ret;

    }

    public String readService(long shopId) {
        String ret;
            long userId = code.getHost();
            ShopUserAdder adder = new ShopUserAdder(ctx);
            ret = adder.addShop(shopId, userId);

            return ret;

    }
    HashMap<Long, QrCode> referals = new HashMap<>();
    public void addPartnerUser(long phone){

        referals.put(phone,code);

    }

    public String addUserToOrder(long shopId, long orderId) {

        EntityOrders entityOrders = orderDao.findById(orderId);

        if (entityOrders != null && entityOrders.getShopId() == shopId & entityOrders.getAccountUsers().getId() == 0) {

                long userId = code.getHost();

            EntityAccountUsers entityAccountUsers = new EntityAccountUsers();
            entityAccountUsers.setId(userId);
                entityOrders.setAccountUsers(entityAccountUsers);

                orderDao.save(entityOrders);

                return "ok";

            } else {
                return "неизвестный case";
            }

    }
}

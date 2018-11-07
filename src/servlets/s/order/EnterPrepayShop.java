package servlets.s.order;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import servlets.ShopServletService;
import spring.entity.EntityOrders;
import spring.interfaces.OrderDao;
import utils.OrdersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EnterPrepayShop"
,urlPatterns = "/enter.prepay")

public class EnterPrepayShop extends HttpServlet {
    String c = "prep_sh";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        long shopId;
        float prepay = 0.0F;
        long orderId = 0;
        int payMethod;

        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            prepay = sSS.getRequestJ().getFloat("prepay");
            payMethod = sSS.getRequestJ().getInt("prepay_type");
            orderId = sSS.getRequestJ().getInt("order_id");

        } else {

            return;

        }

        if(prepay <= 0){

            sSS.getBody().put("error","Некорректная сумма!");
            sSS.finalize(response);
            return;

        }

        OrderDao orderDao = ctx.getBean("jpaOrder", OrderDao.class);

        EntityOrders entityOrders = orderDao.findById(orderId);

        boolean flag = true; /*

        todo нам оказывается пофиг куда вносить предоплату Карл!!!!
        (entityOrders != null) && (shopId == entityOrders.getShopId());

        flag = flag && entityOrders.getStatus() != EntityOrders.STATUS_COMPLETE;
        flag = flag && entityOrders.getStatus() != EntityOrders.STATUS_CANCEL;
        flag = flag && entityOrders.getStatus() != EntityOrders.STATUS_REFUNDED;
        */



        if (flag) {

            OrdersService ordersService = new OrdersService(shopId,ctx);
            sSS.setStatus(ordersService.enterPrepay(ctx,entityOrders,prepay,payMethod));

        } else {

            sSS.getStatus().put("error", "Ордер не найден или не активен");
            sSS.finalize(response);

        }
        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

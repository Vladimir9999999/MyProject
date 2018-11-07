package servlets.s.order;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.NewShopServletService;
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

@WebServlet(name = "OrderDelet",
        urlPatterns = "/delete.order")
public class OrderDelete extends HttpServlet {
    String c = "del_ordr";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        NewShopServletService sSS = new NewShopServletService(ctx);

        long shopId;
        long orderId;


        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            orderId = sSS.getRequestJ().getLong("order_id");
        } else {

            return;

        }

        OrderDao orderDao = ctx.getBean("jpaOrder",OrderDao.class);
        EntityOrders order = orderDao.findById(orderId);

        if(order == null || order.getShopId() !=shopId){

            sSS.getStatus().put("error","Ордер не найден");
            sSS.finalize(response);
            return;

        }

        OrdersService ordersService = new OrdersService(ctx);
        double refundSumm  = ordersService.deleteOrder(ctx,order);
        double refundCash = order.getPrepay()-refundSumm;

        JSONObject orderJ = new JSONObject();

        orderJ.put("refund_cb",refundSumm);
        orderJ.put("refund_cash",refundCash);
        orderJ.put("deleted",order.getId());

        sSS.addPropertyObject("orders",orderJ);

        sSS.getStatus().put("error","ok");

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

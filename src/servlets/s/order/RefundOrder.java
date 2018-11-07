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

@WebServlet(name = "RefundOrder",
urlPatterns = "/refund.order")
public class RefundOrder extends HttpServlet {
    private final static String c = "rfnd_ordr";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long shopId;

        long orderId = 0;

        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {
            sSS.getStatus().put("case",c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            orderId = sSS.getRequestJ().getInt("order_id");

        } else {

            return;

        }
        OrderDao orderDao = ctx.getBean("jpaOrder",OrderDao.class);
        EntityOrders order = orderDao.findById(orderId);

        if(order == null && order.getShopId() !=shopId){
            sSS.getStatus().put("error","Ордер не найден");
            sSS.finalize(response);
            return;
        }

        OrdersService ordersService = new OrdersService(ctx);
        double refundSumm  = ordersService.refundCashback(ctx,order);
        double refundCash  =order.getPrepay()-refundSumm;
        sSS.getBody().put("refund_cb",refundSumm);
        sSS.getBody().put("refund_cash",refundCash);
        sSS.getStatus().put("error","ok");

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

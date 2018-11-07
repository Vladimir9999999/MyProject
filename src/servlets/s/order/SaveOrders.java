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

@WebServlet(name = "SaveOrders"
            ,urlPatterns = "/save.order")

public class SaveOrders extends HttpServlet {

    String c = "sav_order";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tocken;
        long shopId;

        Integer length;
        Double summ;
        Double prepay;
        Boolean favorite;
        Integer status;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            tocken = sSS.getRequestJ().getString("tocken");
            shopId = sSS.getRequestJ().getLong("shop_id");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        OrderDao orderDao = ctx.getBean("jpaOrder", OrderDao.class);

        EntityOrders orders;

        if (sSS.getRequestJ().has("id")) {

            orders = orderDao.findById(sSS.getRequestJ().getLong("id"));

            if (orders == null) {

                sSS.getStatus().put("error", "Запись не найдена");
                sSS.finalize(response);
                return;
            }

        } else {
            orders = new EntityOrders();
        }

        if (sSS.getRequestJ().has("length")) {
            length = sSS.getRequestJ().getInt("length");
            orders.setLength(length);
        }

        if (sSS.getRequestJ().has("summ")) {
            summ = sSS.getRequestJ().getDouble("summ");
            orders.setSumm(summ);
        }

        if (sSS.getRequestJ().has("prepay")) {

            prepay = sSS.getRequestJ().getDouble("prepay");
            orders.setPrepay(prepay);

                OrdersService ordersService = new OrdersService(shopId,ctx);
                //sSS.setStatus(ordersService.enterPrepay(ctx,orders, prepay, payMethod));

        }

        if (sSS.getRequestJ().has("status")) {
            status = sSS.getRequestJ().getInt("status");
            orders.setStatus(status);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}

package servlets.s.order;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.NewShopServletService;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.*;
import utils.OrdersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FinalyOrder", urlPatterns = "/finally.order")
public class FinalyOrder extends HttpServlet {
    private static final String c = "fin_ordr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        long shopId;
        float tPay =0;
        float cPay =0;
        long orderId;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        NewShopServletService sSS = new NewShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            if(sSS.getRequestJ().has("terminal")) {
                tPay = sSS.getRequestJ().getFloat("terminal");
            }

            if(sSS.getRequestJ().has("cash")) {
                cPay = sSS.getRequestJ().getFloat("cash");
            }

            orderId = sSS.getRequestJ().getInt("order_id");

        } else {

            return;

        }

        OrderDao orderDao = ctx.getBean("jpaOrder", OrderDao.class);

        EntityOrders entityOrders = orderDao.findById(orderId);


        if ((entityOrders != null) && (shopId == entityOrders.getShopId())) {

            if(entityOrders.getStatus() != EntityOrders.STATUS_NEW){
                sSS.getStatus().put("case",c);
                sSS.getBody().put("error", "order уже завершен");
                sSS.finalizeShop(response);
                return;
            }

            OrdersService ordersService = new OrdersService(shopId,ctx);
            JSONObject statusCb = null;
            JSONObject statusC = null;

            double summ = entityOrders.getPrepay() + tPay + cPay;

            if (summ < entityOrders.getSumm()) {
                sSS.getStatus().put("error", "Для закрытия ордера недостаточно средств");
                sSS.finalizeShop(response);
                return;
            }
            if (tPay > 0) {

                statusCb = ordersService.enterPrepay(ctx, entityOrders, tPay, EntityBills.PAY_METHOD_TERMINAL);

            }

            boolean payTIsOk = (statusCb == null || statusCb.getString("error").equals("ok"));

            if (!payTIsOk) {

                sSS.setStatus(statusCb);
                sSS.getStatus().put("case",c);
                sSS.finalize(response);
                return;

            }

            if (cPay > 0) {

                statusC = ordersService.enterPrepay(ctx, entityOrders, cPay, EntityBills.PAY_METHOD_CASH);
             }

            boolean payCIsOk = statusC == null || statusC.getString("error").equals("ok");

            if (!payCIsOk) {

                sSS.setStatus(statusC);
                sSS.getStatus().put("case",c);
                sSS.finalizeShop(response);
                return;

            }

            if(entityOrders.getAccountUsers() != null && entityOrders.getAccountUsers().getId() >0) {
                ordersService.addCashBack(ctx, entityOrders);
                addRefer();
                expandFunnel();
            }
            JSONObject finaly = new JSONObject();
            finaly.put("final","ok");

            sSS.addPropertyObject("orders",finaly);

            sSS.getStatus().put("error","ok");
            sSS.getStatus().put("case",c);
            entityOrders.setStatus(EntityOrders.STATUS_COMPLETE);
            orderDao.save(entityOrders);

        } else {

            JSONObject finaly = new JSONObject();
            finaly.put("final","ok");

            sSS.addPropertyObject("orders",finaly);

            sSS.getStatus().put("error", "Не могу найти ордер");
            sSS.finalize(response);

        }
        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }



    private boolean addRefer(){
        return true;
    }

    private boolean expandFunnel(){
        return true;
    }
}

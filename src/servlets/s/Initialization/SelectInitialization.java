package servlets.s.Initialization;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.buyer.my_service.BuyerServletService;
import servlets.s.order.my_service.OrderServletService;
import spring.entity.*;
import spring.interfaces.*;
import utils.OrdersService;
import utils.PriceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SelectInitialization"
           ,urlPatterns = "/select.initialization")

public class SelectInitialization extends HttpServlet {

    private String c = "sel_init";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        final int NEW_ORDERS = 50;
        int offSet = 0;

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case",c);

        }else {

            return;
        }


        PriceService priceService = new PriceService(ctx);

        JSONObject priceJ = priceService.getPriceShop(ctx,shopId);

        sSS.setBody(priceJ);

        OrderServletService oSS = new OrderServletService();
        int newOrders = 50;



                Timestamp thisTime  = new Timestamp(new Date().getTime());
                OrderDao orderDao = ctx.getBean("jpaOrder",OrderDao.class);

                List<EntityOrders> myOrders = orderDao.findNewByStatus(thisTime,EntityOrders.STATUS_NEW,offSet,newOrders);

                OrdersService ordersService = new OrdersService(ctx);

                JSONArray orders  = ordersService.ordersToJsonShop(ctx,myOrders);

        sSS.getBody().put("orders",orders);


        EmployeeDao employeeDao = ctx.getBean("jpaEmployee",EmployeeDao.class);

        EntityEmployee employee = employeeDao.selectById(ShopServletService.DEFAULT_EMPLOYEE);

        TurnDao turnDao = ctx.getBean("jpaTurn",TurnDao.class);

        JSONArray jsonArray = new JSONArray(employee.getScheduleByScheduleId().getValue());

        JSONObject turn = jsonArray.getJSONObject(0);

        EntityTurn entityTurn = turnDao.selectById(turn.getInt("turn_id"));

        assert entityTurn != null;

        sSS.getBody().put("turn",entityTurn.toJsonObject());

        UserShopDao userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);

        List<EntityUserShops> listUserShops = userShopDao.findByShop(shopId);

        if (listUserShops.size() != 0) {

            BuyerServletService bSS = new BuyerServletService(ctx);

            JSONArray buyerArrayJ = bSS.getBayerArrayJ(listUserShops);

            if (buyerArrayJ.length() != 0) {

                sSS.getBody().put("buyers", buyerArrayJ);
            }
        }

        sSS.getBody().put("road_time",ShopServletService.roadTime);

        sSS.getStatus().put("error","ok").put("server_time",new Date().getTime());

        sSS.finalizeShop(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}

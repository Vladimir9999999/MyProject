package servlets.s.order;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.order.my_service.OrderServletService;
import spring.entity.EntityOrders;
import spring.entity.EntityTaskList;
import spring.interfaces.OrderDao;
import spring.interfaces.TaskListDao;
import utils.OrdersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderSelectShop"
                ,urlPatterns = "/select.orders")

public class OrderSelectShop extends HttpServlet {

    String c = "slct_ordr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        long userId = 0;
        String tocken;

        int completeOrders = 0;
        int newOrders = 0;
        int offSet = 0;
        int overdue = 0;
        Date lastUpdate = null;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);

            shopId = sSS.getRequestJ().getLong("shop_id");

            if(sSS.getRequestJ().has("offset")) {

                offSet = sSS.getRequestJ().getInt("offset");

                if (offSet < 0) {

                    sSS.getStatus().put("error", "Некорректное количество offset");
                    sSS.finalize(response);
                    return;
                }

            }


            if(sSS.getRequestJ().has("last_update")){

                lastUpdate = new Date(sSS.getRequestJ().getLong("last_update"));

            }

            if(sSS.getRequestJ().has("overdue")){

                overdue = sSS.getRequestJ().getInt("overdue");

                if(overdue <= 0) {

                    sSS.getStatus().put("error", "Некорректное количество overdue");
                    sSS.finalize(response);
                    return;
                }
            }

            if (sSS.getRequestJ().has("complete")) {

                completeOrders = sSS.getRequestJ().getInt("complete");

                if (completeOrders <= 0) {

                    sSS.getStatus().put("error", "Некорректное количество complete");
                    sSS.finalize(response);
                    return;
                }


            }

            if (sSS.getRequestJ().has("new")) {

                newOrders = sSS.getRequestJ().getInt("new");

                if (newOrders <= 0) {

                    sSS.getStatus().put("error", "Некорректное количество new");
                    sSS.finalize(response);
                    return;
                }
            }

        } else {

            return;
        }


        OrderServletService oSS = new OrderServletService();

        OrderDao orderDao = ctx.getBean("jpaOrder",OrderDao.class);

        TaskListDao taskListDao = ctx.getBean("jpaTaskList",TaskListDao.class);




        List<EntityTaskList> lastListTask = new ArrayList<>();
        List<EntityOrders> myOrders = null;
        JSONArray lastOrderArrayJ = null;

        if (completeOrders > 0) {

            if(lastUpdate == null) {

                //myOrders = orderDao.findByStatus(EntityOrders.STATUS_COMPLETE,offSet,completeOrders);

                OrdersService ordersService = new OrdersService(ctx);

                lastOrderArrayJ = ordersService.ordersToJsonShop(ctx,myOrders);

            }
        }

        if(newOrders > 0) {

            if(lastUpdate == null) {


                Timestamp thisTime  = new Timestamp(new Date().getTime());

                myOrders = orderDao.findNewByStatus(thisTime,EntityOrders.STATUS_NEW,offSet,newOrders);

                OrdersService ordersService = new OrdersService(ctx);

                lastOrderArrayJ = ordersService.ordersToJsonShop(ctx,myOrders);
            }
        }

        if(overdue > 0) {

            if(lastUpdate == null ){




                Timestamp thisTime  = new Timestamp(new Date().getTime());
                myOrders = orderDao.findOldByStatus(thisTime,EntityOrders.STATUS_NEW,offSet,overdue);

                OrdersService ordersService = new OrdersService(ctx);

                lastOrderArrayJ = ordersService.ordersToJsonShop(ctx,myOrders);

            }
        }

        sSS.getBody().put("orders", lastOrderArrayJ);

        sSS.getStatus().put("error","ok").put("server_time",new Date().getTime());
        sSS.finalize(response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

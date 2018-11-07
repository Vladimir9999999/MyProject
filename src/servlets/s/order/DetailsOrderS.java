package servlets.s.order;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.*;
import utils.OrdersService;
import utils.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DetailsOrderS",
urlPatterns = "/detail.order")
public class DetailsOrderS extends HttpServlet {

    String c = "det_ordr";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long orderId;
        long shopId;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);


        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);

            orderId = sSS.getRequestJ().getLong("order_id");


            shopId = sSS.getRequestJ().getLong("shop_id");

        } else {

            return;

        }

        OrderDao orderDao = ctx.getBean("jpaOrder",OrderDao.class);
        EntityOrders order = orderDao.findById(orderId);

        if(order == null || order.getShopId() != shopId) {
            sSS.getStatus().put("error", "Заказ не найден");
            sSS.finalizeShop(response);
            return;
        }
        UserService userService = new UserService(ctx);
        JSONObject userData = userService.getUserData(order);
        JSONArray transactions = null;
        if(order.getPrepay()>0){
             transactions = getPayment(ctx,orderId);
        }

        sSS.getStatus().put("error","ok");
        if(order.getAccountUsers().getId()!=0){

            sSS.getBody().put("user_data",userData);
            sSS.getBody().put("message_client",getMesasageClient(ctx,orderId,shopId));
            sSS.getBody().put("remark_to_client", getRemarkClient(ctx,order.getAccountUsers().getId(),shopId));

        }

        sSS.getBody().put("transactions",transactions);
        sSS.getBody().put("create_date", order.getLastModification());
        sSS.getBody().put("remark_to_order",getRemarkOrder(ctx,orderId,shopId));

        sSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }

    private JSONArray getPayment(WebApplicationContext ctx, long orderId){

        JSONArray transactionsJ = new JSONArray();
        BillsDao billsDao = ctx.getBean("jpaBills",BillsDao.class);
        List<EntityBills> billsList = billsDao.selectByOrderId(orderId);
        TransactionDao transactionDao = ctx.getBean("jpaTransactions",TransactionDao.class);

        for (EntityBills bills : billsList){
            EntityTransactions transaction = bills.getTransactionsByTranzaction();
            JSONObject transactionJ = new JSONObject();
            transactionJ.put("date",transaction.getDete());

            if(transaction.getSender() == null){

                transactionJ.put("summ",transaction.getSumm()*-1);

            }else{

                transactionJ.put("summ",transaction.getSumm());

            }

            transactionJ.put("type",transaction.getCurrency());
            transactionsJ.put(transactionJ);
        }

        return transactionsJ;

    }


    private JSONObject getMesasageClient(WebApplicationContext ctx, long orderId,long marketPlace){

        OrdersService ordersService = new OrdersService(marketPlace,ctx);
        JSONArray rmkArray= ordersService.getRemark(EntityRemark.REMARK_TYPE_ORDER_USER,orderId);
        if(rmkArray == null){
            return  null;
        }
        return rmkArray.getJSONObject(0);

    }

    private JSONArray getRemarkClient(WebApplicationContext ctx, long userId , long marketPlace){

        OrdersService ordersService = new OrdersService(marketPlace,ctx);
        return ordersService.getRemark(EntityRemark.REMARK_TYPE_USER_SHOP,userId);

    }

    private JSONObject getRemarkOrder(WebApplicationContext ctx, long orderId,long marketPlace){

        OrdersService ordersService = new OrdersService(marketPlace,ctx);
        JSONArray rmkArray =  ordersService.getRemark(EntityRemark.REMARK_TYPE_ORDER_SHOP,orderId);
        if(rmkArray == null){
            return  null;
        }

        return rmkArray.getJSONObject(0);

    }

}

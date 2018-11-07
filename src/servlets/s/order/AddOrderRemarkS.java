package servlets.s.order;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import servlets.ShopServletService;
import spring.entity.EntityOrders;
import spring.entity.EntityRemark;
import spring.interfaces.OrderDao;
import spring.interfaces.RemarkDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddOrderRemarkS",
        urlPatterns = "/remark.order")
public class AddOrderRemarkS extends HttpServlet {
    private final String c = "rmk_ordr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        long orderId;

        String tocken;
        String remark;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            orderId = sSS.getRequestJ().getLong("order_id");
            remark = sSS.getRequestJ().getString("remark");

        } else {

            return;

        }

        OrderDao orderDao = ctx.getBean("jpaOrder",OrderDao.class);
        EntityOrders entityOrders = orderDao.findById(orderId);

        if(entityOrders ==null || entityOrders.getShopId() != shopId){

            sSS.getStatus().put("error","Ордер не найден");
            sSS.finalizeShop(response);
            return;
        }

        RemarkDao remarkDao = ctx.getBean("jpaRemark", RemarkDao.class);
        EntityRemark entityRemark;
        List<EntityRemark> entityRemarkList = remarkDao.findByAdresseAndTypeAndMarketplace(orderId,EntityRemark.REMARK_TYPE_ORDER_SHOP,shopId);
        if(entityRemarkList !=null && entityRemarkList.size()>0){
            entityRemark = entityRemarkList.get(0);
        }else{
            entityRemark = new EntityRemark();
        }

        entityRemark.setAdresse(orderId);
        entityRemark.setMarketPlace(entityOrders.getShopId());
        entityRemark.setMsg(remark);
        entityRemark.setType(EntityRemark.REMARK_TYPE_ORDER_SHOP);

        remarkDao.save(entityRemark);
        entityOrders.setStatus(EntityOrders.STATUS_COMMENTED);

        orderDao.save(entityOrders);
        sSS.getStatus().put("error","ok");

        sSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

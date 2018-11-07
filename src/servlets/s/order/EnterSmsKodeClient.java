package servlets.s.order;

import Models.AccountEntity;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityAccountUsers;
import spring.entity.EntityOrders;
import spring.entity.EntityUserShops;
import spring.interfaces.AccountUserDao;
import spring.interfaces.OrderDao;
import spring.interfaces.UserShopDao;
import utils.SMSCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EnterSmsKodeClient",
 urlPatterns = "/enter.code.client")
public class EnterSmsKodeClient extends HttpServlet {
    private final String c = "entr_code_cl";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        String tocken;

        int code;
        long orderId;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            code = sSS.getRequestJ().getInt("code");
            orderId  = sSS.getRequestJ().getInt("order_id");

        } else {

            return;

        }

        AccountUserDao accountUserDao = ctx.getBean("jpaAccountUser",AccountUserDao.class);

        OrderDao orderDao  = ctx.getBean("jpaOrder",OrderDao.class);
        UserShopDao userShopDao = ctx.getBean("jpaUserShop",UserShopDao.class);

        EntityOrders entityOrders = orderDao.findById(orderId);
        if(entityOrders == null){

            sSS.getStatus().put("error","код неверный");
            sSS.finalizeShop(response);
            return;

        }

        EntityAccountUsers entityAccountUser = accountUserDao.selectById(entityOrders.getAccountUsers().getId());
        AccountEntity accountEntity = new AccountEntity(entityAccountUser);
        accountEntity.setSmsCode(code);

        if(SMSCodeUtil.isSMSCode(accountEntity)){

            sSS.getStatus().put("error","ok");
            EntityUserShops eus = userShopDao.findByUserIdAndShop(entityOrders.getAccountUsers().getId(),entityOrders.getShopId());
            sSS.getBody().put("active_cashback",eus.getCashbackActive());

        }else {

            sSS.getStatus().put("error","код неверный");

        }
        sSS.finalizeShop(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost( request,response);

    }
}

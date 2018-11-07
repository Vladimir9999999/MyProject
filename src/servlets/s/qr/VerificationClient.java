package servlets.s.qr;

import Models.AccountEntity;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityAccountUsers;
import spring.entity.EntityOrders;
import spring.interfaces.AccountUserDao;
import spring.interfaces.OrderDao;
import utils.SMSCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VerificationClient",
urlPatterns = "/snd.client.sms")
public class VerificationClient extends HttpServlet {
    private final static  String c = "snd_client_sms";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        long shopId;

        String tocken;
        long orderId;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            orderId = sSS.getRequestJ().getLong("order_id");

        } else {

            return;

        }

        AccountUserDao accountUserDao = ctx.getBean("jpaAccountUser",AccountUserDao.class);
        OrderDao orderDao  = ctx.getBean("jpaOrder",OrderDao.class);
        EntityOrders entityOrders = orderDao.findById(orderId);

        EntityAccountUsers entityAccountUser = entityOrders.getAccountUsers();
        AccountEntity accountEntity = new AccountEntity(entityAccountUser);

        int  code = SMSCodeUtil.sendSms(accountEntity.getLogin());
        //нужна ли нам аунтификация через сервер??????
        sendSms(accountEntity,code);

        sSS.getStatus().put("error", "ok");
        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    private void sendSms(AccountEntity accountEntity,int code){

        accountEntity.setSmsCode(code);
        SMSCodeUtil.addSMSCode(accountEntity);

    }
}

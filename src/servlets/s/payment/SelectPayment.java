package servlets.s.payment;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.payment.my_service.PaymentsServletService;
import spring.entity.EntityPayment;
import spring.interfaces.PaymentDao;
import spring.interfaces.PlayerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectPayment"
           ,urlPatterns = "/select.payments")
public class SelectPayment extends HttpServlet {

    private final String c = "sel_pay";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;
        long userId;

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        if (sSS.getRequestJ().has("user_id")) {

            userId = sSS.getRequestJ().getLong("user_id");

        } else {
            sSS.getStatus().put("error", "Отсутствует user_id");
            sSS.finalize(response);
            return;
        }

        PlayerDao playerDao = ctx.getBean("jpaPlayer", PlayerDao.class);

        if (!playerDao.existsById(userId)) {
            sSS.getStatus().put("error", "Player несуществует");
            sSS.finalize(response);
            return;
        }

        PaymentDao paymentDao = ctx.getBean("jpaPayment", PaymentDao.class);

        List<EntityPayment> listPayments = paymentDao.findByUserId(userId);

        if (listPayments.size() != 0) {

            PaymentsServletService pSS = new PaymentsServletService();

            JSONArray paymentsArrayJ = pSS.getPaymentsArrayJ(listPayments);

            if (paymentsArrayJ != null && paymentsArrayJ.length() != 0) {

                sSS.getBody().put("payments", paymentsArrayJ);
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}

package servlets.s.monthly_payment;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.monthly_payment.my_service.MonthPaymentsServletService;
import spring.entity.EntityPayment;
import spring.interfaces.PaymentDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectMonthlyPayment"
           ,urlPatterns = "/s/select.monthly.payment")
public class SelectMonthlyPayment extends HttpServlet {

    private final String c = "sel_mont_pay";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        PaymentDao paymentDao = ctx.getBean("jpaPayment", PaymentDao.class);

        List<EntityPayment> listPayments = paymentDao.findByShopId(shopId);

        if (listPayments.size() != 0) {

            MonthPaymentsServletService mPSS = new MonthPaymentsServletService(ctx);

            JSONArray paymentsForAllMonthsArrayJ = mPSS.getPaymentsForAllMonthsArrayJ(listPayments);

            if (paymentsForAllMonthsArrayJ != null && paymentsForAllMonthsArrayJ.length() != 0) {

                sSS.getBody().put("payments_for_all_months", paymentsForAllMonthsArrayJ);
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}

//localhost:8080/ice/s/select.monthly.payment?request={"tocken":"847ac07031d2fde4680415535a8fd5bf","employee_id":1414}

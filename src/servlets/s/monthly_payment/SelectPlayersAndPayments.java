package servlets.s.monthly_payment;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.monthly_payment.my_service.PlayersAndPaymentsServletService;
import spring.entity.EntityPayment;
import spring.interfaces.PaymentDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectPlayersAndPayments"
           ,urlPatterns = "/s/select.players.and.payments")
public class SelectPlayersAndPayments extends HttpServlet {

    private String c = "sel_play_pay";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        Integer year;
        Integer month;

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        if (sSS.getRequestJ().has("year")) {

            year = sSS.getRequestJ().getInt("year");

        } else {
            sSS.getStatus().put("error", "Отсутствует year");
            sSS.finalize(response);
            return;
        }

        if (sSS.getRequestJ().has("month")) {

            month = sSS.getRequestJ().getInt("month");

            if (month < 0 || month > 12) {

                sSS.getStatus().put("error", "Некорректно указан month");
                sSS.finalize(response);
                return;
            }

        } else {
            sSS.getStatus().put("error", "Отсутствует month");
            sSS.finalize(response);
            return;
        }

        PaymentDao paymentDao = ctx.getBean("jpaPayment", PaymentDao.class);

        List<EntityPayment> listPayments = paymentDao.findAllByYearAndMonth(year,month);

        if (listPayments != null && listPayments.size() != 0) {

            PlayersAndPaymentsServletService pPSS = new PlayersAndPaymentsServletService(ctx);

            JSONArray playersAndPaymentsArrayJ = pPSS.getPlayersAndPaymentsArrayJ(listPayments,year,month);

            if (playersAndPaymentsArrayJ != null && playersAndPaymentsArrayJ.length() != 0) {

                sSS.getBody().put("players_and_payments", playersAndPaymentsArrayJ);
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}

//localhost:8080/ice/s/select.players.and.payments?request={"tocken":"847ac07031d2fde4680415535a8fd5bf","employee_id":1414,"year":2018,"month":08}

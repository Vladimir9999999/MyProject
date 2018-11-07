package servlets.s.payment;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.payment.my_service.PaymentsServletService;
import spring.entity.EntityPayment;
import spring.interfaces.PaymentDao;
import spring.interfaces.PlayerDao;
import utils.CalendarRussia;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "SavePayment"
           ,urlPatterns = "/save.payment")
public class SavePayment extends HttpServlet {

    private final String c = "sav_pay";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        long userId;
        String tocken;

        int year;
        int month;
        Integer payment;

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

        if (sSS.getRequestJ().has("payment")) {

            payment = sSS.getRequestJ().getInt("payment");

        } else {
            sSS.getStatus().put("error", "Отсутствует payment");
            sSS.finalize(response);
            return;
        }

        PaymentDao paymentDao = ctx.getBean("jpaPayment", PaymentDao.class);

        EntityPayment entityPayment;

        if (sSS.getRequestJ().has("id")) {

            long id = (sSS.getRequestJ().getLong("id"));

            EntityPayment existingPayment = paymentDao.findById(id);

            if (existingPayment == null) {

                sSS.getStatus().put("error", "Запись не найдена");
                sSS.finalize(response);
                return;
            }

            paymentDao.deleteById(id);

            entityPayment = new EntityPayment();

        } else {
            entityPayment = new EntityPayment();
        }

        entityPayment.setUserId(userId);
        entityPayment.setShopId(shopId);

        if (sSS.getRequestJ().has("date")) {

            Timestamp date = new Timestamp(sSS.getRequestJ().getLong("date"));

            CalendarRussia calendar = new CalendarRussia();

            calendar.setTimeInMillis(date.getTime());

            int timestampYear = calendar.get(Calendar.YEAR);

            int timestampMonth = calendar.get(Calendar.MONTH);

            int timestampDay = calendar.get(Calendar.DAY_OF_MONTH);

            EntityPayment entityPaymentDay = paymentDao.findByUserIdAndYearAndMonthAndDay(userId, timestampYear, timestampMonth, timestampDay);

            if ((entityPaymentDay != null) && (entityPaymentDay.getPaymentForTheDay() != null)) {

                sSS.getStatus().put("error", "Оплата уже существует");
                sSS.finalize(response);
                return;
            } else {

                entityPayment.setDate(date);
                entityPayment.setYear(timestampYear);
                entityPayment.setMonth(timestampMonth);
                entityPayment.setDay(timestampDay);
                entityPayment.setPayment(payment);
                entityPayment.setPaymentForTheDay(true);

                paymentDao.save(entityPayment);

                sSS.getBody().put("id", entityPayment.getId());
                sSS.getStatus().put("error", "ok");
                sSS.finalize(response);
                return;
            }
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

        List<EntityPayment> listPayments = paymentDao.findByUserId(userId);

        if (PaymentsServletService.checkListPayments(listPayments, year, month)) {

            sSS.getStatus().put("error", "Оплата уже существует");
            sSS.finalize(response);
            return;
        }

        entityPayment.setYear(year);
        entityPayment.setMonth(month);
        entityPayment.setPayment(payment);
        entityPayment.setMonthlyPayment(true);

        paymentDao.save(entityPayment);

        sSS.getBody().put("id", entityPayment.getId());
        sSS.getStatus().put("error", "ok");
        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

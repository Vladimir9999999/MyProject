package servlets.s.player.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityPayment;
import spring.entity.EntityPlayer;
import spring.interfaces.PaymentDao;
import utils.CalendarRussia;
import java.util.Calendar;
import java.util.List;

public class PlayerServletService {

    private PaymentDao paymentDao;

    public PlayerServletService(WebApplicationContext ctx) {

        paymentDao = ctx.getBean("jpaPayment", PaymentDao.class);
    }

    public JSONArray getPlayersArrayJ(List<EntityPlayer> listPlayers) {

        if (listPlayers.size() == 0) {

            return null;
        }

        JSONArray playersArrayJ = new JSONArray();

        for (EntityPlayer player : listPlayers) {

            JSONObject playerJ = new JSONObject();

            playerJ.put("id", player.getId());
            playerJ.put("name", player.getName());
            playerJ.put("patronymic", player.getPatronymic());
            playerJ.put("surname", player.getSurname());

            List<EntityPayment> paymentList = paymentDao.findByUserId(player.getId());

            JSONArray paymentsArrayJ = getPaymentsArrayJ(paymentList);

            if (paymentsArrayJ != null && paymentsArrayJ.length() != 0) {

                playerJ.put("payments",paymentsArrayJ);
            }

            playersArrayJ.put(playerJ);
        }

        return playersArrayJ;
    }

    private JSONArray getPaymentsArrayJ(List<EntityPayment> paymentList) {

        if (paymentList == null || paymentList.size() == 0) {

            return null;
        }

        JSONArray paymentsArrayJ = new JSONArray();

        CalendarRussia calendar = new CalendarRussia();

        long currentMonth = calendar.get(Calendar.MONTH);

        for (EntityPayment payment : paymentList) {

            if (payment.getMonth() == currentMonth
                    && (payment.getPaymentForTheDay() != null || payment.getMonthlyPayment() != null)) {

                JSONObject paymentJ = new JSONObject(payment);

                paymentJ.remove("shopId");
                paymentJ.remove("userId");
                paymentJ.remove("monthlyPayment");
                paymentJ.remove("paymentForTheDay");

                if (payment.getDate() == null) {
                    paymentJ.remove("date");
                } else {

                    paymentJ.remove("year");
                    paymentJ.remove("month");
                    paymentJ.remove("day");
                    paymentJ.remove("paymentForTheDay");
                    paymentJ.remove("monthly_payment");
                    paymentJ.remove("date");
                    paymentJ.put("date",payment.getDate().getTime());
                }

                paymentsArrayJ.put(paymentJ);
            }
        }

        return paymentsArrayJ;
    }
}

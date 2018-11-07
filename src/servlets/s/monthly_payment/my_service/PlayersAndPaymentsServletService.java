package servlets.s.monthly_payment.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityPayment;
import spring.entity.EntityPlayer;
import spring.interfaces.PlayerDao;
import java.util.List;

public class PlayersAndPaymentsServletService {

    private PlayerDao playerDao;

    public PlayersAndPaymentsServletService(WebApplicationContext ctx) {

        playerDao = ctx.getBean("jpaPlayer",PlayerDao.class);
    }

    public JSONArray getPlayersAndPaymentsArrayJ(List<EntityPayment> paymentList,int year,int month) {

        if (paymentList == null || paymentList.size() == 0) {

            return null;
        }

        JSONArray playersAndPaymentsArrayJ = new JSONArray();

        for (EntityPayment payment : paymentList) {

            if (payment.getYear() == year && payment.getMonth() == month) {

                JSONObject paymentJ = new JSONObject(payment);

                paymentJ.remove("userId");
                paymentJ.remove("shopId");
                paymentJ.remove("monthlyPayment");
                paymentJ.remove("paymentForTheDay");
                paymentJ.remove("day");

                if (payment.getDate() == null) {
                    paymentJ.remove("date");
                } else {

                    paymentJ.remove("userId");
                    paymentJ.remove("shopId");
                    paymentJ.remove("year");
                    paymentJ.remove("month");
                    paymentJ.remove("day");
                    paymentJ.remove("monthlyPayment");
                    paymentJ.remove("paymentForTheDay");
                    paymentJ.remove("date");
                    paymentJ.put("date",payment.getDate().getTime());
                }

                EntityPlayer player = playerDao.findById(payment.getUserId());

                JSONObject playerJ = new JSONObject(player);

                playerJ.remove("shopId");

                paymentJ.put("player",playerJ);

                playersAndPaymentsArrayJ.put(paymentJ);
            }
        }

        return playersAndPaymentsArrayJ;
    }
}

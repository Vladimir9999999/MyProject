package servlets.s.payment.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import spring.entity.EntityPayment;
import java.util.List;

public class PaymentsServletService {

    public static boolean checkListPayments(List<EntityPayment> listPayments, int year, int month) {

        boolean check = false;

        if (listPayments == null || listPayments.size() == 0) {

            return false;
        }

        for (EntityPayment payment : listPayments) {

            if (year == payment.getYear() && month == payment.getMonth()
                    && (payment.getMonthlyPayment() != null)) {

                check = true;

                break;
            }

        }

        return check;
    }

    public JSONArray getPaymentsArrayJ(List<EntityPayment> listPayments) {

        JSONArray paymentsArrayJ = new JSONArray();

        for (EntityPayment payment : listPayments) {

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
                paymentJ.put("date", payment.getDate().getTime());
            }

            paymentsArrayJ.put(paymentJ);
        }

        return paymentsArrayJ;
    }
}

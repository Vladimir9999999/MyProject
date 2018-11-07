package servlets.s.monthly_payment.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityPayment;
import spring.interfaces.PaymentDao;
import java.util.LinkedList;
import java.util.List;

public class MonthPaymentsServletService {

    private PaymentDao paymentDao;

    public MonthPaymentsServletService(WebApplicationContext ctx) {

        paymentDao = ctx.getBean("jpaPayment", PaymentDao.class);
    }

    public JSONArray getPaymentsForAllMonthsArrayJ(List<EntityPayment> listPayments) {

        if (listPayments == null || listPayments.size() == 0) {

            return null;
        }

        JSONArray paymentsArrayJ = new JSONArray();

        List<EntityPayment> entityPaymentList = new LinkedList<>();

        for (EntityPayment payment : listPayments) {

            EntityPayment entityPay = new EntityPayment();

            if (checkListPayments(entityPaymentList,payment.getYear(),payment.getMonth())) {

                continue;
            }

            entityPay.setYear(payment.getYear());
            entityPay.setMonth(payment.getMonth());

            entityPaymentList.add(entityPay);

            JSONObject paymentJ = new JSONObject(payment);

            paymentJ.remove("id");
            paymentJ.remove("userId");
            paymentJ.remove("shopId");
            paymentJ.remove("payment");
            paymentJ.remove("day");
            paymentJ.remove("date");
            paymentJ.remove("monthlyPayment");
            paymentJ.remove("paymentForTheDay");

            List<EntityPayment> paymentList = paymentDao.findAllByYearAndMonth(payment.getYear(), payment.getMonth());

            if (paymentList == null || paymentList.size() == 0) {

                break;
            }

            long paymentAmount = 0;

            for (EntityPayment entityPayment : paymentList) {

                paymentAmount += entityPayment.getPayment();
            }

            paymentJ.put("all_payment_for_the_month", paymentAmount);

            paymentsArrayJ.put(paymentJ);

        }

        return paymentsArrayJ;
    }

    private static boolean checkListPayments(List<EntityPayment> listPayments,int year,int month) {

        if (listPayments == null || listPayments.size() == 0) {

            return false;
        }

        for (EntityPayment payment : listPayments) {

            if (year == payment.getYear() && month == payment.getMonth()) {

                return true;
            }

        }

        return false;
    }
}

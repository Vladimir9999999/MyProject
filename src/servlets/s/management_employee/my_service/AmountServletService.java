package servlets.s.management_employee.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityAmount;
import spring.interfaces.AmountDao;
import java.sql.Timestamp;
import java.util.List;

public class AmountServletService {

    private AmountDao amountDao;

    public AmountServletService(long shopId, long employeeId, WebApplicationContext ctx) {

        amountDao = ctx.getBean("jpaAmount", AmountDao.class);

        this.shopId = shopId;
        this.employeeId = employeeId;
    }

    private long shopId;
    private long employeeId;

    public EntityAmount save(JSONObject amountJ, int type) {

        EntityAmount amount = new EntityAmount();
        amount.setShopId(shopId);
        amount.setEmployeeId(employeeId);

        try {
            amount.setDate(Timestamp.valueOf(amountJ.getString("date")));
        } catch (Exception e) {
            amount.setDate(Timestamp.valueOf(amountJ.getString("date") + " 00:00:00"));
        }

        amount.setAmount(amountJ.getDouble("amount"));
        amount.setType(type);

        if (amountJ.has("id")) {
            amount.setId(amountJ.getLong("id"));
        }

        if (amountJ.has("comment")) {
            amount.setComment(amountJ.getString("comment"));
        }


        amountDao.save(amount);

        return amount;

    }

    public JSONArray getAmountArrayJ(List<EntityAmount> listAmount,int type) {

        if (type == EntityAmount.PRIZE) {

            JSONArray prizeArrayJ = new JSONArray();

            for (EntityAmount amount : listAmount) {

                JSONObject prizeJ = new JSONObject(amount);
                prizeJ.remove("type");
                prizeJ.remove("shopId");
                prizeJ.remove("employeeId");

                switch (amount.getType()) {
                    case EntityAmount.PRIZE:
                        prizeArrayJ.put(prizeJ);
                        break;

                }

            }

            return prizeArrayJ;
        }

        if (type == EntityAmount.PENALTY) {

            JSONArray penaltyArrayJ = new JSONArray();

            for (EntityAmount amount : listAmount) {

                JSONObject penaltyJ = new JSONObject(amount);
                penaltyJ.remove("type");
                penaltyJ.remove("shopId");
                penaltyJ.remove("employeeId");

                switch (amount.getType()) {
                    case EntityAmount.PENALTY:
                        penaltyArrayJ.put(penaltyJ);
                        break;
                }

            }

            return penaltyArrayJ;
        }

        return null;
    }
}

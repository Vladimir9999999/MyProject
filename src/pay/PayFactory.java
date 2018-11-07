package pay;

import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityBills;
import spring.entity.EntityOrders;

public abstract class PayFactory{

    public static Pay getPay(WebApplicationContext ctx, EntityOrders orders, float prepaySumm, int payType) {
        switch (payType){

            case EntityBills.PAY_METHOD_CASHBACK:

                return new PayTypeCashback(ctx,orders.getShopId(),orders.getAccountUsers().getId(),prepaySumm);

            case EntityBills.PAY_METHOD_CASH:

                return new PayTypeCash(ctx,orders.getShopId(),orders.getAccountUsers().getId(),prepaySumm);

            case EntityBills.PAY_METHOD_TERMINAL:

                return new PayTypeTerminal(ctx,orders.getShopId(),orders.getAccountUsers().getId(),prepaySumm);

        }
        return null;
    }
}
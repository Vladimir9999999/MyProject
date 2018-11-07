package pay;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityOrders;
import spring.entity.EntityTransactions;

public interface Pay {

    EntityTransactions createTransactionBuy();
    JSONObject getError();
}

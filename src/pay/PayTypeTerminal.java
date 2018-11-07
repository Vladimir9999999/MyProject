package pay;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityBills;
import spring.entity.EntityTransactions;
import spring.interfaces.TransactionDao;
import java.sql.Timestamp;
import java.util.Date;

public class PayTypeTerminal implements Pay {

    private WebApplicationContext ctx;

    private float prepaySumm = 0F;
    private int currency = EntityBills.PAY_METHOD_TERMINAL;

    private long shopId;
    private long userId;



    public PayTypeTerminal(WebApplicationContext ctx, long shopId, long userId, float prepaySumm) {

        this.ctx = ctx;
        this.shopId = shopId;
        this.userId = userId;
        this.prepaySumm=prepaySumm;

    }

    @Override
    public EntityTransactions createTransactionBuy(){

        EntityTransactions entityTransactions = new EntityTransactions();
        Timestamp createDate = new Timestamp(new Date().getTime());

        entityTransactions.setDete(createDate);
        entityTransactions.setSumm((double) prepaySumm);
        entityTransactions.setMarketplace(shopId);
        entityTransactions.setCurrency(currency);
        entityTransactions.setSender(userId);

        TransactionDao transactionDao = ctx.getBean("jpaTransactions",TransactionDao.class);
        transactionDao.save(entityTransactions);

        return entityTransactions;

    }

    @Override
    public JSONObject getError() {

        return null;

    }

    public WebApplicationContext getCtx() {
        return ctx;
    }

    public void setCtx(WebApplicationContext ctx) {
        this.ctx = ctx;
    }

    public float getPrepaySumm() {
        return prepaySumm;
    }

    public void setPrepaySumm(float prepaySumm) {
        this.prepaySumm = prepaySumm;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


}

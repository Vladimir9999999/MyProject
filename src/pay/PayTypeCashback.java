package pay;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityBills;
import spring.entity.EntityTransactions;
import spring.entity.EntityUserShops;
import spring.interfaces.TransactionDao;
import spring.interfaces.UserShopDao;

import java.sql.Timestamp;
import java.util.Date;

public class PayTypeCashback implements Pay {

    private WebApplicationContext ctx;

    private float prepaySumm = 0F;
    private int currency = EntityBills.PAY_METHOD_CASHBACK;

    private long shopId;
    private long userId;
    private JSONObject error = null;


    public PayTypeCashback(WebApplicationContext ctx, long shopId, long userId, float prepaySumm) {

        this.ctx = ctx;
        this.shopId = shopId;
        this.userId = userId;

        ctx.getServletContext().getClassLoader().getName();

        blockPrepay(prepaySumm);

    }

    private   boolean blockPrepay(float summ){

        UserShopDao userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);

        EntityUserShops eus = userShopDao.findByUserIdAndShop(userId, shopId);

        if (eus == null || eus.getCashbackActive() < summ) {

            error = new JSONObject();

            error.put("cashback_active",eus.getCashbackActive());
            return false;
        }

        eus.setCashbackActive(eus.getCashbackActive()-summ);
        prepaySumm = summ;
        userShopDao.save(eus);

        return true;

    }

    @Override
    public EntityTransactions createTransactionBuy(){

        if(error == null) {

            EntityTransactions entityTransactions = new EntityTransactions();
            Timestamp createDate = new Timestamp(new Date().getTime());

            entityTransactions.setDete(createDate);
            entityTransactions.setSumm((double) prepaySumm);
            entityTransactions.setMarketplace(shopId);
            entityTransactions.setCurrency(currency);
            entityTransactions.setSender(userId);

            TransactionDao transactionDao = ctx.getBean("jpaTransactions", TransactionDao.class);
            transactionDao.save(entityTransactions);

            return entityTransactions;

        }
        return null;
    }

    @Override
    public JSONObject getError() {
        return error;
    }

    public  void unlockPrepay(){

        UserShopDao userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);

        EntityUserShops eus = userShopDao.findByUserIdAndShop(userId, shopId);

        eus.setCashbackActive(eus.getCashbackInactive()+prepaySumm);

        userShopDao.save(eus);

        prepaySumm = 0;

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

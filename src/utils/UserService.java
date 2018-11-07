package utils;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.*;
import spring.interfaces.AccountUserDao;
import spring.interfaces.CashbackDao;
import spring.interfaces.UserShopDao;

public class UserService {

   private AccountUserDao accountUserDao;
   private UserShopDao userShopDao;
   private CashbackDao cashbackDao;

    public UserService(WebApplicationContext ctx) {

        accountUserDao = ctx.getBean("jpaAccountUser", AccountUserDao.class);
        userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);
        cashbackDao = ctx.getBean("jpaCashback", CashbackDao.class);
    }

    public JSONObject getUserData(EntityOrders order) {

        JSONObject userDataJ = new JSONObject();

        EntityAccountUsers accountUsers = order.getAccountUsers();

        if (accountUsers == null) {
            return null;
        }

        userDataJ.put("phone", Long.parseLong(accountUsers.getLogin()));

        EntityUserShops eus = userShopDao.findByUserIdAndShop(order.getAccountUsers().getId() , order.getShopId());

        if (eus != null) {

            EntityCashback entityCashback = cashbackDao.selectByShopId(order.getShopId());
            switch (eus.getCashback()) {

                case EntityCashback.STANDART_CB:
                    userDataJ.put("cashback", entityCashback.getStandard() / 100f);
                    break;
                case EntityCashback.SILVER_CB:
                    userDataJ.put("cashback", entityCashback.getSilver() / 100f);
                    break;
                case EntityCashback.GOLD_CB:
                    userDataJ.put("cashback", entityCashback.getGold() / 100f);
                    break;

            }
        }

        return userDataJ;
    }

    float getCasbackPercent(EntityUserShops entityUserShops) {

        EntityCashback entityCashback = cashbackDao.selectByShopId(entityUserShops.getShop());

        float percent = 0;

        switch (1) {

            case EntityCashback.STANDART_CB:
                percent = entityCashback.getStandard() / 100f;
                break;
            case EntityCashback.SILVER_CB:
                percent = entityCashback.getSilver() / 100f;
                break;
            case EntityCashback.GOLD_CB:
                percent = entityCashback.getGold() / 100f;
                break;
        }

        return percent;
    }
}


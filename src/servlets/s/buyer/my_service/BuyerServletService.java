package servlets.s.buyer.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityAccountUsers;
import spring.entity.EntityPersonalDataUser;
import spring.entity.EntityUserShops;
import spring.interfaces.AccountUserDao;
import spring.interfaces.PersonalDataUserDao;
import spring.interfaces.UserShopDao;
import java.util.List;

public class BuyerServletService {

    private AccountUserDao accountUserDao;
    private PersonalDataUserDao personalDataUserDao;
    private UserShopDao userShopDao;

    public BuyerServletService(WebApplicationContext ctx) {

        accountUserDao = ctx.getBean("jpaAccountUser", AccountUserDao.class);
        personalDataUserDao = ctx.getBean("jpaPersonalDataUser", PersonalDataUserDao.class);
        userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);
    }

    public JSONArray getBayerArrayJ(List<EntityUserShops> listUserShops) {

        JSONArray bayerArrayJ = new JSONArray();

        for (EntityUserShops userShops : listUserShops) {

            EntityAccountUsers accountUsers = accountUserDao.selectById(userShops.getUserId());
            EntityPersonalDataUser personalDataUser = personalDataUserDao.findByUserId(userShops.getUserId());

            JSONObject bayerJ = new JSONObject();

            bayerJ.put("id", userShops.getUserId());

            bayerJ.put("phone", Long.parseLong(accountUsers.getLogin()));
            //bayerJ.put("cash_back", userShops.getCashback());
            bayerJ.put("cash_back_active", userShops.getCashbackActive());
            //bayerJ.put("last_buy", userShops.getLastBuy());

            setInformation(personalDataUser, bayerJ);

            bayerArrayJ.put(bayerJ);
        }

        return bayerArrayJ;
    }

    public JSONObject getBayerJ(long id,long shopId) {

        EntityUserShops userShops = userShopDao.findByUserIdAndShop(id,shopId);

        if (userShops != null) {

            EntityAccountUsers accountUsers = accountUserDao.selectById(userShops.getUserId());
            EntityPersonalDataUser personalDataUser = personalDataUserDao.findByUserId(userShops.getUserId());

            JSONObject bayerJ = new JSONObject();

            bayerJ.put("id", userShops.getUserId());

            bayerJ.put("phone", Long.parseLong(accountUsers.getLogin()));
            //bayerJ.put("cash_back", userShops.getCashback());
            bayerJ.put("cash_back_active", userShops.getCashbackActive());
            //bayerJ.put("last_buy", userShops.getLastBuy());

            setInformation(personalDataUser, bayerJ);

            return bayerJ;

        }

        return null;
    }

    private void setInformation(EntityPersonalDataUser personalDataUser, JSONObject bayerJ) {

        if (personalDataUser != null) {

            bayerJ.put("name", personalDataUser.getName());

            if (personalDataUser.getSex() != null) {

                if (personalDataUser.getSex()) {

                    bayerJ.put("sex", 1);
                } else {
                    bayerJ.put("sex", 0);
                }
            }
        }
    }
}

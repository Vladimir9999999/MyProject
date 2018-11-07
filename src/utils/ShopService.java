package utils;

import Models.*;
import dao.AccountDao;
import dao.EmployeeDao;
import dao.ShopDao;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityCashback;
import spring.entity.EntityEmployee;
import spring.entity.EntityPrice;
import spring.interfaces.CashbackDao;
import spring.interfaces.PriceDao;

import javax.persistence.OneToMany;
import java.util.List;

public class ShopService {
    @OneToMany
    private WebApplicationContext ctx;

    private String errors = null;
    private CashbackDao cashbackDao;
    AccountDao accountDao = new AccountDao(AccountDao.BD_SHOP);

    public ShopService(WebApplicationContext ctx) {
        this.ctx = ctx;
        cashbackDao = ctx.getBean("jpaCashback",CashbackDao.class);

    }

    public JSONObject createDefaultShop(JSONObject requestJ) {

        String login = String.valueOf(requestJ.getLong("phone"));


        AccountDao accountDao = new AccountDao(AccountDao.BD_SHOP);


        if (login != null) {


            ShopEntity newShop = addShop();
            if (newShop != null) {

                AccountEntity accountEntity = new AccountEntity();
                accountEntity.setLogin(login);

                EntityCashback entityCashback = new EntityCashback();

                entityCashback.setGold(0);
                entityCashback.setPartner(0);
                entityCashback.setShopId(newShop.getId());
                entityCashback.setSilver(0);
                entityCashback.setStandard(0);

                cashbackDao.save(entityCashback);


                EmploeeEntity emploeeEntity = EmploeeEntity.getBuilder()

                        .setShopId(newShop.getId())
                        //.setBeginTime((short) 0)
                        //.setEndTime((short) 0)
                        .setFunction("[]")
                        .setName("Администратор")
                        //.setWeekend("[]")
                        .setPrivilege((short) 1).build();
                emploeeEntity.setSchedule(1);
                EmployeeDao employeeDao = new EmployeeDao();

                employeeDao.registartion(emploeeEntity);
                accountEntity.setShop_id(newShop.getId());
                accountEntity.setEmployeeId(emploeeEntity.getId());
                accountDao.saveAccount(accountEntity);
                JSONObject body = new JSONObject();

                String token = GenerateToken.generateToken(accountEntity);
                Session session = new Session();
                session.setToken(token);
                session.setShopId(newShop.getId());
                session.setUserId(emploeeEntity.getId());
                session.setPrivilege(emploeeEntity.getPrivilege());
                SessionManager.addSession(session, ctx);

                body.put("employee_id", emploeeEntity.getId())
                        .put("tocken", token);

                return body;


            }

        }else {
            errors = "login = null || password = null";
        }
        return null;
    }

    public boolean deleteShop(long shopId){

        spring.interfaces.EmployeeDao employeeDao = ctx.getBean("jpaEmployee", spring.interfaces.EmployeeDao.class);
        CashbackDao cashbackDao = ctx.getBean("jpaCashback",CashbackDao.class);

        List<EntityEmployee> employeeList = employeeDao.selectByShopId(shopId);
        for(EntityEmployee entityEmployee: employeeList) {
            employeeDao.deleteById(entityEmployee.getId());
        }
        PriceDao priceDao = ctx.getBean("jpaPrice",PriceDao.class);

        List<EntityPrice> prices = priceDao.selectByShopId(shopId);

        for(EntityPrice price: prices){
            priceDao.deleteById(price.getId());
        }

        cashbackDao.delete(shopId);
        accountDao.deleteByShopId(shopId);
        spring.interfaces.ShopDao shopDao = ctx.getBean("jpaShop", spring.interfaces.ShopDao.class);
        shopDao.deleteById(shopId);

        return true;
    }

    private AccountEntity addAccount(String login, String password,long shop_id){
        AccountEntity accountShop = new AccountEntity();

        accountShop.setLogin(login);
        accountShop.setPassword(password);
        accountShop.setShop_id(shop_id);
        AccountDao accountDao = new AccountDao(AccountDao.BD_SHOP);

        if(accountDao.saveAccount(accountShop)){
            return accountShop;
        }
        return null;
    }

    private ShopEntity addShop(){

        ShopEntity shop = new ShopEntity();

        ShopDao shopDao = new ShopDao();
        if(shopDao.addShop(shop)){
            return shop;
        }
        return null;
    }

    private String getTocken(Long id){
        return "12345678901234567890";
    }

    public String getErrors() {
        return errors;
    }
}

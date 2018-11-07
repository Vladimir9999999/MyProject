package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityBasket;
import spring.interfaces.BasketDao;
import java.util.List;

public class BasketService {

    private WebApplicationContext ctx;
    private BasketDao basketDao;

    public BasketService(WebApplicationContext ctx){

        this.ctx = ctx;
        basketDao = ctx.getBean("jpaBasket",BasketDao.class);

    }

    public boolean save(long shopId, long user_id, JSONArray basket){

        EntityBasket entityBasket = basketDao.selectByShopIdUserId(shopId,user_id);

        if(entityBasket == null){

            entityBasket = new EntityBasket();

            entityBasket.setShopId(shopId);
            entityBasket.setUserId(user_id);

        }

        if(entityBasket.getServices() == null || ! entityBasket.getServices().equals(basket.toString())){

            entityBasket.setServices(basket.toString());
            basketDao.save(entityBasket);
            return true;
        }

        return false;
    }

    public void delete(EntityBasket entityBasket){

        basketDao.delete(entityBasket);

    }


}

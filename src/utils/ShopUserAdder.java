package utils;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityShopUser;
import spring.entity.EntityShopsCluster;
import spring.entity.EntityUserShops;
import spring.interfaces.CashbackDao;
import spring.interfaces.ShopClusterDao;
import spring.interfaces.ShopUserDao;
import spring.interfaces.UserShopDao;

public class ShopUserAdder {

    private ShopUserDao shopUserDao;
    private UserShopDao userShopDao;
    private CashbackDao cashbackDao;
    private ShopClusterDao clusterDao;

    public ShopUserAdder(WebApplicationContext ctx){

        shopUserDao = ctx.getBean("jpaShopUser", ShopUserDao.class);
        userShopDao = ctx.getBean("jpaUserShop",UserShopDao.class);
        cashbackDao = ctx.getBean("jpaCashback",CashbackDao.class);
        clusterDao = ctx.getBean("jpaShopCluster",ShopClusterDao.class);
    }


    public String addShop(long shopId, long userId){
        String status = "ok";

        EntityShopUser shopUser = shopUserDao.selectByUserId(userId);
        EntityUserShops userShops = userShopDao.findByUserIdAndShop(userId,shopId);
        //EntityCashback cashback = cashbackDao.selectByShopId(shopId);
        JSONArray shopCluster;
        EntityShopsCluster entityShopsCluster;

        if(userShops  == null){
            userShops = new EntityUserShops();
            userShops.setCashback(1);
            userShops.setUserId(userId);
            userShops.setShop(shopId);
            userShops.setFavorite(false);

            userShopDao.save(userShops);

        }

        if(shopUser == null){

            shopUser = new EntityShopUser();

            shopUser.setUserId(userId);
            shopCluster = new JSONArray();
            shopCluster.put(shopId);

            entityShopsCluster = clusterDao.selectByShopCluster(shopCluster.toString());

            if (entityShopsCluster == null) {
                entityShopsCluster = new EntityShopsCluster();
                entityShopsCluster.setShops(shopCluster.toString());
                clusterDao.save(entityShopsCluster);
            }

            shopUser.setShopsClusterByShopCluster(entityShopsCluster);

            shopUserDao.save(shopUser);

        }else{

            entityShopsCluster = shopUser.getShopsClusterByShopCluster();

            if (entityShopsCluster == null) {

                shopCluster = new JSONArray();
                shopCluster.put(shopId);

                entityShopsCluster = new EntityShopsCluster();
                entityShopsCluster.setShops(shopCluster.toString());

                clusterDao.save(entityShopsCluster);

            }else {

                boolean flag = true;
                shopCluster = new JSONArray(entityShopsCluster.getShops());

                for(int i = 0; i<shopCluster.length();i++){

                    if(shopId==shopCluster.getLong (i) ) {

                        flag = false;
                        break;

                    }

                }

                if(flag) {

                    shopCluster.put(shopId);
                    entityShopsCluster.setShops(shopCluster.toString());
                    clusterDao.save(entityShopsCluster);

                }


            }
        }

        return status;

    }

}

package spring.interfaces;

import spring.entity.EntityBasket;

import java.util.List;

public interface BasketDao {

    EntityBasket save(EntityBasket basket);
    void delete(long shopId, long userId);
    EntityBasket selectByShopIdUserId(long shopId, long userId);
    List<EntityBasket> selectByUserId(long user_id);
    void deleteFromShopIdAndUserId(long shopId, long userId);
    void delete(EntityBasket entityBasket);
}

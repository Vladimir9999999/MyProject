package spring.interfaces;

import spring.entity.EntityIndexPageShop;

import java.util.List;

public interface IndexPageShopDao {

    EntityIndexPageShop save(EntityIndexPageShop entityIndexPageShop);
    List<EntityIndexPageShop> selectByShopId(long shopId);
    EntityIndexPageShop selectById(long id);

}

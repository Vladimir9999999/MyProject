package spring.interfaces;

import spring.entity.EntityShopUser;

public interface ShopUserDao {

    EntityShopUser save(EntityShopUser shopUser);
    EntityShopUser selectByUserId(long id);

}

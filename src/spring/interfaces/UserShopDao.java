package spring.interfaces;

import spring.entity.EntityUserShops;
import java.util.List;

public interface UserShopDao {
    EntityUserShops findByUserIdAndShop(long user, long shop);

    EntityUserShops save(EntityUserShops userShops);
    List<EntityUserShops> findByUserId(long user);
    List<EntityUserShops> findByShop(long shop);
    List<EntityUserShops> saveAll(List<EntityUserShops> shops);
    EntityUserShops findById(long buyerId);
    void deleteById(long id);
}

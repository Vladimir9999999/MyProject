package spring.interfaces;

import spring.entity.EntityShop;
import java.util.List;

public interface ShopDao {

    EntityShop save(EntityShop shop);
    EntityShop findById(long shopId);
    void deleteById(long shopId);
    List<EntityShop> findAllById(long id);
}

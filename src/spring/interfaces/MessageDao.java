package spring.interfaces;

import spring.entity.EntityMessage;
import spring.entity.EntityUserShops;
import java.util.List;

public interface MessageDao {

    EntityMessage save(EntityMessage message);
    EntityMessage findById(long id);
    List<EntityMessage> findAllByEntityUserShopsList(EntityUserShops entityUserShop);
    void deleteAllByEntityUserShopsList(EntityUserShops entityUserShops);
}

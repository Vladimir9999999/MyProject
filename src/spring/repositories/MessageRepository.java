package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityMessage;
import spring.entity.EntityUserShops;
import java.util.List;

public interface MessageRepository extends CrudRepository<EntityMessage,Long> {

    EntityMessage findById(long id);
    List<EntityMessage> findAllByEntityUserShopsList(EntityUserShops entityUserShop);
    void deleteAllByEntityUserShopsList(EntityUserShops entityUserShops);
}

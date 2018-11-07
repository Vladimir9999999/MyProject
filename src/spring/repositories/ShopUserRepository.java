package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityShopUser;

public interface ShopUserRepository extends CrudRepository<EntityShopUser, Long>{

    EntityShopUser findByUserId(long id);

}

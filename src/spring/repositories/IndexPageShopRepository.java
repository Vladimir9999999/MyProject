package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityIndexPageShop;

import java.util.List;

public interface IndexPageShopRepository extends CrudRepository<EntityIndexPageShop, Long> {

    List<EntityIndexPageShop> findTopByShopId(long shopId);
    EntityIndexPageShop findById(long id);


}

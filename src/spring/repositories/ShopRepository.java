package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityShop;

import java.util.List;

public interface ShopRepository extends CrudRepository<EntityShop, Long> {
    EntityShop findById(long id);
    List<EntityShop> findAllById(long id);
}

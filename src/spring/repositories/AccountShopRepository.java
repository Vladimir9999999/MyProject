package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityAccountShop;

public interface AccountShopRepository extends CrudRepository<EntityAccountShop, Long> {

    EntityAccountShop findById(long id);
}

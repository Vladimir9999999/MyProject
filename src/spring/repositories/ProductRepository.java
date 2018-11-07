package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityProduct;

public interface ProductRepository extends CrudRepository<EntityProduct, Long> {
    EntityProduct findByName(String name);
    EntityProduct findById(long shopId);
}

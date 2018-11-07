package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityTechKomplekt;
import java.util.List;

public interface TechKomplectRepository extends CrudRepository<EntityTechKomplekt, Long> {
    EntityTechKomplekt findByShopIdAndId(long shopId, long id);
    List<EntityTechKomplekt> findByShopIdAndProduct(long shopId, long product);

}

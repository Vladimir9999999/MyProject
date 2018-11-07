package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityShopsCluster;

public interface ShopsClusterRepository extends CrudRepository<EntityShopsCluster, Long> {

    EntityShopsCluster findByShops(String shops);

}

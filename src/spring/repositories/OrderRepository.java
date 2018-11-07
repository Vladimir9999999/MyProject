package spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityOrders;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends CrudRepository<EntityOrders, Long> {

    EntityOrders findById(long id);
    List<EntityOrders> findAllByLastModificationAfterAndShopId(Timestamp lastMod,long shopId);

    List<EntityOrders> findByShopId(long shopId);
    List<EntityOrders> findAllByStatusAndShopId(int status, long shopId);

    @Query(value = "select * from orders where status != ?1 and shop_id = ?2", nativeQuery = true)
    List<EntityOrders> findAllByNoStatus(int status, long shopId);


}

package spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityBills;
import java.util.List;

public interface BillsRepository  extends CrudRepository<EntityBills, Long> {
    EntityBills findById(long billsId);

    @Query(value="SELECT * " +
            "FROM bills t left outer join orders o ON t.order_id = o.id" +
            " WHERE o.id = ?1 and t.type  = ?2", nativeQuery = true)
    List<EntityBills> findByOrderIdAndType(long orderId, int type);

    @Query(value="SELECT * " +
            "FROM bills t left outer join orders o ON t.order_id = o.id" +
            " WHERE o.id = ?1 ", nativeQuery = true)
    List<EntityBills> findByOrderId(long orderId);
}

package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityLackOf;
import java.sql.Timestamp;
import java.util.List;

public interface LackOfRepository extends CrudRepository<EntityLackOf,Long> {

    EntityLackOf findById(long id);
    List<EntityLackOf> findByEmployeeId(long employeeId);
    List<EntityLackOf> findTop1ByDateBeforeAndEmployeeIdOrderByDateDesc(Timestamp startTime, long employeeId);
    int countByDateBeforeAndDateAfterAndEmployeeId(Timestamp finishTime, Timestamp startTime, long employeeId);
    EntityLackOf findByIdAndShopId(long id, long shopId);
}

package spring.interfaces;

import spring.entity.EntityLackOf;
import java.sql.Timestamp;
import java.util.List;

public interface LackOfDao {

    EntityLackOf findById(long id);
    EntityLackOf save(EntityLackOf lackOf);
    List<EntityLackOf> findByEmployeeId(long employeeId);
    List<EntityLackOf> findByLastLackOf(Timestamp startTime, long employeeId);
    int countByPeriod(Timestamp finishTime, Timestamp startTime, long employeeId);
    EntityLackOf findByIdAndShopId(long id, long shopId);
}

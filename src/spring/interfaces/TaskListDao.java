package spring.interfaces;

import spring.entity.EntityOrders;
import spring.entity.EntityTaskList;
import java.sql.Timestamp;
import java.util.List;

public interface TaskListDao {

    EntityTaskList save(EntityTaskList entityTaskList);

    List<EntityTaskList> findByEmployeeId(long employeeId, Timestamp time);
    EntityTaskList findOneBeforePlannedTime(Timestamp plannedTime, long employeeId);

    int countOderPeriod(Timestamp startTime, Timestamp finishTime, long employeeId);

    List<EntityTaskList> findByTimeAfter(Timestamp time);

    EntityTaskList deleteByOrder(EntityOrders entityOrders);
    List<EntityTaskList> findNew(Timestamp time, int offset, int limit, int status);
    List<EntityTaskList> findByStatus(int offset, int limit, int status);
    List<EntityTaskList> findOld(Timestamp time, int offset, int limit, int status);
}

package spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityOrders;
import spring.entity.EntityTaskList;
import java.sql.Timestamp;
import java.util.List;

public interface TaskListRepository  extends CrudRepository<EntityTaskList, Long> {

    @Query(value="SELECT * " +
            "FROM task_list t left outer join orders o ON t.order_id = o.id" +
            " WHERE t.time > ?2 AND t.employee= ?1 AND o.status = "+ EntityOrders.STATUS_NEW +
            "", nativeQuery = true)
    List<EntityTaskList> findByEmployeeAndTimeAfterOrderByTimeAsc(long employeeId, Timestamp plannedTime);

    @Query(value="SELECT * " +
            "FROM task_list t left outer join orders o ON t.order_id = o.id" +
            " WHERE t.time <= ?1 AND o.status = "+ EntityOrders.STATUS_NEW +
            " limit 1", nativeQuery = true)
    List<EntityTaskList> findTop1ByTimeBeforeAndEmployeeOrderByTimeDesc(Timestamp startTime, long employeeId);

    @Query(value="SELECT count(*)" +
            "FROM task_list t left outer join orders o ON t.order_id = o.id" +
            " WHERE t.time <= ?1 AND t.time >= ?2 AND t.employee = ?3 and o.status = "+EntityOrders.STATUS_NEW +
            " limit 1", nativeQuery = true)

    int countByTimeBeforeAndTimeAfterAndEmployee(Timestamp finishTime, Timestamp startTime, long employeeId);

    List<EntityTaskList> findByTimeAfter(Timestamp time);
    List<EntityTaskList> findByTimeBefore(Timestamp time);

    @Query(value="SELECT * " +
            "FROM task_list t left outer join orders o ON t.order_id = o.id" +
            " WHERE t.time < ?1 AND o.status = ?4" +
            " ORDER BY t.time, o.id DESC offset ?2 limit ?3", nativeQuery = true)

    List<EntityTaskList> findOldByStatus(Timestamp time, int offset, int limit, int status);

    @Query(value="SELECT * " +
            "FROM task_list t left outer join orders o ON t.order_id = o.id" +
            " WHERE t.time > ?1 AND o.status = ?4" +
            " ORDER BY t.time, o.id DESC offset ?2 limit ?3", nativeQuery = true)
    List<EntityTaskList> findNewByStatus(Timestamp time, int offset, int limit, int status);

    @Query(value="SELECT * " +
            "FROM task_list t left outer join orders o ON t.order_id = o.id" +
            " WHERE o.status = ?3" +
            " ORDER BY t.time, o.id DESC offset ?1 limit ?2", nativeQuery = true)

    List<EntityTaskList> findByStatus(int offset, int limit, int status);


}

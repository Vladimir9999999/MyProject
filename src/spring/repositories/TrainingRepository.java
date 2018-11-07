package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityTraining;
import java.sql.Timestamp;
import java.util.List;

public interface TrainingRepository extends CrudRepository<EntityTraining,Long> {
    EntityTraining findById(long id);
    List<EntityTraining> findByEmployeeId(long employeeId);
    List<EntityTraining> findTop1ByDateBeforeAndEmployeeIdOrderByDateDesc(Timestamp startTime, long employeeId);
    int countByDateBeforeAndDateAfterAndEmployeeId(Timestamp finishTime, Timestamp startTime, long employeeId);
    EntityTraining findByIdAndShopId(long id, long shopId);
}

package spring.interfaces;

import spring.entity.EntityTraining;
import java.sql.Timestamp;
import java.util.List;

public interface TrainingDao {

    EntityTraining findById(long id);
    EntityTraining save(EntityTraining training);
    List<EntityTraining> findByEmployeeId(long employeeId);
    List<EntityTraining> findByLastTraining(Timestamp startTime, long employeeId);
    int countByPeriod(Timestamp finishTime, Timestamp startTime, long employeeId);
    EntityTraining findByIdAndShopId(long id, long shopId);
}


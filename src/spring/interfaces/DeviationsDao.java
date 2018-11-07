package spring.interfaces;

import spring.entity.EntityDeviations;
import java.sql.Timestamp;
import java.util.List;

public interface DeviationsDao {


    EntityDeviations getByDataAndScheduleId(Timestamp data, long scheduleId);

    EntityDeviations save(EntityDeviations deviation);
    void saveAll(Iterable<EntityDeviations> deviations);
    List <EntityDeviations> selectBySheduleId(long scheduleId);
    List<EntityDeviations> selectByScheduleIdAndDataAfter(long scheduleId, Timestamp date);
    void delete(EntityDeviations deviation);

}

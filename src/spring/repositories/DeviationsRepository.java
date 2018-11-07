package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityDeviations;
import java.sql.Timestamp;
import java.util.List;

public interface DeviationsRepository extends CrudRepository<EntityDeviations,Integer> {


    EntityDeviations findAllByDataAndScheduleId(Timestamp data, long scheduleId);
    void deleteByDataAndScheduleId(Timestamp data, long scheduleId);
    List<EntityDeviations> findByScheduleIdOrderByDataAsc(long scheduleId);
    List<EntityDeviations> findByScheduleIdAndDataAfterOrderByDataAsc(long scheduleId, Timestamp date);

}

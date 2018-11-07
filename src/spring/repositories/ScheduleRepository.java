package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntitySchedule;

public interface ScheduleRepository extends CrudRepository<EntitySchedule, Long> {

    EntitySchedule findEntityScheduleByValue(String value);
    EntitySchedule findById(long id);

}

package spring.interfaces;

import spring.entity.EntitySchedule;

public interface ScheduleDao {
    boolean save(EntitySchedule schedule);
    EntitySchedule selectById(long id);
    EntitySchedule selectByShedule(EntitySchedule schedule);
}

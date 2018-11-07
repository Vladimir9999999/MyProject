package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntitySchedule;
import spring.interfaces.ScheduleDao;
import spring.repositories.ScheduleRepository;

@Service("jpaSchedule")
@Repository
@Transactional
public class ScheduleImplementation implements ScheduleDao {

   private final ScheduleRepository repository;

   @Autowired
    public ScheduleImplementation(ScheduleRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public boolean save(EntitySchedule schedule) {
        repository.save(schedule);
        if(schedule.getId()!=0){
            return true;
        }
        return false;
    }

    @Override
    public EntitySchedule selectById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntitySchedule selectByShedule(EntitySchedule schedule) {
        return repository.findEntityScheduleByValue(schedule.getValue());
    }

}

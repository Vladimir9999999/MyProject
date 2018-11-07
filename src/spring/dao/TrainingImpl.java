package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityTraining;
import spring.interfaces.TrainingDao;
import spring.repositories.TrainingRepository;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service("jpaTraining")
@Transactional
@Repository

public class TrainingImpl implements TrainingDao{

    @Autowired
    private final TrainingRepository repository;

    public TrainingImpl(TrainingRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityTraining findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityTraining save(EntityTraining training) {
        return repository.save(training);
    }

    @Override
    public List<EntityTraining> findByEmployeeId(long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public List<EntityTraining> findByLastTraining(Timestamp startTime, long employeeId) {
        return repository.findTop1ByDateBeforeAndEmployeeIdOrderByDateDesc(startTime,employeeId);
    }

    @Override
    public int countByPeriod(Timestamp finishTime, Timestamp startTime, long employeeId) {
        return repository.countByDateBeforeAndDateAfterAndEmployeeId(finishTime,startTime,employeeId);
    }

    @Override
    public EntityTraining findByIdAndShopId(long id, long shopId) {
        return repository.findByIdAndShopId(id,shopId);
    }
}

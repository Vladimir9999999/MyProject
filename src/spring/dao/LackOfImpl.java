package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityLackOf;
import spring.interfaces.LackOfDao;
import spring.repositories.LackOfRepository;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service("jpaLackOf")
@Transactional
@Repository

public class LackOfImpl implements LackOfDao{

    @Autowired
    private final LackOfRepository repository;

    public LackOfImpl(LackOfRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityLackOf findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityLackOf save(EntityLackOf lackOf) {
        return repository.save(lackOf);
    }

    @Override
    public List<EntityLackOf> findByEmployeeId(long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public List<EntityLackOf> findByLastLackOf(Timestamp startTime, long employeeId) {
        return repository.findTop1ByDateBeforeAndEmployeeIdOrderByDateDesc(startTime,employeeId);
    }

    @Override
    public int countByPeriod(Timestamp finishTime, Timestamp startTime, long employeeId) {
        return repository.countByDateBeforeAndDateAfterAndEmployeeId(finishTime,startTime,employeeId);
    }

    @Override
    public EntityLackOf findByIdAndShopId(long id, long shopId) {
        return repository.findByIdAndShopId(id,shopId);
    }
}

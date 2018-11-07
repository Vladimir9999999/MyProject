package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityDeviations;
import spring.interfaces.DeviationsDao;
import spring.repositories.DeviationsRepository;
import java.sql.Timestamp;
import java.util.List;

@Service("jpaDeviations")
@Repository
@Transactional
public class DeviationsImpl implements DeviationsDao{

    private final DeviationsRepository repository;

    @Autowired
    public DeviationsImpl(DeviationsRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityDeviations getByDataAndScheduleId(Timestamp data, long scheduleId) {
        return repository.findAllByDataAndScheduleId(data,scheduleId);
    }

    @Override
    public EntityDeviations save(EntityDeviations deviations) {

        return repository.save(deviations);

    }

    @Override
    public void saveAll(Iterable<EntityDeviations> deviations) {

        repository.saveAll(deviations);

    }

    @Override
    public List<EntityDeviations> selectBySheduleId(long scheduleId) {
        return repository.findByScheduleIdOrderByDataAsc(scheduleId);
    }

    @Override
    public List<EntityDeviations> selectByScheduleIdAndDataAfter(long shopId, Timestamp date) {
        return repository.findByScheduleIdAndDataAfterOrderByDataAsc(shopId, date);
    }


    @Override
    public void delete(EntityDeviations deviation) {

  //      repository.delete(deviation.getId());
        repository.deleteByDataAndScheduleId(deviation.getData(),deviation.getScheduleId());
    }
}

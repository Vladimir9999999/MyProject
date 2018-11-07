package spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityPolling;
import spring.interfaces.PollingDao;
import spring.repositories.PollingRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service("jpaPolling")
@Transactional
@Repository

public class PollingImpl implements PollingDao {

    private final PollingRepository repository;

    public PollingImpl(PollingRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public List<EntityPolling> findByShopId(long shopId) {
        return repository.findByShopId(shopId);
    }

    @Override
    public EntityPolling findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityPolling save(EntityPolling polling) {
        return repository.save(polling);
    }

    @Override
    public EntityPolling findMyVote(long shopId, long userId) {
        return repository.findByShopIdAndUserId(shopId, userId);
    }

    @Override
    public long counter(long shopId, boolean countFlag) {
        return repository.countByShopIdAndVote(shopId, countFlag);
    }

}

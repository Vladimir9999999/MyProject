package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityCashback;
import spring.interfaces.CashbackDao;
import spring.repositories.CashbackRepository;

@Service("jpaCashback")
@Repository
@Transactional
public class CasbackImpl implements CashbackDao {

    private final CashbackRepository repository;

    @Autowired
    public CasbackImpl(CashbackRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityCashback save(EntityCashback cashback) {
        return repository.save(cashback);
    }

    @Override
    public EntityCashback selectByShopId(long shopId) {
        return repository.findByShopId(shopId);
    }

    @Override
    public void delete(long shopId) {
        repository.deleteByShopId(shopId);
    }
}

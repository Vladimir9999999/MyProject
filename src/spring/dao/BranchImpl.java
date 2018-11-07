package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityBranch;
import spring.entity.EntityShop;
import spring.interfaces.BranchDao;
import spring.repositories.BranchRepository;
import java.util.List;

@Service("jpaBranch")
@Repository
@Transactional
public class BranchImpl implements BranchDao {

    private final BranchRepository repository;

    @Autowired
    public BranchImpl(BranchRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityBranch findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityBranch save(EntityBranch branch) {
        return repository.save(branch);
    }

    @Override
    public void saveAll(List<EntityBranch> listBranches) {
        repository.saveAll(listBranches);
    }

    @Override
    public List<EntityBranch> findAllByShopId(long shopId) {
        return repository.findAllByShopId(shopId);
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByShop(EntityShop shop) {
        repository.deleteAllByShop(shop);
    }
}

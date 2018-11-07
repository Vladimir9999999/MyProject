package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityShop;
import spring.interfaces.ShopDao;
import spring.repositories.ShopRepository;
import java.util.List;

@Service("jpaShop")
@Transactional
@Repository
public class ShopImpl implements ShopDao {

    private final ShopRepository repository;

    @Autowired
    public ShopImpl(ShopRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityShop save(EntityShop shop) {

        return repository.save(shop);

    }

    @Override
    public EntityShop findById(long shopId) {
        return repository.findById(shopId);
    }

    @Override
    public void deleteById(long shopId) {
        repository.deleteById(shopId);
    }

    @Override
    public List<EntityShop> findAllById(long id) {
        return repository.findAllById(id);
    }
}

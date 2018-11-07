package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityUserShops;
import spring.interfaces.UserShopDao;
import spring.repositories.UserShopsRepository;
import java.util.List;

@Service("jpaUserShop")
@Repository
@Transactional

public class UserShopsImpl implements UserShopDao {

   private final UserShopsRepository repository;

    @Autowired
    public UserShopsImpl(UserShopsRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityUserShops findByUserIdAndShop(long user, long shop) {

        return repository.findByUserIdAndShop(user, shop);

    }

    @Override
    public EntityUserShops save(EntityUserShops userShops) {
        return repository.save(userShops);
    }

    @Override
    public List<EntityUserShops> findByUserId(long user) {
        return repository.findByUserId(user);
    }

    @Override
    public List<EntityUserShops> findByShop(long shop) {
        return repository.findByShop(shop);
    }

    @Override
    public List<EntityUserShops> saveAll(List<EntityUserShops> shops) {

        return Lists.newArrayList(repository.saveAll(shops));

    }

    @Override
    public EntityUserShops findById(long buyerId) {
        return repository.findById(buyerId);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}

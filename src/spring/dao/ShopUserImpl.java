package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityShopUser;
import spring.interfaces.ShopUserDao;
import spring.repositories.ShopUserRepository;
import javax.transaction.Transactional;

@Service("jpaShopUser")
@Transactional
@Repository
public class ShopUserImpl implements ShopUserDao{

    private final ShopUserRepository repository;

    @Autowired
    public ShopUserImpl(ShopUserRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityShopUser save(EntityShopUser shopUser) {

        return repository.save(shopUser);

    }

    @Override
    public EntityShopUser selectByUserId(long id) {

        return repository.findByUserId(id);

    }

}

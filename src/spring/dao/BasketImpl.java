package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityBasket;
import spring.interfaces.BasketDao;
import spring.repositories.BasketRepository;

import java.util.List;

@Service("jpaBasket")
@Transactional
@Repository
public class BasketImpl implements BasketDao {

    private final BasketRepository repository;

    @Autowired
    public BasketImpl(BasketRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityBasket save(EntityBasket basket) {

        return repository.save(basket);

    }

    @Override
    public void delete(long shopId, long userId) {

        repository.deleteByShopIdAndUserId(shopId,userId);

    }

    @Override
    public EntityBasket selectByShopIdUserId(long shopId, long userId) {

        return repository.findByShopIdAndUserId(shopId, userId);

    }

    @Override
    public List<EntityBasket> selectByUserId(long userId) {

        return repository.findByUserId(userId);

    }
    public void deleteFromShopIdAndUserId(long userId, long shopId){
        repository.deleteByShopIdAndUserId(shopId,userId);
    }
    public void delete(EntityBasket entityBasket){
        repository.delete(entityBasket);
    }

}

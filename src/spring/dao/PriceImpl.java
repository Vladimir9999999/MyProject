package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityArticle;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityPrice;
import spring.interfaces.PriceDao;
import spring.interfaces.delete.Removable;
import spring.repositories.PriceRepository;
import java.util.List;


@Service("jpaPrice")
@Transactional
@Repository

public class PriceImpl implements PriceDao {

    private final PriceRepository repository;

    @Autowired
    public PriceImpl(PriceRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityPrice save(EntityPrice price) {

        return repository.save(price);

    }

    @Override
    public List<EntityPrice> saveAll(List<EntityPrice> entityPrices) {
        return Lists.newArrayList(repository.saveAll(entityPrices));
    }

    @Override
    public List<EntityPrice> selectByCategoryShop(long categoryShop) {
        return repository.findByCategoryShop(categoryShop);
    }

    @Override
    public boolean isByShopID(long shopId,long id) {
        return repository.existsByShopIdAndId(shopId,id);
    }

    @Override
    public EntityPrice selectByShopIdByID(long shopId, long id) {
        return repository.findByShopIdAndId(shopId, id);
    }

    @Override
    public int countByShopID(long shopId) {
        return repository.countByShopId(shopId);
    }

    @Override
    public List<EntityPrice> findAllByEntityArticles(EntityArticle article) {
        return repository.findAllByEntityArticles(article);
    }

    @Override
    public boolean existsByShopIdAndId(long shopId, long id) {
        return repository.existsByShopIdAndId(shopId,id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll(List<EntityPrice> delPrices) {
        repository.deleteAll(delPrices);
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteAllByCategoryShop(EntityCategoryShop categoryShop) {
        repository.deleteAllByCategoryShop(categoryShop);
    }

    @Override
    public List<EntityPrice> findAllByCategoryShop(EntityCategoryShop categoryShop) {
        return repository.findAllByCategoryShop(categoryShop);
    }

    @Override
    public List<EntityPrice> selectNullPrice(long shopId) {
        return repository.findByShopIdAndCategoryShopOrderByPriorityAsc(shopId,0);
    }

    @Override
    public List<EntityPrice> selectByShopIdIsVisible(long shopId) {
        return repository.findByShopIdAndVisible(shopId,true);
    }

    @Override
    public List<EntityPrice> selectByShopId(long shopId) {
        return repository.findByShopId(shopId);
    }


    @Override
    public boolean save(Removable entity) {

        if(entity instanceof EntityPrice){

            return save((EntityPrice) entity)!=null;

        }
        return false;
    }

    @Override
    public EntityPrice selectById(long id) {
        return repository.findById(id);
    }
}

package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityCategoryService;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityDeleteMarker;
import spring.interfaces.CategoryShopDao;
import spring.interfaces.delete.Removable;
import spring.repositories.CategoryShopRepository;
import java.util.List;


@Service("jpaCategoryShop")
@Repository
@Transactional

public class CategoryShopImpl implements CategoryShopDao {

    private final CategoryShopRepository repository;

    @Autowired
    public CategoryShopImpl(CategoryShopRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityCategoryShop save(EntityCategoryShop categoryShop) {

        return repository.save(categoryShop);

    }

    @Override
    public List<EntityCategoryShop> saveAll(List<EntityCategoryShop> entityCategoryShops) {
        return Lists.newArrayList(repository.saveAll(entityCategoryShops));
    }

    @Override
    public List<EntityCategoryShop> selectByParent(long parent) {
        return repository.findByParent(parent);
    }

    @Override
    public List<EntityCategoryShop> selectByShopId(long shopId) {
        return repository.findByShopIdOrderByPriorityAsc(shopId);
    }

    @Override
    public EntityCategoryShop selectByShopIdAndId(long shopId, long id) {
        return repository.findByShopIdAndId(shopId, id);
    }

    @Override
    public boolean save(Removable entity) {

        if (entity instanceof EntityCategoryShop) {

            return save((EntityCategoryShop) entity) != null;

        }

        return false;
    }

    @Override
    public EntityCategoryShop selectById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityCategoryShop selectByCategory(EntityCategoryService category) {
        return repository.findByCategoryServiceByCategory(category);
    }

    @Override
    public boolean existsParent(long shopId, long categoryId) {
        return repository.existsByShopIdAndId(shopId,categoryId);
    }

    @Override
    public boolean existsCategory(long shopId, EntityCategoryService category) {
        return repository.existsByShopIdAndCategoryServiceByCategory(shopId, category);
    }

    @Override
    public int countByShopId(long shopId) {

        return repository.countByShopId(shopId);

    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll(List<EntityCategoryShop> deleteList) {
        repository.deleteAll(deleteList);
    }

    @Override
    public void deleteAllByCategoryServiceByCategoryAndDeleteMarker(EntityCategoryService categoryService, EntityDeleteMarker deleteMarker) {
        repository.deleteAllByCategoryServiceByCategoryAndDeleteMarker(categoryService,deleteMarker);
    }
}

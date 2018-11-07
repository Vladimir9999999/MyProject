package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityCategoryService;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityDeleteMarker;

import java.util.List;

public interface CategoryShopRepository extends CrudRepository<EntityCategoryShop, Long> {

    List<EntityCategoryShop> findByShopIdOrderByPriorityAsc(long shopId);

    List<EntityCategoryShop> findByParent(long parent);
    EntityCategoryShop findByShopIdAndId(long shopId, long id);
    EntityCategoryShop findById(long id);
    EntityCategoryShop findByCategoryServiceByCategory(EntityCategoryService service);
    boolean existsByShopIdAndId(long shopId, long id);
    boolean existsByShopIdAndCategoryServiceByCategory(long shopId, EntityCategoryService categoryService);
    int countByShopId(long shopId);
    void deleteById(long id);
    void deleteAllByCategoryServiceByCategoryAndDeleteMarker(EntityCategoryService categoryService, EntityDeleteMarker deleteMarker);
}

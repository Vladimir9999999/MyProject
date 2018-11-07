package spring.interfaces;

import spring.entity.EntityCategoryService;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityDeleteMarker;
import spring.interfaces.delete.Remover;
import java.util.List;

public interface CategoryShopDao extends Remover{

    EntityCategoryShop save(EntityCategoryShop categoryShop);

    List<EntityCategoryShop> saveAll(List<EntityCategoryShop> entityCategoryShops);
    List<EntityCategoryShop> selectByParent(long parent);
    List<EntityCategoryShop> selectByShopId(long shopId);

    EntityCategoryShop selectByShopIdAndId(long shopId, long id);
    EntityCategoryShop selectById(long id);
    EntityCategoryShop selectByCategory(EntityCategoryService category);

    boolean existsParent(long shopId, long categoryId);
    boolean existsCategory(long shopId, EntityCategoryService category);

    int countByShopId(long shopId);
    void deleteById(long id);

    void deleteAll(List<EntityCategoryShop> deleteList);

    void deleteAllByCategoryServiceByCategoryAndDeleteMarker(EntityCategoryService categoryService, EntityDeleteMarker deleteMarker);
}

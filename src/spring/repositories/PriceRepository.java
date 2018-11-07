package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityArticle;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityPrice;
import java.util.List;

public interface PriceRepository extends CrudRepository<EntityPrice , Long> {

    EntityPrice findById(long id);
    List<EntityPrice> findByShopIdAndCategoryShopOrderByPriorityAsc(long shopId, long categoryShop);
    List<EntityPrice> findByCategoryShop(long categoryShop);

    List<EntityPrice> findByShopIdAndVisible(long categoryshop, boolean visible);
    List<EntityPrice> findByShopId(long shopId);
    EntityPrice findByShopIdAndId(long shopId, long id);
    int countByShopId(long shopId);
    List<EntityPrice> findAllByEntityArticles(EntityArticle article);

    boolean existsByShopIdAndId(long shopId, long id);
    void deleteById(long id);
    boolean existsById(long id);

    void deleteAllByCategoryShop(EntityCategoryShop categoryShop);
    List<EntityPrice> findAllByCategoryShop(EntityCategoryShop categoryShop);
}

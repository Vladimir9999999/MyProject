package spring.interfaces;

import spring.entity.EntityArticle;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityPrice;
import spring.interfaces.delete.Remover;
import java.util.List;

public interface PriceDao extends Remover{

    EntityPrice save(EntityPrice price);

    List<EntityPrice> saveAll(List<EntityPrice> entityPrices);
    List<EntityPrice> selectByCategoryShop(long categoryShop);
    List<EntityPrice> selectNullPrice(long shopId);
    List<EntityPrice> selectByShopIdIsVisible(long shopId);
    List<EntityPrice> selectByShopId(long shopId);

    boolean isByShopID(long shop_id, long id);
    EntityPrice selectByShopIdByID(long shopId, long id);
    EntityPrice selectById(long id);
    int countByShopID(long shopId);

    List<EntityPrice> findAllByEntityArticles(EntityArticle article);
    boolean existsByShopIdAndId(long shopId, long id);
    void deleteById(long id);
    void deleteAll(List<EntityPrice> delPrices);
    boolean existsById(long id);

    void deleteAllByCategoryShop(EntityCategoryShop categoryShop);
    List<EntityPrice> findAllByCategoryShop(EntityCategoryShop categoryShop);
}

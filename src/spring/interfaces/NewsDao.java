package spring.interfaces;

import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityNews;
import spring.entity.EntityShopsCluster;
import java.util.List;

public interface NewsDao {

    EntityNews findById(long id);
    List<EntityNews> findByShopId(long shopId);
    EntityNews save(EntityNews news);
    EntityNews findLastByShopId(long shopId);
    List<EntityNews> findShopArray(WebApplicationContext ctx, EntityShopsCluster entityShopsCluster);
}


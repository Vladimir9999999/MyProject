package spring.interfaces;

import spring.entity.EntityDefaultService;
import java.util.List;

public interface DefaultServiceDao {

    EntityDefaultService save(EntityDefaultService defaultService);
    List<EntityDefaultService> saveAll(List<EntityDefaultService> defaultServices);
    List<EntityDefaultService> findByCategory(long categoryId);
}

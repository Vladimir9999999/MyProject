package spring.interfaces;

import spring.entity.EntityPhoto;
import java.util.List;

public interface PhotoDao {

    EntityPhoto findById(long id);
    List<EntityPhoto> findByShopId(long id);
    EntityPhoto findLastByShopId(long shopId);
    EntityPhoto save(EntityPhoto photo);

}

package spring.interfaces;

import spring.entity.EntityComment;
import java.util.List;

public interface CommentDao {

    EntityComment save(EntityComment comment);
    EntityComment findById(long id);
    EntityComment findByIdAndShopId(long id, long shopId);
    List<EntityComment> findByOrderId(Long orderId);
    List<EntityComment> findByClientId(Long clientId);
    List<EntityComment> findByShopId(long shopId);

    void deleteAll(List<EntityComment> comments);
}

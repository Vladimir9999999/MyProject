package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityComment;
import java.util.List;

public interface CommentRepository extends CrudRepository<EntityComment,Long> {

    EntityComment findById(long id);
    EntityComment findByIdAndShopId(long id, long shopId);
    List<EntityComment> findByOrderId(Long orderId);
    List<EntityComment> findByClientId(Long clientId);
    List<EntityComment> findByShopId(long shopId);
}

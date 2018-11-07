package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityPolling;
import java.util.List;

public interface PollingRepository extends CrudRepository<EntityPolling,Long> {

        EntityPolling findById(long id);
        List<EntityPolling> findByShopId(long shopId);
        EntityPolling findByShopIdAndUserId(long shopId, long userId);
        long countByShopIdAndVote(long shopId, boolean vote);
}

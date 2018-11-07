package spring.interfaces;

import spring.entity.EntityPolling;
import java.util.List;

public interface PollingDao {

    List<EntityPolling> findByShopId(long Id);
    EntityPolling findById(long id);
    EntityPolling save(EntityPolling polling);
    EntityPolling findMyVote(long shopId, long userId);

    long counter(long shopId, boolean countFlag);
}

package spring.interfaces;

import spring.entity.EntityOrders;
import java.sql.Timestamp;
import java.util.List;

public interface OrderDao {

    EntityOrders save(EntityOrders order);
    EntityOrders findById(long id);

    List<EntityOrders> findByShopId(long shopId);
    List<EntityOrders> findLastModificationAfter(Timestamp lastMod, long shopId);
    List<EntityOrders> findByShopIdAndUserId(int offset, int limit, long shopId, long UserId);
    List<EntityOrders> findOldByStatus(Timestamp before, int status, int offset, int limit);
    List<EntityOrders> findNewByStatus(Timestamp after, int status, int offset, int limit);

    List<EntityOrders> findByStatus(int status, long shopId);

    List<EntityOrders> findByNoStatus(int status, long shopId);

    List<EntityOrders> findAll();
    void deleteAll();
}

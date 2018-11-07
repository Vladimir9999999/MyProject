package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityCashback;

public interface CashbackRepository extends CrudRepository<EntityCashback ,Long> {
    EntityCashback findByShopId(long shopId);
    void deleteByShopId(long shopId);
}

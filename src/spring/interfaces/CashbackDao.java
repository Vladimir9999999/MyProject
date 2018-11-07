package spring.interfaces;

import spring.entity.EntityCashback;

public interface CashbackDao {
    EntityCashback save(EntityCashback cashback);
    EntityCashback selectByShopId(long shopId);
    void delete(long shopId);
}

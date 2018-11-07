package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityBasket;

import java.util.List;

public interface BasketRepository extends CrudRepository<EntityBasket , Long>{

    EntityBasket findByShopIdAndUserId(long shopId, long userId);
    void deleteByShopIdAndUserId(long shopId, long userId);

    List<EntityBasket> findByUserId(long user_id);

    void deleteAllByShopIdAndUserId(long shopId, long userId);

}

package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityUserShops;
import java.util.List;

public interface UserShopsRepository extends CrudRepository <EntityUserShops,Long> {
    EntityUserShops findByUserIdAndShop(long user, long shop);
    List<EntityUserShops> findByUserId(long user);
    List<EntityUserShops> findByShop(long shop);
    EntityUserShops findById(long buyerId);
    void deleteById(long id);
}

package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityPlayer;
import java.util.List;

public interface PlayerRepository extends CrudRepository<EntityPlayer, Long> {

    EntityPlayer findById(long id);
    List<EntityPlayer> findByShopId(long shopId);
    boolean existsById(long userId);
    void deleteById(long id);
}

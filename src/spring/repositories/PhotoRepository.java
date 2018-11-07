package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityPhoto;
import java.util.ArrayList;
import java.util.List;

public interface PhotoRepository extends CrudRepository<EntityPhoto,Long> {

    EntityPhoto findById(long id);
    List<EntityPhoto> findByShopId(long shopId);
    EntityPhoto findTopByShopIdOrderByIdDesc(long shopId);
    ArrayList<EntityPhoto> findAllRecentByShopId(long shopId);

}

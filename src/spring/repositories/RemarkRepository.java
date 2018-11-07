package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityRemark;
import java.util.List;

public interface RemarkRepository extends CrudRepository<EntityRemark , Long> {

    List<EntityRemark> findByAdresseAndType(long adresse, int type);
    List<EntityRemark> findByAdresseAndTypeAndMarketPlace(long adresse, int type, long marketplace);
    EntityRemark findById(long id);


}

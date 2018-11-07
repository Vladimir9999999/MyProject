package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityDefaultService;
import java.util.List;

public interface DefaultServiceRepository extends CrudRepository<EntityDefaultService , Integer> {

    List<EntityDefaultService> findByCategory(long category);

}

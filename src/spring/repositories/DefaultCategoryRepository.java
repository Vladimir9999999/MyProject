package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityDefaultCategory;
import java.util.List;

public interface DefaultCategoryRepository extends CrudRepository<EntityDefaultCategory, Integer> {

    List<EntityDefaultCategory> findByParent(long parent);


}

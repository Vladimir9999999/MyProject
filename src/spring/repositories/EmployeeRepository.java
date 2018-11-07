package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityEmployee;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<EntityEmployee, Long> {

    List<EntityEmployee> findByShopIdOrderByIdAsc(long id);
    EntityEmployee findById(long id);
    EntityEmployee findByShopIdAndId(long shopId, long id);
    boolean existsByShopIdAndId(long shopId, long id);
}

package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityAmount;
import java.util.List;

public interface AmountRepository extends CrudRepository<EntityAmount,Long> {

        EntityAmount findById(long id);
        List<EntityAmount> findByEmployeeId(long employeeId);
}

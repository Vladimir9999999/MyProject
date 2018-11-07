package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityAppraisal;
import java.util.List;

public interface AppraisalRepository extends CrudRepository<EntityAppraisal,Long> {

    EntityAppraisal findById(long id);
    List<EntityAppraisal> findByEmployeeId(long employeeId);
}

package spring.interfaces;

import spring.entity.EntityAppraisal;
import java.util.List;

public interface AppraisalDao {

    EntityAppraisal findById(long id);
    EntityAppraisal save(EntityAppraisal appraisal);
    List<EntityAppraisal> findByEmployeeId(long employeeId);

}

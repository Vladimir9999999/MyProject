package spring.interfaces;

import spring.entity.EntityAmount;
import java.util.List;

public interface AmountDao {

    EntityAmount findById(long id);
    EntityAmount save(EntityAmount commentAmount);
    List<EntityAmount> findByEmployeeId(long employeeId);
}

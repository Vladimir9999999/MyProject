package spring.interfaces;

import spring.entity.EntityEmployee;
import spring.interfaces.delete.Removable;
import spring.interfaces.delete.Remover;
import java.util.List;

public interface EmployeeDao extends Remover{

    boolean save(Removable employee);
    boolean save(EntityEmployee emploee);
    EntityEmployee selectByShopIdAndId(long shopId, long id);
    List<EntityEmployee> selectByShopId(long shopId);
    EntityEmployee selectById(long id);
    boolean existByShopIdAndId(long shopId, long id);
    void deleteById(long employeeId);

}

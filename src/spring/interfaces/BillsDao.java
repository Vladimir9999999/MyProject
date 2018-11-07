package spring.interfaces;

import spring.entity.EntityBills;
import java.util.List;

public interface BillsDao {

    EntityBills save(EntityBills bills);
    EntityBills selectById(long id);

    List<EntityBills> selectByOrderId(long orderId);
    List<EntityBills> selectByOrderIdAndType(long orderId, int type);
    List<EntityBills> selectAll();
    void deleteById(long id);
    void deleteAll(List<EntityBills> bills);

}

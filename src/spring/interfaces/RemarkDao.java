package spring.interfaces;

import spring.entity.EntityRemark;
import java.util.List;

public interface RemarkDao {

    EntityRemark save(EntityRemark remark);
    List<EntityRemark> findByAddresseAndType(long addresse, int type);
    List<EntityRemark> findByAdresseAndTypeAndMarketplace(long adresse, int type, long marketplace);

}

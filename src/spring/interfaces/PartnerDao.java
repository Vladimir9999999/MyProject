package spring.interfaces;

import spring.entity.EntityPartners;
import java.util.List;

public interface PartnerDao {

    EntityPartners save(EntityPartners partners);
    List<EntityPartners> selectPartners(long shopId);
    int countMyPartner(long shopId);
    EntityPartners selectPartner(long shopId1, long shopId2);
}

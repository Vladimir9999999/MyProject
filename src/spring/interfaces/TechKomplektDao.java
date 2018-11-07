package spring.interfaces;

import spring.entity.EntityTechKomplekt;
import java.util.List;

public interface TechKomplektDao {

    EntityTechKomplekt save(EntityTechKomplekt entityTechKomplekt);

    EntityTechKomplekt selectKomplect(long id, long shopId);

    List<EntityTechKomplekt> selectByProduct(long product, long shopId);

}

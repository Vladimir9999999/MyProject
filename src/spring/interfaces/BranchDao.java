package spring.interfaces;

import spring.entity.EntityBranch;
import spring.entity.EntityShop;

import java.util.List;

public interface BranchDao {

    EntityBranch findById(long id);
    EntityBranch save(EntityBranch branch);
    void saveAll(List<EntityBranch> listBranches);
    List<EntityBranch> findAllByShopId(long shopId);
    boolean existsById(long id);
    void deleteById(long id);
    void deleteAllByShop(EntityShop shop);
}

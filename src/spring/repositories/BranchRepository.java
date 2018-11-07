package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityBranch;
import spring.entity.EntityShop;

import java.util.List;

public interface BranchRepository extends CrudRepository<EntityBranch, Long> {

    EntityBranch findById(long id);
    List<EntityBranch> findAllByShopId(long shopId);
    boolean existsById(long id);
    void deleteById(long id);
    void deleteAllByShop(EntityShop shop);
}

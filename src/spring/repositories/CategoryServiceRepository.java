package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityCategoryService;

public interface CategoryServiceRepository extends CrudRepository<EntityCategoryService, Integer> {

    //List<EntityCategoryService> findAllByShopId(long idShop);
    boolean existsByName(String name);
    EntityCategoryService findByName(String name);
    void deleteByName(String name);
}

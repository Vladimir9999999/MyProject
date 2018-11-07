package spring.interfaces;

import spring.entity.EntityCategoryService;

public interface CategoryServiceDao {
    EntityCategoryService save(EntityCategoryService entityCategoryService);
    EntityCategoryService findByName(String name);
    boolean exist(String name);
    void deleteByName(String name);
}

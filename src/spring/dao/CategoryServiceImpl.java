package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityCategoryService;
import spring.interfaces.CategoryServiceDao;
import spring.repositories.CategoryServiceRepository;

@Service("jpaCategoryService")
@Repository
@Transactional

public class CategoryServiceImpl implements CategoryServiceDao {

    private final CategoryServiceRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryServiceRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }


    @Override
    public EntityCategoryService save(EntityCategoryService entityCategoryService) {

        return repository.save(entityCategoryService);

    }

    @Override
    public EntityCategoryService findByName(String name) {
        return repository.findByName(name);
    }


    @Override
    public boolean exist(String name) {

        return repository.existsByName(name);

    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }
}

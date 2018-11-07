package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityDefaultCategory;
import spring.interfaces.DefaultCategoryDAO;
import spring.repositories.DefaultCategoryRepository;
import java.util.List;

@Service("jpaDefaultCategory")
@Repository
@Transactional
public class DefaultCategoryImpl implements DefaultCategoryDAO {

    private final DefaultCategoryRepository repository;

    @Autowired
    public DefaultCategoryImpl(DefaultCategoryRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityDefaultCategory save(EntityDefaultCategory defaultCategory) {

        return repository.save(defaultCategory);

    }

    @Override
    public List<EntityDefaultCategory> selectByParent(long parentId) {

        return repository.findByParent(parentId);

    }

    @Override
    public List<EntityDefaultCategory> selectAll() {
        return Lists.newArrayList(repository.findAll());
    }


}

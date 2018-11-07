package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityDefaultService;
import spring.interfaces.DefaultServiceDao;
import spring.repositories.DefaultServiceRepository;
import java.util.List;

@Service("jpaDefaultService")
@Repository
@Transactional

public class DefaultServiceImpl implements DefaultServiceDao {

    private final DefaultServiceRepository repository;

    @Autowired
    public DefaultServiceImpl(DefaultServiceRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityDefaultService save(EntityDefaultService defaultService) {
        return repository.save(defaultService);
    }

    @Override
    public List<EntityDefaultService> saveAll(List<EntityDefaultService> defaultServices) {

        return Lists.newArrayList(repository.saveAll(defaultServices));

    }

    @Override
    public List<EntityDefaultService> findByCategory(long category) {

        return repository.findByCategory(category);

    }

}

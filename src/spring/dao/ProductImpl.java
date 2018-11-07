package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityProduct;
import spring.interfaces.ProductDao;
import spring.repositories.ProductRepository;

@Service("jpaProduct")
@Repository
@Transactional
public class ProductImpl implements ProductDao {

    private final ProductRepository repository;

    @Autowired
    public ProductImpl(ProductRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityProduct save(EntityProduct product) {
        return repository.save(product);
    }

    @Override
    public EntityProduct selectByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public EntityProduct findById(long shopId) {
        return repository.findById(shopId);
    }
}

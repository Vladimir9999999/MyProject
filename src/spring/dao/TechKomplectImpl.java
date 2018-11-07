package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityTechKomplekt;
import spring.interfaces.TechKomplektDao;
import spring.repositories.TechKomplectRepository;
import java.util.List;

@Service("jpaTectKomplekt")
@Transactional
@Repository
public class TechKomplectImpl implements TechKomplektDao {

    final
    TechKomplectRepository repository;

    @Autowired
    public TechKomplectImpl(TechKomplectRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityTechKomplekt save(EntityTechKomplekt entityTechKomplekt) {
        return repository.save(entityTechKomplekt);
    }

    @Override
    public EntityTechKomplekt selectKomplect(long id, long shopId) {
        return repository.findByShopIdAndId(shopId,id);
    }

    @Override
    public List<EntityTechKomplekt> selectByProduct(long product, long shopId) {

        return repository.findByShopIdAndProduct(shopId,product);

    }


}

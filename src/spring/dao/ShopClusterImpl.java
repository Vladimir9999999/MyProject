package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityShopsCluster;
import spring.interfaces.ShopClusterDao;
import spring.repositories.ShopsClusterRepository;
import javax.transaction.Transactional;

@Service("jpaShopCluster")
@Repository
@Transactional
public class ShopClusterImpl implements ShopClusterDao {

   private final ShopsClusterRepository repository;

   @Autowired
    public ShopClusterImpl(ShopsClusterRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }


    @Override
    public EntityShopsCluster selectByShopCluster(String cluster) {

        return repository.findByShops(cluster);

    }

    @Override
    public EntityShopsCluster save(EntityShopsCluster cluster) {

        return repository.save(cluster);

    }
}

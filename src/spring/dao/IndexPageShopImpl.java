package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.EntityIndexPageShop;
import spring.interfaces.IndexPageShopDao;
import spring.repositories.IndexPageShopRepository;

import java.util.List;

@Repository
@Service("jpaIndexPageShop")
@Transactional
public class IndexPageShopImpl implements IndexPageShopDao {
    final
    IndexPageShopRepository repository;

    @Autowired
    public IndexPageShopImpl(IndexPageShopRepository repository) {
        this.repository = repository;
    }

    @Override
    public EntityIndexPageShop save(EntityIndexPageShop entityIndexPageShop) {
        return repository.save(entityIndexPageShop);
    }

    @Override
    public List<EntityIndexPageShop> selectByShopId(long shopId) {

        return repository.findTopByShopId(shopId);

    }

    @Override
    public EntityIndexPageShop selectById(long id) {
        return repository.findById(id);
    }
}

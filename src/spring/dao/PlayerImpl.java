package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityPlayer;
import spring.interfaces.PlayerDao;
import spring.repositories.PlayerRepository;
import java.util.List;

@Service("jpaPlayer")
@Transactional
@Repository
public class PlayerImpl implements PlayerDao {

    private final PlayerRepository repository;

    @Autowired
    public PlayerImpl(PlayerRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityPlayer save(EntityPlayer player) {
        return repository.save(player);
    }

    @Override
    public EntityPlayer findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<EntityPlayer> findByShopId(long shopId) {
        return repository.findByShopId(shopId);
    }

    @Override
    public boolean existsById(long userId) {
        return repository.existsById(userId);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}

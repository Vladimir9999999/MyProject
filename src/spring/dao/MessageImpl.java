package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityMessage;
import spring.entity.EntityUserShops;
import spring.interfaces.MessageDao;
import spring.repositories.MessageRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service("jpaMessage")
@Transactional
@Repository

public class MessageImpl implements MessageDao {

    private final MessageRepository repository;

    @Autowired
    public MessageImpl(MessageRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityMessage save(EntityMessage message) {
        return repository.save(message);
    }

    @Override
    public EntityMessage findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<EntityMessage> findAllByEntityUserShopsList(EntityUserShops entityUserShop) {
        return repository.findAllByEntityUserShopsList(entityUserShop);
    }

    @Override
    public void deleteAllByEntityUserShopsList(EntityUserShops entityUserShops) {
        repository.deleteAllByEntityUserShopsList(entityUserShops);
    }
}

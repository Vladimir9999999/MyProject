package spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityComment;
import spring.interfaces.CommentDao;
import spring.repositories.CommentRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service("jpaComment")
@Repository
@Transactional

public class CommentImpl implements CommentDao {

    private final CommentRepository repository;

    public CommentImpl(CommentRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityComment save(EntityComment comment) {
        return repository.save(comment);
    }

    @Override
    public EntityComment findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityComment findByIdAndShopId(long id, long shopId) {
        return repository.findByIdAndShopId(id,shopId);
    }

    @Override
    public List<EntityComment> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public List<EntityComment> findByClientId(Long clientId) {
        return repository.findByClientId(clientId);
    }

    @Override
    public List<EntityComment> findByShopId(long shopId) {
        return repository.findByShopId(shopId);
    }

    @Override
    public void deleteAll(List<EntityComment> comments) {
        repository.deleteAll();
    }
}

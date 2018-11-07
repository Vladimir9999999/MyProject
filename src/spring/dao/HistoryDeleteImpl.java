package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityHistoryDelete;
import spring.interfaces.HistoryDeleteDao;
import spring.repositories.HistoryDeleteRepository;

@Service("jpaHistoryDelete")
@Repository
@Transactional
public class HistoryDeleteImpl implements HistoryDeleteDao {

   private final HistoryDeleteRepository repository;

   @Autowired
    public HistoryDeleteImpl(HistoryDeleteRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityHistoryDelete findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityHistoryDelete save(EntityHistoryDelete historyDelete) {
        return repository.save(historyDelete);
    }
}
package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityTransactions;
import spring.interfaces.TransactionDao;
import spring.repositories.TransactionRepository;

import java.util.List;

@Service("jpaTransactions")
@Repository
@Transactional
public class TransactionsImpl implements TransactionDao {

    private final TransactionRepository repository;

    @Autowired
    public TransactionsImpl(TransactionRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityTransactions save(EntityTransactions transaction) {

        return repository.save(transaction);

    }

    @Override
    public EntityTransactions findById(long transactionId) {

        return repository.findById( transactionId);

    }

    @Override
    public List<EntityTransactions> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}

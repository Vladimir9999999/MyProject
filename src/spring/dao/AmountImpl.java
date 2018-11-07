package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityAmount;
import spring.interfaces.AmountDao;
import spring.repositories.AmountRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service("jpaAmount")
@Transactional
@Repository

public class AmountImpl implements AmountDao {

    @Autowired
    private final AmountRepository repository;

    public AmountImpl(AmountRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityAmount findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityAmount save(EntityAmount amount) {
        return repository.save(amount);
    }

    @Override
    public List<EntityAmount> findByEmployeeId(long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }
}

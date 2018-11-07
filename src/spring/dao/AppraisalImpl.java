package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityAppraisal;
import spring.interfaces.AppraisalDao;
import spring.repositories.AppraisalRepository;
import java.util.List;

@Service("jpaAppraisal")
@Transactional
@Repository

public class AppraisalImpl implements AppraisalDao {

    @Autowired
    private final AppraisalRepository repository;

    public AppraisalImpl(AppraisalRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityAppraisal findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityAppraisal save(EntityAppraisal appraisal) {
        return repository.save(appraisal);
    }

    @Override
    public List<EntityAppraisal> findByEmployeeId(long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }
}

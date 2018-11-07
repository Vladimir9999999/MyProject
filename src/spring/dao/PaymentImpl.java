package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityPayment;
import spring.interfaces.PaymentDao;
import spring.repositories.PaymentRepository;
import java.util.List;

@Service("jpaPayment")
@Transactional
@Repository

public class PaymentImpl implements PaymentDao {

    private final PaymentRepository repository;

    @Autowired
    public PaymentImpl(PaymentRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityPayment save(EntityPayment payment) {
        return repository.save(payment);
    }

    @Override
    public EntityPayment findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<EntityPayment> findByUserId(long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public EntityPayment findByUserIdAndYearAndMonthAndDay(long userId, int year, int month, int day) {
        return repository.findByUserIdAndYearAndMonthAndDay(userId,year,month,day);
    }

    @Override
    public List<EntityPayment> findByShopId(long shopId) {
        return repository.findByShopId(shopId);
    }

    @Override
    public List<EntityPayment> findAllByYearAndMonth(Integer year, Integer month) {
        return repository.findAllByYearAndMonth(year,month);
    }

    @Override
    public void deleteAllByUserId(long userId) {
        repository.deleteAllByUserId(userId);
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}

package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityBills;
import spring.interfaces.BillsDao;
import spring.repositories.BillsRepository;
import java.util.List;


@Service ("jpaBills")
@Repository
@Transactional
public class BillsImpl implements BillsDao {

    private final BillsRepository repository;

    @Autowired
    public BillsImpl(BillsRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityBills save(EntityBills bills) {
        return repository.save(bills);
    }

    @Override
    public EntityBills selectById(long id) {

        return repository.findById(id);

    }

    @Override
    public List<EntityBills> selectByOrderId(long orderId) {
        return repository.findByOrderId(orderId);
    }

    @Override
    public List<EntityBills> selectByOrderIdAndType(long orderId, int type) {
        return repository.findByOrderIdAndType(orderId,type);
    }

    @Override
    public List<EntityBills> selectAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public void deleteById(long id) {

        repository.deleteById(id);

    }

    @Override
    public void deleteAll(List<EntityBills> bills) {
        repository.deleteAll();
    }

}

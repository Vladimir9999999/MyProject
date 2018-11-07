package spring.dao;

import com.google.common.collect.Lists;
import dao.OrderEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityOrders;
import spring.interfaces.OrderDao;
import spring.repositories.OrderRepository;
import java.sql.Timestamp;
import java.util.List;

@Service("jpaOrder")
@Repository
@Transactional
public class OrderImpl implements OrderDao {

    private final OrderRepository repository;
    private OrderEntityDao orderEntityDao = new OrderEntityDao();

    @Autowired
    public OrderImpl(OrderRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityOrders save(EntityOrders order) {
        return repository.save(order);
    }

    @Override
    public EntityOrders findById(long id) {

        return repository.findById(id);

    }



    @Override
    public List<EntityOrders> findByShopId(long shopId) {
        return repository.findByShopId(shopId);
    }

    @Override
    public List<EntityOrders> findLastModificationAfter(Timestamp lastMod , long shopId) {

        return repository.findAllByLastModificationAfterAndShopId(lastMod,shopId);

    }

    @Override
    public List<EntityOrders> findByShopIdAndUserId(int offset, int limit, long shopId, long userId) {

        return orderEntityDao.selectByShopIdAndUserId(shopId,userId,offset,limit);

    }

    @Override
    public List<EntityOrders> findOldByStatus(Timestamp before, int status, int offset, int limit) {

        return orderEntityDao.findOldByStatus(before, status, offset, limit);

    }

    @Override
    public List<EntityOrders> findNewByStatus(Timestamp after, int status, int offset, int limit) {

        return orderEntityDao.findNewByStatus(after, status, offset, limit);

    }

    @Override
    public List<EntityOrders> findByStatus(int status, long shopId) {

        return repository.findAllByStatusAndShopId(status,shopId);

    }

    @Override
    public List<EntityOrders> findByNoStatus(int status, long shopId) {

        return repository.findAllByNoStatus(status,shopId);

    }


    @Override
    public List<EntityOrders> findAll() {

        return Lists.newArrayList(repository.findAll());

    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }


}

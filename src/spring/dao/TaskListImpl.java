package spring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityOrders;
import spring.entity.EntityTaskList;
import spring.interfaces.TaskListDao;
import spring.repositories.TaskListRepository;
import java.sql.Timestamp;
import java.util.List;


@Service ("jpaTaskList")
@Repository
@Transactional


public class TaskListImpl implements TaskListDao {

    private dao.TaskListDao taskListDao= new dao.TaskListDao();

    private final TaskListRepository repository;

    @Autowired
    public TaskListImpl(TaskListRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityTaskList save(EntityTaskList entityTaskList) {

        return repository.save(entityTaskList);

    }

/*    @Override
    public List<EntityTaskList> findByOrdeId(long orderId) {
        return repository.findByOrderId(orderId);
    }*/

    @Override
    public List<EntityTaskList> findByEmployeeId(long employeeId, Timestamp time) {

        //System.out.println("AHTING!!!! "+employeeId+":"+time);

        return repository.findByEmployeeAndTimeAfterOrderByTimeAsc(employeeId,time);

    }

    @Override
    public EntityTaskList findOneBeforePlannedTime(Timestamp plannedTime ,long employeeId){
        List<EntityTaskList> beforeOrder = repository.findTop1ByTimeBeforeAndEmployeeOrderByTimeDesc(plannedTime,employeeId);
        if(beforeOrder.size() == 0){
            return null;
        }
        return beforeOrder.get(0);
    }


    @Override
    public int countOderPeriod(Timestamp startTime, Timestamp finishTime, long employeeId) {

        return repository.countByTimeBeforeAndTimeAfterAndEmployee(finishTime, startTime, employeeId);

    }

    @Override
    public List<EntityTaskList> findByTimeAfter(Timestamp time) {
        return repository.findByTimeAfter(time);
    }

    @Override
    public EntityTaskList deleteByOrder(EntityOrders entityOrders) {
         taskListDao.deleteTasck(entityOrders.getId());
            return null;
    }

    @Override
    public List<EntityTaskList> findNew(Timestamp time, int offset, int limit, int status) {
        return repository.findNewByStatus(time, offset, limit, status);
    }

    @Override
    public List<EntityTaskList> findByStatus(int offset, int limit, int status) {
        return repository.findByStatus(offset, limit, status);
    }

    @Override
    public List<EntityTaskList> findOld(Timestamp time, int offset, int limit, int status) {

        return repository.findOldByStatus(time,offset,limit,status);

    }
}

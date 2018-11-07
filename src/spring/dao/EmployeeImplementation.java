package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityEmployee;
import spring.interfaces.EmployeeDao;
import spring.interfaces.delete.Removable;
import spring.repositories.EmployeeRepository;
import java.util.List;


@Service("jpaEmployee")
@Repository
@Transactional
public class EmployeeImplementation implements EmployeeDao{

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeImplementation(EmployeeRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public boolean save(Removable employee) {

        if(employee  instanceof EntityEmployee){

            return save((EntityEmployee) employee);

        }
        return false;
    }

    @Override
    public boolean save(EntityEmployee employee)
    {

        repository.save(employee);
        return true;

    }

    @Override
    public EntityEmployee selectByShopIdAndId(long shopId, long id) {

        return repository.findByShopIdAndId(shopId, id);

    }

    @Override
    public List<EntityEmployee> selectByShopId(long shopId) {

        return repository.findByShopIdOrderByIdAsc(shopId);

    }

    @Override
    public EntityEmployee selectById(long id) {
        return repository.findById(id);
    }

    @Override
    public boolean existByShopIdAndId(long shopId, long id) {
        return repository.existsByShopIdAndId(shopId, id);
    }

    @Override
    public void deleteById(long employeeId) {

        repository.deleteById(employeeId);

    }
}

package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityPersonalData;
import spring.interfaces.PersonalDao;
import spring.repositories.PersonalDataRepository;


@Service("jpaPersonalDaoService")
@Transactional
@Repository

public class PersonalDataImpl implements PersonalDao {

    private final PersonalDataRepository repository;

    @Autowired
    public PersonalDataImpl(PersonalDataRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }


    @Override
    public boolean save(EntityPersonalData personalData) {

        repository.save(personalData);

        return true;
    }

    @Override
    public EntityPersonalData selectByEmployeeId(long employeeId) {

        return repository.findByEmployeeId(employeeId);

    }
}

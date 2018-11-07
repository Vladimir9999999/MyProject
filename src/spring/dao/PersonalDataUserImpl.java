package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityPersonalDataUser;
import spring.interfaces.PersonalDataUserDao;
import spring.repositories.PersonalDataUserRepository;
import javax.transaction.Transactional;


@Service("jpaPersonalDataUser")
@Transactional
@Repository

public class PersonalDataUserImpl implements PersonalDataUserDao {

    private final PersonalDataUserRepository repository;

    @Autowired
    public PersonalDataUserImpl(PersonalDataUserRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityPersonalDataUser findById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityPersonalDataUser save(EntityPersonalDataUser personalDataUser) {
        return repository.save(personalDataUser);
    }

    @Override
    public EntityPersonalDataUser findByUserId(long userId) {
        return repository.findByUserId(userId);
    }
}

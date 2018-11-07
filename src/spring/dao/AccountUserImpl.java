package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityAccountUsers;
import spring.interfaces.AccountUserDao;
import spring.repositories.AccountUserRepository;

@Service("jpaAccountUser")
@Repository
@Transactional
public class AccountUserImpl implements AccountUserDao{

    private final AccountUserRepository repository;

    @Autowired
    public AccountUserImpl(AccountUserRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityAccountUsers save(EntityAccountUsers accountUsers) {
        return repository.save(accountUsers);
    }

    @Override
    public EntityAccountUsers selectById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityAccountUsers selectByMobile(long mobile) {
        return repository.findByLogin(String.valueOf(mobile));
    }

}

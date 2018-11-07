package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityAccountShop;
import spring.interfaces.AccountShopDao;
import spring.repositories.AccountShopRepository;

@Service("jpaAccountShop")
@Repository
@Transactional
public class AccountShopImpl implements AccountShopDao {

    private final AccountShopRepository repository;

    @Autowired
    public AccountShopImpl(AccountShopRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityAccountShop findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void save(EntityAccountShop accountShop) {
        repository.save(accountShop);
    }
}

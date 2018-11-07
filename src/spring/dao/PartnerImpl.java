package spring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityPartners;
import spring.interfaces.PartnerDao;
import spring.repositories.PartnersRepository;
import java.util.List;

@Service("jpaPartner")
@Transactional
@Repository
public class PartnerImpl implements PartnerDao {

    final
    PartnersRepository repository;

    @Autowired
    public PartnerImpl(PartnersRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityPartners save(EntityPartners partners) {

        return repository.save(partners);

    }

    @Override
    public List<EntityPartners> selectPartners(long shopId) {

        return repository.findByShop1OrShop2(shopId,shopId);

    }

    @Override
    public int countMyPartner(long shopId) {
        return repository.countByShop1OrShop2(shopId,shopId);
    }

    @Override
    public EntityPartners selectPartner(long shopId1, long shopId2) {
        return repository.findByShop1AndShop2(shopId1,shopId2);
    }

}

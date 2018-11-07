package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityRemark;
import spring.interfaces.RemarkDao;
import spring.repositories.RemarkRepository;
import java.util.List;

@Service("jpaRemark")
@Repository
@Transactional
public class RemarkImpl implements RemarkDao {

    private final RemarkRepository repository;

    @Autowired
    public RemarkImpl(RemarkRepository repository) {

        Assert.notNull(repository, "repository - null");
        this.repository = repository;

    }

    @Override
    public EntityRemark save(EntityRemark remark) {
        return repository.save(remark);
    }

    @Override
    public List<EntityRemark> findByAddresseAndType(long addresse, int type) {
        return null;
    }

    @Override
    public List<EntityRemark> findByAdresseAndTypeAndMarketplace(long adresse, int type, long marketplace) {
        return repository.findByAdresseAndTypeAndMarketPlace(adresse, type, marketplace);
    }
}

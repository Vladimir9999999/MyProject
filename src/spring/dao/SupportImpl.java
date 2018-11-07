package spring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.EntitySupport;
import spring.interfaces.SupportDao;
import spring.repositories.SupportRepository;


@Service("jpaSupport")
@Transactional
@Repository

public class SupportImpl implements SupportDao {
    final
    SupportRepository repository;

    @Autowired
    public SupportImpl(SupportRepository repository) {
        this.repository = repository;
    }

    @Override
    public EntitySupport save(EntitySupport entitySupport) {

        return repository.save(entitySupport);

    }
}

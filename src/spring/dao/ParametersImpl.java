package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.EntityParameters;
import spring.interfaces.ParametersDao;
import spring.repositories.ParametersRepository;

@Service("jpaParameters")
@Transactional
@Repository
public class ParametersImpl implements ParametersDao {


    final
    ParametersRepository repository;

    @Autowired
    public ParametersImpl(ParametersRepository repository) {
        this.repository = repository;
    }

    @Override
    public EntityParameters save(EntityParameters entityParameters) {
        return repository.save(entityParameters);
    }

    @Override
    public EntityParameters selectById(long id) {

        return repository.findById(id);

    }

    @Override
    public EntityParameters findTypeAndValue(String type, String value) {

        return repository.findByTypeAndValue(type, value);

    }
}

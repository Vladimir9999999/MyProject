package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityParameters;

public interface ParametersRepository extends CrudRepository<EntityParameters, Long> {
    EntityParameters findById(long id);
    EntityParameters findByTypeAndValue(String type, String value);

}

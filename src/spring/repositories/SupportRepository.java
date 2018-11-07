package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntitySupport;

public interface SupportRepository  extends CrudRepository<EntitySupport, Long> {

}

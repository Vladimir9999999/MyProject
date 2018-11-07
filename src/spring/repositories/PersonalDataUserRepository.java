package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityPersonalDataUser;

public interface PersonalDataUserRepository extends CrudRepository<EntityPersonalDataUser,Long> {

    EntityPersonalDataUser findById(long id);
    EntityPersonalDataUser findByUserId(long userId);
}

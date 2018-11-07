package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityAccountUsers;

public interface AccountUserRepository extends CrudRepository<EntityAccountUsers, Long> {
    EntityAccountUsers findById(long id);
    EntityAccountUsers findByLogin(String login);
}

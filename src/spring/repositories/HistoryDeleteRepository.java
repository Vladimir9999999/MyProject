package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityHistoryDelete;

public interface HistoryDeleteRepository extends CrudRepository<EntityHistoryDelete,Long> {
    EntityHistoryDelete findById(long id);
}
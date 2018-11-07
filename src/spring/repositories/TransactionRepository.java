package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityTransactions;

public interface TransactionRepository extends CrudRepository<EntityTransactions, Long> {
    EntityTransactions findById(long id);
}

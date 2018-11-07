package spring.interfaces;

import spring.entity.EntityTransactions;

import java.util.List;

public interface TransactionDao {

    EntityTransactions save(EntityTransactions transaction);
    EntityTransactions findById(long transactionId);
    List<EntityTransactions> findAll();
    void deleteAll();
}

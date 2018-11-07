package spring.interfaces;

import spring.entity.EntityHistoryDelete;

public interface HistoryDeleteDao {
    EntityHistoryDelete findById(long id);
    EntityHistoryDelete save(EntityHistoryDelete historyDelete);
}
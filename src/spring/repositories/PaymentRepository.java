package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityPayment;
import java.util.List;

public interface PaymentRepository extends CrudRepository<EntityPayment, Long> {

    EntityPayment findById(long id);
    List<EntityPayment> findByUserId(long userId);
    EntityPayment findByUserIdAndYearAndMonthAndDay(long userId,int year,int month,int day);
    List<EntityPayment> findByShopId(long shopId);
    List<EntityPayment> findAllByYearAndMonth(Integer year,Integer month);
    void deleteAllByUserId(long userId);
    boolean existsById(long id);
    void deleteById(long id);
}

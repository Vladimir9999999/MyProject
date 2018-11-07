package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityPartners;
import java.util.List;

public interface PartnersRepository extends CrudRepository<EntityPartners, Long> {
    List<EntityPartners> findByShop1OrShop2(long shop1, long shop2);
    int countByShop1OrShop2(long shop1, long shop2);
    EntityPartners findByShop1AndShop2(long shop1, long shop2);
}

package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityNews;
import java.util.List;

public interface NewsRepository extends CrudRepository<EntityNews,Long> {

    EntityNews findById(long id);
    List<EntityNews> findByShopId(long shopId);
    EntityNews findTopByShopId(long shopId);
    // метод findTopByShopIdOrderByIdDesc переберет коллекцию с конца
    // вернет последнию новость по shopId;
    // метод findTopByShopIdOrderByIdAsc переберет коллекцию с начала

}

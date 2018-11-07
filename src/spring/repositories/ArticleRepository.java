package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityArticle;
import spring.entity.EntityPrice;

import java.util.List;

public interface ArticleRepository extends CrudRepository<EntityArticle, Long> {
    EntityArticle findById(long id);
    void deleteAllByEntityPrice(EntityPrice price);
    boolean existsById(long id);
    void deleteById(long id);
    List<EntityArticle> findAllById(long id);
    List<EntityArticle> findByIdIsNotNull();
}

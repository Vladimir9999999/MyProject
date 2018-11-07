package spring.interfaces;

import spring.entity.EntityArticle;
import spring.entity.EntityPrice;
import java.util.List;

public interface ArticleDao {
    EntityArticle selectById(long id);
    EntityArticle save(EntityArticle entityArticle);
    void saveAll(List<EntityArticle> articleList);
    void deleteAllByEntityPrice(EntityPrice price);
    boolean existsById(long id);
    void deleteById(long id);
    List<EntityArticle> findAllById(long id);
    List<EntityArticle> findByIdIsNotNull();
}

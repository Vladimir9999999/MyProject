package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityArticle;
import spring.entity.EntityPrice;
import spring.interfaces.ArticleDao;
import spring.repositories.ArticleRepository;
import java.util.List;

@Service("jpaArticle")
@Transactional
@Repository
public class ArticleImpl implements ArticleDao {

    private final ArticleRepository repository;

    @Autowired
    public ArticleImpl(ArticleRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityArticle selectById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityArticle save(EntityArticle entityArticle) {
        return repository.save(entityArticle);
    }

    @Override
    public void saveAll(List<EntityArticle> articleList) {
        repository.saveAll(articleList);
    }

    @Override
    public void deleteAllByEntityPrice(EntityPrice price) {
        repository.deleteAllByEntityPrice(price);
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EntityArticle> findAllById(long id) {
        return repository.findAllById(id);
    }

    @Override
    public List<EntityArticle> findByIdIsNotNull() {
        return repository.findByIdIsNotNull();
    }
}

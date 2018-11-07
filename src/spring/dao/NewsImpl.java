package spring.dao;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityNews;
import spring.entity.EntityShopsCluster;
import spring.interfaces.NewsDao;
import spring.repositories.NewsRepository;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("jpaNews")
@Transactional
@Repository

public class NewsImpl implements NewsDao {
    //class реализует интерфейс NewsDao

    @Autowired
    private final NewsRepository repository;

    public NewsImpl(NewsRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityNews findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<EntityNews> findByShopId(long shopId) {
        return repository.findByShopId(shopId);
    }

    @Override
    public EntityNews save(EntityNews news) {
        return repository.save(news);
    }

    @Override
    public EntityNews findLastByShopId(long shopId) {
        return repository.findTopByShopId(shopId);
    }

    public List<EntityNews> findShopArray(WebApplicationContext ctx, EntityShopsCluster entityShopsCluster){

        JSONArray myCluster = new JSONArray(entityShopsCluster.getShops());

        List<EntityNews> newsArrayList = new ArrayList<>();

        for (int i=0;i<myCluster.length();i++){

            newsArrayList.addAll(repository.findByShopId(myCluster.getLong(i)));

        }
        return newsArrayList;
    }
}

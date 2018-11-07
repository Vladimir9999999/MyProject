package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import spring.entity.EntityPhoto;
import spring.interfaces.PhotoDao;
import spring.repositories.PhotoRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service("jpaPhoto")
@Transactional
@Repository

public class PhotoImpl implements PhotoDao{

    private  final PhotoRepository repository;

    @Autowired
    public PhotoImpl(PhotoRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityPhoto findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<EntityPhoto> findByShopId(long shopId){
        return repository.findByShopId(shopId);
    }

    @Override
    public EntityPhoto save(EntityPhoto photo) {
       // return repository.save(photo);
        return repository.save(photo);
    }

    @Override
    public EntityPhoto findLastByShopId(long shopId) {
        return repository.findTopByShopIdOrderByIdDesc(shopId);
    }

}


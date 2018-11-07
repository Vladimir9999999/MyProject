package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityDeleteMarker;
import spring.interfaces.DeleteMarkerDao;
import spring.repositories.DeleteMarkerRepository;
import java.sql.Timestamp;

@Service("jpaDeleteMarker")
@Repository
@Transactional

public class DeleteMarkerImpl implements DeleteMarkerDao {

    private final DeleteMarkerRepository repository;

    @Autowired
    public DeleteMarkerImpl(DeleteMarkerRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntityDeleteMarker selectById(long id) {
        return repository.findById(id);
    }

    @Override
    public EntityDeleteMarker save(EntityDeleteMarker deleteMarker) {
        return repository.save(deleteMarker);
    }

    @Override
    public EntityDeleteMarker selectByDate(Timestamp date) {
        return repository.findByDate(date);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}

package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntitySession;
import spring.interfaces.SessionDao;
import spring.repositories.SessionRepository;

import java.util.List;

@Service("jpaSession")
@Transactional
@Repository

public class SessionImpl implements SessionDao {

    private final SessionRepository repository;

    @Autowired
    public SessionImpl(SessionRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public EntitySession findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<EntitySession> findAll() {

        return Lists.newArrayList(repository.findAll());

    }

    @Override
    public EntitySession findByUserIdAndTocken(long userId, String tocken) {

        return repository.findByUserIdAndTocken(userId, tocken);

    }

    @Override
    public EntitySession save(EntitySession session) {
        return repository.save(session);
    }

    @Override
    public void deleteByAppleTocken(String iTocken) {

        repository.deleteByAppleTocken(iTocken);
    }

    @Override
    public void deleteByUserIdAndTocken(long userId, String tocken) {

        repository.deleteByUserIdAndTocken(userId, tocken);

    }

    @Override
    public void deleteByGoogleTocken(String gTocken) {
        System.out.println(gTocken);
        repository.deleteByGoogleTocken(gTocken);
    }

    @Override
    public void deleteAll(List<EntitySession> sessions) {
        repository.deleteAll(sessions);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<EntitySession> findByUserId(long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<EntitySession> findByGoogleTockenIsNotNull() {
        return repository.findByGoogleTockenIsNotNull();
    }

    @Override
    public List<EntitySession> findByAppleTockenIsNotNull() {
        return repository.findByAppleTockenIsNotNull();
    }
}

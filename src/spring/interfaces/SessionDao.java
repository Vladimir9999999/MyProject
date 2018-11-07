package spring.interfaces;

import spring.entity.EntitySession;
import java.util.List;

public interface SessionDao {

    EntitySession findById(long id);
    List<EntitySession> findAll();
    EntitySession findByUserIdAndTocken(long userId, String tocken);
    EntitySession save(EntitySession session);


    void deleteByAppleTocken(String iTocken);
    void deleteByUserIdAndTocken(long userId, String tocken);
    void deleteByGoogleTocken(String gTocken);
    void deleteAll(List<EntitySession> sessions);
    void deleteById(long id);

    List<EntitySession> findByUserId(long userId);

    List<EntitySession> findByGoogleTockenIsNotNull();
    List<EntitySession> findByAppleTockenIsNotNull();
}

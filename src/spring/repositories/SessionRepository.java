package spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import spring.entity.EntitySession;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SessionRepository extends CrudRepository<EntitySession,Long> {

    EntitySession findById(long id);
    EntitySession findByUserIdAndTocken(long userId, String tocken);

    void deleteByAppleTocken(String iTocken);

    void deleteByUserIdAndTocken(long userId, String tocken);
    void deleteByGoogleTocken(String gTocken);

    @Override
    @NotNull
    @Query(value = "SELECT * FROM session ORDER BY last_visit DESC", nativeQuery = true)
    List<EntitySession> findAll();

    List<EntitySession> findByUserId(long userId);

    List<EntitySession> findByGoogleTockenIsNotNull();
    List<EntitySession> findByAppleTockenIsNotNull();
}

package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.EntityDeleteMarker;
import java.sql.Timestamp;

public interface DeleteMarkerRepository extends JpaRepository<EntityDeleteMarker,Long> {
    EntityDeleteMarker findById(long id);
    EntityDeleteMarker findByDate(Timestamp date);
    void deleteById (long id);
}

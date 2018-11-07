package spring.interfaces;

import spring.entity.EntityDeleteMarker;
import java.sql.Timestamp;

public interface DeleteMarkerDao {
    EntityDeleteMarker selectById(long id);
    EntityDeleteMarker save(EntityDeleteMarker deleteMarker);
    EntityDeleteMarker selectByDate(Timestamp date);
    void deleteById (long id);
}

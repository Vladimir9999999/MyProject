package spring.interfaces.delete;

import spring.entity.EntityDeleteMarker;

public interface Removable {

    EntityDeleteMarker getDeleteMarker();

    void setDeleteMarker(EntityDeleteMarker deleteMarker);

    long getShopId();

}

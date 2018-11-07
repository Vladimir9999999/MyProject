package spring.interfaces;

import spring.entity.EntityDefaultCategory;
import java.util.List;

public interface DefaultCategoryDAO {

    EntityDefaultCategory save(EntityDefaultCategory defaultCategory);
    List<EntityDefaultCategory> selectByParent(long parentId);
    List<EntityDefaultCategory> selectAll();

}

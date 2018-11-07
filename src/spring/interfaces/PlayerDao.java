package spring.interfaces;

import spring.entity.EntityPlayer;
import java.util.List;

public interface PlayerDao {

    EntityPlayer save(EntityPlayer player);
    EntityPlayer findById(long id);
    List<EntityPlayer> findByShopId(long shopId);
    boolean existsById(long userId);
    void deleteById(long id);
}

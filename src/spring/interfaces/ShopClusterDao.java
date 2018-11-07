package spring.interfaces;

import spring.entity.EntityShopsCluster;

public interface ShopClusterDao {

    EntityShopsCluster selectByShopCluster(String cluster);
    EntityShopsCluster save(EntityShopsCluster cluster);

}

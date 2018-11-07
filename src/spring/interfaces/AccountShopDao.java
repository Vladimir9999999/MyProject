package spring.interfaces;

import spring.entity.EntityAccountShop;

public interface AccountShopDao {

    EntityAccountShop findById(long id);
    void save(EntityAccountShop accountShop);
}

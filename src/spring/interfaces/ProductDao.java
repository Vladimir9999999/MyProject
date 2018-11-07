package spring.interfaces;

import spring.entity.EntityProduct;

public interface ProductDao {

    EntityProduct save(EntityProduct product);
    EntityProduct selectByName(String name);
    EntityProduct findById(long shopId);

}

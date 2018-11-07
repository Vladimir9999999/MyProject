package spring.interfaces;

import spring.entity.EntityParameters;

public interface ParametersDao {

    EntityParameters save(EntityParameters entityParameters);
    EntityParameters selectById(long id);
    EntityParameters findTypeAndValue(String type, String value);

}

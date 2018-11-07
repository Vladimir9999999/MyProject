package spring.interfaces;

import spring.entity.EntityPersonalDataUser;

public interface PersonalDataUserDao {

    EntityPersonalDataUser findById(long id);
    EntityPersonalDataUser save(EntityPersonalDataUser personalDataUser);
    EntityPersonalDataUser findByUserId(long id);
}

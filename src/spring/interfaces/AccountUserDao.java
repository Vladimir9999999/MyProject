package spring.interfaces;

import spring.entity.EntityAccountUsers;

import java.util.List;

public interface AccountUserDao {

    EntityAccountUsers save(EntityAccountUsers accountUsers);
    EntityAccountUsers selectById(long id);
    EntityAccountUsers selectByMobile(long mobile);


}

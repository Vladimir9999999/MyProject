package spring.interfaces;

import spring.entity.EntityPersonalData;

public interface PersonalDao  {
    boolean save(EntityPersonalData personalData);
    EntityPersonalData selectByEmployeeId(long employeeId);
}

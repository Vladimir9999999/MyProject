package spring.interfaces;

import spring.entity.EntityTurn;
import java.util.List;

public interface TurnDao {

    boolean save(EntityTurn turn);
    EntityTurn selectById(int id);
    List<EntityTurn> selectAll();
    EntityTurn selectByTurn(EntityTurn turn);
}

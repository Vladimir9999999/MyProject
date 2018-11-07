package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.EntityTurn;

public interface TurnRepository extends CrudRepository<EntityTurn, Long> {


    EntityTurn findEntityTurnByBeginTimeAndEndTimeAndBeginLunchAndEndLunch(int beginTime, int endTime, int beginLunch, int endLunch);
    EntityTurn findById(int id);

}

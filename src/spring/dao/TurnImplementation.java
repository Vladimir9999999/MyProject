package spring.dao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import spring.entity.EntityTurn;
import spring.interfaces.TurnDao;
import spring.repositories.TurnRepository;
import java.util.List;


@Service("jpaTurn")
@Repository
@Transactional

public class TurnImplementation implements TurnDao {

   private final TurnRepository repository;

   @Autowired
    public TurnImplementation(TurnRepository repository) {
        Assert.notNull(repository, "repository - null");
        this.repository = repository;
    }

    @Override
    public boolean save(EntityTurn turn) {

        repository.save(turn);

        return false;
    }

    @Override
    public EntityTurn selectById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<EntityTurn> selectAll() {

        return Lists.newArrayList(repository.findAll());

    }

    @Override
    public EntityTurn selectByTurn(EntityTurn turn) {
        return repository.findEntityTurnByBeginTimeAndEndTimeAndBeginLunchAndEndLunch(turn.getBeginTime(),turn.getEndTime(),turn.getBeginLunch(),turn.getEndLunch());
    }
}

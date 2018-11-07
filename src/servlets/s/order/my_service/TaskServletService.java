package servlets.s.order.my_service;

import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityTaskList;
import spring.interfaces.TaskListDao;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TaskServletService {

    private TaskListDao taskListDao;

    public TaskServletService(WebApplicationContext ctx) {

        taskListDao = ctx.getBean("jpaTaskList", TaskListDao.class);
    }

    public List<EntityTaskList> getNewListTask() {

        Date date = new Date();

        Timestamp currentTimeMinus = new Timestamp(date.getTime() - 100);

        List<EntityTaskList> newTaskList = taskListDao.findByTimeAfter(currentTimeMinus);

        if (newTaskList == null || newTaskList.size() == 0) {

            return null;
        }

        return newTaskList;
    }

    public List<EntityTaskList> getLastListTask(int offset, int limit, int status) {

        Timestamp currentTime = (new Timestamp(new Date().getTime()));

        return taskListDao.findOld(currentTime,offset,limit,status);

    }
}

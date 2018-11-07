package utils;

import dao.TaskListDao;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityTaskList;

import java.util.List;

public class NotificationOrder  implements Runnable{

    TaskListDao taskListDao;
    private WebApplicationContext ctx;

    public static boolean flag = false;
    public static boolean runn = true;

    public NotificationOrder(WebApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        if(runn) {
            runn = false;
            while (flag) {
                try {

                    taskListDao  = new TaskListDao();

                    List<EntityTaskList> taskLists = taskListDao.findNewTask();

                    Thread.sleep(1000);

                    System.out.println(taskLists.size());

                    if (taskLists.size() == 0){
                        //flag = false;
                        runn = true;
                    }

                } catch (InterruptedException e) {
                    flag = false;
                    runn = true;
                    e.printStackTrace();
                }
            }
        }
    }

}

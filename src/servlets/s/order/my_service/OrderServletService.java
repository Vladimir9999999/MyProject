package servlets.s.order.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import spring.entity.EntityAccountUsers;
import spring.entity.EntityOrders;
import spring.entity.EntityTaskList;
import java.util.List;

public class OrderServletService {

    public JSONArray getNewOrdersArrayJ(List<EntityTaskList> newTaskList) {

        JSONArray newOrderArrayJ = new JSONArray();

        for (EntityTaskList taskList : newTaskList) {

            EntityOrders orders = taskList.getOrders();

            JSONObject ordersJ = getOrderJ(orders, taskList);

            newOrderArrayJ.put(ordersJ);

        }

        return newOrderArrayJ;
    }

    public JSONArray getLastOrderArrayJ(List<EntityTaskList> lastTaskList) {

        JSONArray lastOrderArrayJ = new JSONArray();

        for (EntityTaskList taskList : lastTaskList) {

            EntityOrders orders = taskList.getOrders();

            JSONObject ordersJ = getOrderJ(orders,taskList);

            lastOrderArrayJ.put(ordersJ);

        }

        return lastOrderArrayJ;
    }

    private JSONObject getOrderJ(EntityOrders orders,EntityTaskList taskList) {

        orders.setTaskListsById(null);
        JSONObject ordersJ = new JSONObject(orders);


        EntityAccountUsers accountUsers = orders.getAccountUsers();

        if (accountUsers != null && accountUsers.getId() != 0) {

            JSONObject accountUsersJ = new JSONObject(accountUsers);

            ordersJ.put("client", accountUsersJ);

        }

        ordersJ.remove("accountUsers");

        JSONArray serviceArrayJ = new JSONArray(taskList.getServices());

        ordersJ.put("services", serviceArrayJ);

        ordersJ.put("planned_time", taskList.getTime().getTime());

        ordersJ.put("last_modification", orders.getLastModification().getTime());

        ordersJ.remove("lastModification");

        ordersJ.remove("taskListsById");

        ordersJ.remove("deleteMark");

        ordersJ.remove("shopId");

        return ordersJ;
    }
}

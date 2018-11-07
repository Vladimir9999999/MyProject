package servlets.s.employee;

import interfaces.ServletService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.CategoryShopDao;
import spring.interfaces.EmployeeDao;
import spring.interfaces.ScheduleDao;
import utils.EmployeeManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SaveEmployee", urlPatterns = "/save.employee")
public class SaveEmployee extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long editEmployee = 0;
        long shopId;


        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ServletService servletService = new ShopServletService(ctx);

        servletService.setRequestJ(new JSONObject(request.getParameter("request")));
        shopId = servletService.getRequestJ().getLong("shop_id");

        editEmployee = servletService.getRequestJ().getLong("edit_employee");
        EmployeeDao employeeDao = ctx.getBean("jpaEmployee",EmployeeDao.class);


        EntityEmployee entityEmployee;

        if(editEmployee>0){

            entityEmployee = employeeDao.selectById(editEmployee);

        }else {
            entityEmployee = new EntityEmployee();
        }

        JSONArray service = null;

        EmployeeManager employeeManager = new EmployeeManager(ctx,null);
        if(servletService.getRequestJ().has("services")){
            try{

                service = servletService.getRequestJ().getJSONArray("services");

            }catch (JSONException e){

                if(servletService.getRequestJ().getString("services").equals("all")){
                    employeeManager.addAllPrice(entityEmployee.getId());
                }
            }
        }
        ScheduleDao scheduleDao = ctx.getBean("jpaShedule",ScheduleDao.class);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        doPost(request, response);


    }
}

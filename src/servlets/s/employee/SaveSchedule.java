package servlets.s.employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.*;
import utils.EmployeeManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@WebServlet(name = "servlets.save.schedule",
        urlPatterns = "/save.schedule")
public class SaveSchedule extends HttpServlet {

    private static String c = "sv_schdl";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(getServletContext());




        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;
        Timestamp date;
        JSONObject turnJ;

        if(sSS.initializeShop(request,response)){

            sSS.getStatus().put("case",c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            /*date = new Timestamp(sSS.getRequestJ().getLong("date"));*/
            turnJ = sSS.getRequestJ().getJSONObject("turn");

        }else {
            return;
        }

        EmployeeDao employeeDao = ctx.getBean("jpaEmployee",EmployeeDao.class);
        EntityEmployee employee = employeeDao.selectById(ShopServletService.DEFAULT_EMPLOYEE);

        EntitySchedule schedule = employee.getScheduleByScheduleId();
        /*DeviationsDao deviationsDao= ctx.getBean("jpaDeviations", DeviationsDao.class);

        EntityDeviations entityDeviations = deviationsDao.getByDataAndScheduleId(date,schedule.getId());

        if(entityDeviations == null){
            entityDeviations = new EntityDeviations();
        }

        entityDeviations.setData(date);
        entityDeviations.setType(3);
        entityDeviations.setScheduleId(schedule.getId());
*/
        TurnDao turnDao = ctx.getBean("jpaTurn",TurnDao.class);

        List<EntityTurn> turnList = turnDao.selectAll();
        JSONObject schJ= new JSONObject();


            EntityTurn turn = new EntityTurn(turnJ);

            if(turn.getBeginLunch()!= null){

                if(!turnList.contains(turn)){

                    turnDao.save(turn);
                    schJ.put("turn_id",turn.getId());

                }else {

                    schJ.put("turn_id",turnList.get(turnList.indexOf(turn)).getId());

                }

            }else{

                schJ.put("turn_id",turn.getId());

            }

            schedule.setValue(new JSONArray().put(schJ).toString());

            ScheduleDao scheduleDao= ctx.getBean("jpaSchedule",ScheduleDao.class);
            scheduleDao.save(schedule);
/*        entityDeviations.setTurns(schJ.toString());

        deviationsDao.save(entityDeviations);*/
        sSS.getStatus().put("error","ok");
        sSS.finalizeShop(response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }

}
//request={"tocken":"","shopId":1010,"date":50400000,"turn":{"sH":1,"sM":2,"fH":1,"fH":3,"sLH":3,"sLM":2,"fLH":1,"fLM":1}}
package servlets.s.employee;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SelectSchedule",
        urlPatterns = "/select.schedule")

public class SelectSchedule extends HttpServlet {
    private String c = "selct_schdl";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);
        String tocken;
        long shopId;
        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
        } else {
            return;
        }
        EmployeeDao employeeDao = ctx.getBean("jpaEmployee",EmployeeDao.class);
        EntityEmployee employee = employeeDao.selectById(ShopServletService.DEFAULT_EMPLOYEE);
        DeviationsDao deviationsDao = ctx.getBean("jpaDeviations",DeviationsDao.class);
        TurnDao turnDao = ctx.getBean("jpaTurn",TurnDao.class);

        JSONArray jsonArray = new JSONArray(employee.getScheduleByScheduleId().getValue());
        JSONObject turn = jsonArray.getJSONObject(0);
        EntityTurn entityTurn = turnDao.selectById(turn.getInt("turn_id"));

        assert entityTurn != null;
        sSS.getBody().put("turn",entityTurn.toJsonObject());
        sSS.getStatus().put("error","ok");
        sSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        doPost(request, response);

    }
}
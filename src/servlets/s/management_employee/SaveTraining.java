package servlets.s.management_employee;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.management_employee.my_service.TrainingServletService;
import spring.interfaces.EmployeeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveTraining"
           ,urlPatterns = "/save.training")
public class SaveTraining extends HttpServlet {

    private String c = "sav_training";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;
        long employeeId;

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            employeeId = sSS.getRequestJ().getLong("employee_id");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        EmployeeDao employeeDao = ctx.getBean("jpaEmployee", EmployeeDao.class);

        if (!employeeDao.existByShopIdAndId(shopId, employeeId)) {
            sSS.getStatus().put("error", "Сотрудника несуществует");
            sSS.finalizeShop(response);
            return;
        }

        TrainingServletService tSS = new TrainingServletService(shopId, employeeId, ctx);

        JSONObject trainingJ = tSS.save(sSS.getRequestJ());

        if (trainingJ.has("error")) {

            sSS.getStatus().put("error", trainingJ.getString("error"));
            sSS.finalizeShop(response);
            return;
        }

        sSS.getBody().put("id", trainingJ.getLong("id"));

        sSS.getStatus().put("error", "ok");

        sSS.finalizeShop(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}


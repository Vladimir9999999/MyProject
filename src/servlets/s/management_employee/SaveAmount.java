package servlets.s.management_employee;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.management_employee.my_service.AmountServletService;
import spring.entity.EntityAmount;
import spring.interfaces.EmployeeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveAmount"
        ,urlPatterns = "/save.amount")
public class SaveAmount extends HttpServlet {

    private String c = "sav_amount";

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

        if (sSS.getRequestJ().has("penalty") || sSS.getRequestJ().has("prize")) {

            AmountServletService aSS;

            EntityAmount penalty;
            if (sSS.getRequestJ().has("penalty")) {
                JSONObject penaltyJ = sSS.getRequestJ().getJSONObject("penalty");

                aSS = new AmountServletService(shopId, employeeId, ctx);
                penalty = aSS.save(penaltyJ, EntityAmount.PENALTY);

                sSS.getBody().put("id", penalty.getId());
                sSS.getStatus().put("error", "ok");
            }

            EntityAmount prize;
            if (sSS.getRequestJ().has("prize")) {
                JSONObject prizeJ = sSS.getRequestJ().getJSONObject("prize");

                aSS = new AmountServletService(shopId, employeeId, ctx);
                prize = aSS.save(prizeJ, EntityAmount.PRIZE);

                sSS.getBody().put("id", prize.getId());
                sSS.getStatus().put("error", "ok");
            }



            sSS.finalizeShop(response);

        }else {

            JSONObject status = new JSONObject();
            status.put("error", "Не найден тип Amount")
                    .put("case", c);

            response.getWriter().print(new JSONObject().put("status", status));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}


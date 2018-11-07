package servlets.s.management_employee;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.management_employee.my_service.AppraisalServletService;
import spring.entity.EntityAppraisal;
import spring.interfaces.EmployeeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveAppraisal"
           ,urlPatterns = "/save.appraisal")
public class SaveAppraisal extends HttpServlet {

    private String c = "sav_appraisal";

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

        if (sSS.getRequestJ().has("promotion") || sSS.getRequestJ().has("rebuke")) {

            AppraisalServletService aSS;

            EntityAppraisal promotion;
            if (sSS.getRequestJ().has("promotion")) {
                JSONObject promotionJ = sSS.getRequestJ().getJSONObject("promotion");

                aSS = new AppraisalServletService(shopId, employeeId, ctx);
                promotion = aSS.save(promotionJ, EntityAppraisal.PROMOTION);

                sSS.getBody().put("id", promotion.getId());
            }

            EntityAppraisal rebuke;
            if (sSS.getRequestJ().has("rebuke")) {
                JSONObject rebukeJ = sSS.getRequestJ().getJSONObject("rebuke");

                aSS = new AppraisalServletService(shopId, employeeId, ctx);
                rebuke = aSS.save(rebukeJ, EntityAppraisal.REBUKE);

                sSS.getBody().put("id", rebuke.getId());
            }

            sSS.getStatus().put("error", "ok");

            sSS.finalizeShop(response);
        }else {

            JSONObject status = new JSONObject();
            status.put("error", "Не найден тип appraisal")
                    .put("case", c);

            response.getWriter().print(new JSONObject().put("status", status));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}


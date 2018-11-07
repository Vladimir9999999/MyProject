package servlets.s.management_employee;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.management_employee.my_service.LackOfServletService;
import spring.entity.EntityLackOf;
import spring.interfaces.EmployeeDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveLackOf"
        ,urlPatterns = "/save.lack.of")
public class SaveLackOf extends HttpServlet {

    private String c = "sav_lack_of";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;
        long employeeId;

        if(sSS.initializeShop(request,response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            employeeId = sSS.getRequestJ().getLong("employee_id");
            sSS.getStatus().put("case", c);
        }else{
            return;
        }

        EmployeeDao employeeDao  = ctx.getBean("jpaEmployee",EmployeeDao.class);

        if(!employeeDao.existByShopIdAndId(shopId,employeeId)){
            sSS.getStatus().put("error","Сотрудника несуществует");
            sSS.finalizeShop(response);
            return;
        }

        if (sSS.getRequestJ().has("hospital") || sSS.getRequestJ().has("vacation") ||
                sSS.getRequestJ().has("time_of") || sSS.getRequestJ().has("pause")) {

            LackOfServletService lSS;

            JSONObject resultSaveLackOfJ;

            if (sSS.getRequestJ().has("hospital")) {

                JSONObject timeOfJ = sSS.getRequestJ().getJSONObject("hospital");

                lSS = new LackOfServletService(shopId, employeeId, ctx);
                resultSaveLackOfJ = lSS.save(timeOfJ, EntityLackOf.HOSPITAL);

                if (resultSaveLackOfJ.has("error")) {

                    sSS.getStatus().put("error", resultSaveLackOfJ.getString("error"));
                    sSS.finalizeShop(response);
                    return;
                }

                sSS.getBody().put("id", resultSaveLackOfJ.getLong("id"));
            }

            if (sSS.getRequestJ().has("vacation")) {

                JSONObject vacationJ = sSS.getRequestJ().getJSONObject("vacation");

                lSS = new LackOfServletService(shopId, employeeId, ctx);
                resultSaveLackOfJ = lSS.save(vacationJ, EntityLackOf.VACATION);

                if (resultSaveLackOfJ.has("error")) {

                    sSS.getStatus().put("error", resultSaveLackOfJ.getString("error"));
                    sSS.finalizeShop(response);
                    return;
                }

                sSS.getBody().put("id", resultSaveLackOfJ.getLong("id"));
            }

            if (sSS.getRequestJ().has("time_of")) {

                JSONObject timeOfJ = sSS.getRequestJ().getJSONObject("time_of");

                lSS = new LackOfServletService(shopId, employeeId, ctx);

                resultSaveLackOfJ = lSS.save(timeOfJ, EntityLackOf.TIME_OF);

                if (resultSaveLackOfJ.has("error")) {

                    sSS.getStatus().put("error", resultSaveLackOfJ.getString("error"));
                    sSS.finalizeShop(response);
                    return;
                }

                sSS.getBody().put("id", resultSaveLackOfJ.getLong("id"));
            }

            if (sSS.getRequestJ().has("pause")) {

                JSONObject pauseJ = sSS.getRequestJ().getJSONObject("pause");

                lSS = new LackOfServletService(shopId, employeeId, ctx);
                resultSaveLackOfJ = lSS.save(pauseJ, EntityLackOf.PAUSE);

                if (resultSaveLackOfJ.has("error")) {

                    sSS.getStatus().put("error",resultSaveLackOfJ.getString("error"));
                    sSS.finalizeShop(response);
                    return;
                }

                sSS.getBody().put("id", resultSaveLackOfJ.getLong("id"));
            }

            sSS.getStatus().put("error", "ok");

            sSS.finalizeShop(response);
        }else {

            JSONObject status = new JSONObject();
            status.put("error", "Не найден тип lack_of")
                    .put("case", c);

            response.getWriter().print(new JSONObject().put("status", status));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

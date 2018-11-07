package servlets.s.management_employee;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.management_employee.my_service.AmountServletService;
import servlets.s.management_employee.my_service.AppraisalServletService;
import servlets.s.management_employee.my_service.LackOfServletService;
import servlets.s.management_employee.my_service.TrainingServletService;
import spring.entity.EntityAmount;
import spring.entity.EntityAppraisal;
import spring.entity.EntityLackOf;
import spring.entity.EntityTraining;
import spring.interfaces.AmountDao;
import spring.interfaces.AppraisalDao;
import spring.interfaces.LackOfDao;
import spring.interfaces.TrainingDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectManagement"
           ,urlPatterns = "/select.manage.employee")

public class SelectManagement extends HttpServlet {

    private String c = "slct_mngmnt_empl";

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

        LackOfDao lackOfDao = ctx.getBean("jpaLackOf", LackOfDao.class);

        List<EntityLackOf> listLackOf = lackOfDao.findByEmployeeId(employeeId);

        if (listLackOf.size() != 0) {

            LackOfServletService lSS = new LackOfServletService(shopId,employeeId,ctx);
            JSONArray hospitalArrayJ = lSS.getHospitalArrayJ(listLackOf);

            if (hospitalArrayJ.length() != 0) {

                sSS.getBody().put("hospital",hospitalArrayJ);
            }

            JSONArray vacationArrayJ = lSS.getVacationArrayJ(listLackOf);

            if(vacationArrayJ.length() != 0) {

                sSS.getBody().put("vacation",vacationArrayJ);
            }

            JSONArray timeOfArrayJ = lSS.getTimeOfArrayJ(listLackOf);

            if (timeOfArrayJ.length() != 0) {

                sSS.getBody().put("time_of",timeOfArrayJ);
            }

            JSONArray pauseArrayJ = lSS.getPauseArrayJ(listLackOf);

            if (pauseArrayJ.length() != 0) {

                sSS.getBody().put("pause",pauseArrayJ);
            }
        }

        AmountDao amountDao = ctx.getBean("jpaAmount", AmountDao.class);

        List<EntityAmount> listAmount = amountDao.findByEmployeeId(employeeId);

        if (listAmount.size() != 0) {

            AmountServletService aSS = new AmountServletService(shopId,employeeId,ctx);

            JSONArray prizeArrayJ = aSS.getAmountArrayJ(listAmount,EntityAmount.PRIZE);

            if (prizeArrayJ.length() != 0) {

                sSS.getBody().put("prize", prizeArrayJ);
            }

            JSONArray penaltyArrayJ = aSS.getAmountArrayJ(listAmount,EntityAmount.PENALTY);

            if (penaltyArrayJ.length() != 0) {

                sSS.getBody().put("penalty", penaltyArrayJ);
            }
        }

        AppraisalDao appraisalDao = ctx.getBean("jpaAppraisal",AppraisalDao.class);

        List<EntityAppraisal> listAppraisal = appraisalDao.findByEmployeeId(employeeId);

        if(listAppraisal.size() != 0) {

            AppraisalServletService aSS = new AppraisalServletService(shopId,employeeId,ctx);

            JSONArray promotionArrayJ = aSS.getAppraisalArrayJ(listAppraisal,EntityAppraisal.PROMOTION);

            if (promotionArrayJ.length() != 0) {

                sSS.getBody().put("promotion",promotionArrayJ);
            }

            JSONArray rebukeArrayJ = aSS.getAppraisalArrayJ(listAppraisal,EntityAppraisal.REBUKE);

            if (rebukeArrayJ.length() != 0) {

                sSS.getBody().put("rebuke",rebukeArrayJ);
            }
        }

        TrainingDao trainingDao = ctx.getBean("jpaTraining", TrainingDao.class);

        List<EntityTraining> listTraining = trainingDao.findByEmployeeId(employeeId);

        if (listTraining.size() != 0) {

            TrainingServletService tSS = new TrainingServletService(shopId, employeeId, ctx);

            JSONArray trainingArrayJ = tSS.getArrayTraining(listTraining);

            if (trainingArrayJ.length() != 0) {

                sSS.getBody().put("training", trainingArrayJ);
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalizeShop(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

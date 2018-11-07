package servlets.s.employee;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityEmployee;
import spring.interfaces.DeleteMarkerDao;
import spring.interfaces.EmployeeDao;
import utils.DeleteShopElement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteEmployee",
urlPatterns = "/delete.employee")

public class DeleteEmployee extends HttpServlet {

    private String c = "del_emp";
    long shopId;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject requestJ = new JSONObject(request.getParameter("request"));

        long shopId;
        String tocken;

        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if(sSS.initializeShop(request,response)){

            shopId = requestJ.getLong("shop_id");
            tocken = requestJ.getString("tocken");

        }else {

            return;

        }

        sSS.getStatus().put("case",c);
        long employeeId = requestJ.getLong("employee");


        DeleteMarkerDao deleteMarkerDao = ctx.getBean("jpaDeleteMarker", DeleteMarkerDao.class);
        EmployeeDao employeeDao = ctx.getBean("jpaEmployee", EmployeeDao.class);

        EntityEmployee employee = employeeDao.selectByShopIdAndId(shopId,employeeId);

        DeleteShopElement deleteShopElement = new DeleteShopElement(shopId,deleteMarkerDao);
        deleteShopElement.markToDelete(employee,employeeDao);

        //todo mark delete order and schedule

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }

}

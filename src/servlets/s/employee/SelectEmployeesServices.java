package servlets.s.employee;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import servlets.ShopServletService;
import utils.EmployeeManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelecServiceEmployees",
urlPatterns = "/select.employees.services")
public class SelectEmployeesServices extends HttpServlet {
    private String c = "slct_empl_srvc";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        String tocken;
        List<Object> services;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {
            sSS.getStatus().put("case", c);
            tocken = sSS.getRequestJ().getString("tocken");
            services = sSS.getRequestJ().getJSONArray("services").toList();
            shopId  = sSS.getRequestJ().getLong("shop_id");
        } else {

            return;

        }


        EmployeeManager employeeManager = new EmployeeManager(ctx,null);

        sSS.getBody().put("employees", employeeManager.getEmployeeInterval(shopId));

        sSS.getStatus().put("error","ok");

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

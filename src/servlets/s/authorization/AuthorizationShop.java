package servlets.s.authorization;

import Models.AccountEntity;
import Models.EmploeeEntity;
import Models.Session;
import Models.ShopEntity;
import dao.AccountDao;
import dao.EmployeeDao;
import dao.ShopDao;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "servlets.authorization.shop",
        urlPatterns = "/auth.shop")
public class AuthorizationShop extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HtmlLoader loader = new HtmlLoader();

        String ipNode = System.getProperty("ip.node");

        String uri = "http://" + ipNode + ":8101/shop/auth?request="+ URLEncoder.encode(request.getParameter("request"), StandardCharsets.UTF_8);;


        String res = loader.getHTML(uri);
        JSONObject resj = new JSONObject(res);
        if(resj.has("authentication")){
            resj.getJSONObject("authentication").put("employee_id",resj.getJSONObject("authentication").get("employeeId"));
        }
        System.out.println(resj.toString());
        response.getWriter().print(resj.toString());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }
}

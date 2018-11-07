package servlets.s.authorization;

import interfaces.ServletService;
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

@WebServlet(name = "servlets.new.shop",
        urlPatterns = "/new.shop")
public class NewShop extends HttpServlet {

    private String c = "registration";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ServletService servletService = new ShopServletService(ctx);

        response.setContentType("application/json");
        JSONObject requestJ = new JSONObject(request.getParameter("request"));
        ShopService shopService = new ShopService(ctx);
        servletService.setRequestJ(requestJ);
        JSONObject bodyJ = shopService.createDefaultShop(requestJ);

        servletService.getStatus().put("error","ok");

        servletService.setBody(bodyJ);
        servletService.getStatus().put("case",c);
        servletService.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }


}

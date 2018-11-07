package servlets.s.appearance;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.NewShopServletService;
import utils.MultipartUtility;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SaveIndexShop", urlPatterns = "/save.show.case")
public class SaveIndexShop extends HttpServlet {
    private String c = "sv_indx";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        NewShopServletService sSS = new NewShopServletService(ctx);
        sSS.setRequestJ(new JSONObject(request.getParameter("request")));

        if(!sSS.getRequestJ().has("employeeId")){
            sSS.getRequestJ().put("employeeId",sSS.getRequestJ().get("employee_id"));
        }

        String ipNode = System.getProperty("ip.node");

        String uri = "http://" + ipNode + ":8103/showCase/element";

        MultipartUtility multipartUtility = new MultipartUtility(uri, "UTF-8");
        multipartUtility.addFormField("request", sSS.getRequestJ().toString());
        List<String> res = multipartUtility.finish();
        System.out.println(res);

        response.getWriter().print(res.get(0));

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }

}
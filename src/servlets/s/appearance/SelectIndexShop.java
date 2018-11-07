package servlets.s.appearance;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.NewShopServletService;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.IndexPageShopDao;
import utils.HtmlLoader;
import utils.MultipartUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@WebServlet(name = "SelectIndexShop"
        ,urlPatterns = "/select.show.case.shop")

public class SelectIndexShop extends HttpServlet {

    private String c = "slct_index";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        NewShopServletService sSS = new NewShopServletService(ctx);
        sSS.setRequestJ(new JSONObject(request.getParameter("request")));

        if(!sSS.getRequestJ().has("employeeId")){
            sSS.getRequestJ().put("employeeId",sSS.getRequestJ().get("employee_id"));
        }
        HtmlLoader loader = new HtmlLoader();

        String ipNode = System.getProperty("ip.node");

        String uri = "http://" + ipNode + ":8103/showCase?request="+ URLEncoder.encode(sSS.getRequestJ().toString(), StandardCharsets.UTF_8);

        String res = loader.getHTML(uri);

        System.out.println(res);
        response.getWriter().print(res);

/*
            shopId = sSS.getRequestJ().getLong("shop_id");
            IndexPageShopDao indexPageShopDao = ctx.getBean("jpaIndexPageShop",IndexPageShopDao.class);
            EntityIndexPageShop entityIndexPageShop;
            if(sSS.getRequestJ().has("showCaseId")){
                    entityIndexPageShop = indexPageShopDao.selectById(sSS.getRequestJ().getInt("id"));
            }else {
                entityIndexPageShop = new EntityIndexPageShop();
            }

            IndexShopService indexShopService = new IndexShopService(ctx, shopId);


            entityIndexPageShop.setShopId(shopId);
            entityIndexPageShop.setValue(indexElements.toString());

            if (indexShopService.saveIndexShop(entityIndexPageShop)) {

                sSS.getStatus().put("error", "ok");
                JSONObject showCase = new JSONObject();
                showCase.put("showCaseElement",indexElements)
                        .put("showCaseId",entityIndexPageShop.getId());


                sSS.addPropertyObject("showCase",showCase);


                sSS.finalize(response);

            } else {

                sSS.getStatus().put("error", indexShopService.getStatus());
                sSS.finalize(response);

            }

        }else {

        }*/

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}

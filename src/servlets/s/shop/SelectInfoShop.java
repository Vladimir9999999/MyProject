package servlets.s.shop;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.shop.my_service.BranchServletService;
import spring.entity.EntityShop;
import spring.interfaces.ShopDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SelectInfoShop"
            ,urlPatterns = "/select.info.shop")

public class SelectInfoShop extends HttpServlet {

    String c = "sel_inf_shop";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if(sSS.initializeShop(request,response)){

            tocken = sSS.getRequestJ().getString("tocken");
            shopId = sSS.getRequestJ().getLong("shop_id");
            sSS.getStatus().put("case",c);
        }else{
            return;
        }

        ShopDao shopDao = ctx.getBean("jpaShop",ShopDao.class);

        EntityShop shop = shopDao.findById(shopId);

        if (shop != null) {

            JSONObject shopJ = new JSONObject();

            shopJ.put("id",shop.getId());
            shopJ.put("name_shop",shop.getNameShop());
            shopJ.put("server_ip",shop.getServerIp());
            shopJ.put("theme_id",shop.getThemeId());
            shopJ.put("information",shop.getInformation());
            shopJ.put("phone",shop.getPhone());

            BranchServletService bSS = new BranchServletService(ctx);

            JSONArray branchesArrayJ = bSS.getBranchesArrayJ(shop.getEntityBranchList());

            if (branchesArrayJ != null) {

                shopJ.put("branches", branchesArrayJ);
            }

            sSS.getBody().put("shop", shopJ);
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}


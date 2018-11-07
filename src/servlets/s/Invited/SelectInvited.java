package servlets.s.Invited;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.Invited.my_service.InvitedServletService;
import spring.entity.EntityUserShops;
import spring.interfaces.UserShopDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectInvited"
           ,urlPatterns = "/select.invited")

public class SelectInvited extends HttpServlet {

    private String c = "sel.inv";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        final int REF = 0;

        if(sSS.initializeShop(request,response)){

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case",c);
        }else{
            return;
        }


        UserShopDao userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);

        List<EntityUserShops> userShopsList = userShopDao.findByShop(shopId);

        InvitedServletService iSS = new InvitedServletService();

        JSONArray invitedArrayJ = iSS.recursive(REF,userShopsList);

        if (invitedArrayJ.length() != 0) {

            sSS.getBody().put("invited", invitedArrayJ);

        }

        sSS.getStatus().put("error", "ok");

        sSS.finalizeShop(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}


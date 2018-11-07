package servlets.s.buyer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.buyer.my_service.BuyerServletService;
import spring.entity.EntityUserShops;
import spring.interfaces.UserShopDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectBuyer"
           ,urlPatterns = "/select.buyer")

public class SelectBuyer extends HttpServlet {

    private String c = "slct_buyr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long buyerId;
        long shopId;
        String tocken;

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");

            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        UserShopDao userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);

        BuyerServletService bSS = new BuyerServletService(ctx);

        List<EntityUserShops> listUserShops = userShopDao.findByShop(shopId);

        if (sSS.getRequestJ().has("id")) {

            buyerId = sSS.getRequestJ().getLong("id");

            if (buyerId <= 0) {

                sSS.getStatus().put("error", "Некорректно указан id");
                sSS.finalize(response);
                return;
            }

            JSONObject buyerJ = bSS.getBayerJ(buyerId,shopId);

            if (buyerJ == null) {

                sSS.getStatus().put("error", "Запись не найдена");
                sSS.finalize(response);
                return;
            }

            sSS.getBody().put("buyer", buyerJ);
            sSS.getStatus().put("error", "ok");
            sSS.finalize(response);
            return;
        }

        if (listUserShops != null && listUserShops.size() != 0) {

            JSONArray buyerArrayJ = bSS.getBayerArrayJ(listUserShops);

            if (buyerArrayJ.length() != 0) {

                sSS.getBody().put("buyers", buyerArrayJ);
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }
}

//localhost:8080/ice/select.buyer?request={"tocken":"5cf73532f63c1a56f44d0a9de5c7e33c","employee_id":1414}

package servlets.s.basket;

import interfaces.ServletService;
import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.basket.my_service.BasketServletService;
import spring.entity.EntityBasket;
import spring.interfaces.BasketDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectAllBasket"
           ,urlPatterns = "/select.all.basket")
public class SelectAllBasket extends HttpServlet {

    private String c = "sel_all_bas";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ServletService uSS = new ShopServletService(ctx);

        long shopId;
        long userId;
        String tocken;

        if (uSS.initialize(request, response)) {

            shopId = uSS.getRequestJ().getLong("shop_id");
            tocken = uSS.getRequestJ().getString("tocken");

            uSS.getStatus().put("case", c);
        } else {
            return;
        }

        if (!uSS.getRequestJ().has("user_id")) {

            uSS.getStatus().put("error", "Отсутствует user_id");
            uSS.finalize(response);
            return;
        }

        userId = uSS.getRequestJ().getLong("user_id");

        BasketDao basketDao = ctx.getBean("jpaBasket", BasketDao.class);

        List<EntityBasket> listBaskets = basketDao.selectByUserId(userId);

        if (listBaskets != null && listBaskets.size() != 0) {

            BasketServletService bSS = new BasketServletService();

            JSONArray basketsArrayJ = bSS.getBasketsArrayJ(listBaskets);

            if (basketsArrayJ.length() != 0) {

                uSS.getBody().put("baskets", basketsArrayJ);
            }
        }

        uSS.getStatus().put("error", "ok");

        uSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

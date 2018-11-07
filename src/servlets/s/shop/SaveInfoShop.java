package servlets.s.shop;

import org.json.JSONArray;
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

@WebServlet(name = "SaveInfoShop"
        ,urlPatterns = "/save.info.shop")

public class SaveInfoShop extends HttpServlet {

    String c = "sav_inf_shop";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        String tocken;
        long shopId;

        JSONArray branchesArrayJ;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            tocken = sSS.getRequestJ().getString("tocken");
            shopId = sSS.getRequestJ().getLong("shop_id");
            sSS.getStatus().put("case", c);

        } else {
            return;
        }

        ShopDao shopDao = ctx.getBean("jpaShop", ShopDao.class);

        EntityShop shop = shopDao.findById(shopId);

        if (shop == null) {

            shop = new EntityShop();
        }

        if (sSS.getRequestJ().has("name_shop")) {
            shop.setNameShop(sSS.getRequestJ().getString("name_shop"));
        }

        if (sSS.getRequestJ().has("server_ip")) {
            shop.setServerIp(sSS.getRequestJ().getString("server_ip"));
        }

        if (sSS.getRequestJ().has("information")) {
            shop.setInformation(sSS.getRequestJ().getString("information"));
        }

        if (sSS.getRequestJ().has("theme_id")) {
            shop.setThemeId(sSS.getRequestJ().getInt("theme_id"));
        }

        if (sSS.getRequestJ().has("phone")) {
            shop.setPhone(sSS.getRequestJ().getLong("phone"));
        }

        shop.setId(shopId);

        if (shop.getNameShop() == null && shop.getServerIp() == null && shop.getInformation() == null
                && shop.getThemeId() == null && shop.getPhone() == null) {

            sSS.getStatus().put("error", "Отсутствуют параметры");
            sSS.finalize(response);
            return;
        }

        shopDao.save(shop);

        if (sSS.getRequestJ().has("branches")) {

            branchesArrayJ = sSS.getRequestJ().getJSONArray("branches");

            if (branchesArrayJ == null || branchesArrayJ.length() == 0) {

                sSS.getStatus().put("error", "Отсутствуют branches");
                sSS.finalize(response);
                return;
            }

            BranchServletService bSS = new BranchServletService(ctx);

            boolean check = bSS.saveBranches(shop, branchesArrayJ);

            if (!check) {

                sSS.getStatus().put("error", "Запись не найдена");
                sSS.finalize(response);
                return;
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        doPost(request, response);
    }
}

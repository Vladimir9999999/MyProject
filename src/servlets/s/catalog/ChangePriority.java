package servlets.s.catalog;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.NewShopServletService;
import servlets.ShopServletService;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityPrice;
import spring.interfaces.CategoryShopDao;
import spring.interfaces.PriceDao;
import utils.CodeResponse;
import utils.PriceService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ChangePriority",
urlPatterns = "/change.priority")
public class ChangePriority extends HttpServlet {

    private String c = "hange_srvs";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject requestJ = new JSONObject(request.getParameter("request"));

        long shopId;

        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        NewShopServletService sSS = new NewShopServletService(ctx);


        if(sSS.initializeShop(request,response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");

        }else {

            return;

        }
        sSS.getStatus().put("case",c);

        try {

            boolean flagIsProductAndCategory = true;

            PriceDao priceDao = ctx.getBean("jpaPrice", PriceDao.class);

            List<EntityPrice> entityPrices = null;

            PriceService priceService = new PriceService(ctx);

            if (sSS.getRequestJ().has("products")) {

                entityPrices = priceService.changeProductPriority(sSS.getRequestJ().getJSONArray("products"),shopId);
                flagIsProductAndCategory = entityPrices != null;

            }

            List<EntityCategoryShop> entityCategoryShops = null;
            CategoryShopDao categoryShopDao = ctx.getBean("jpaCategoryShop", CategoryShopDao.class);

            if (sSS.getRequestJ().has("categories") && flagIsProductAndCategory) {

                entityCategoryShops = priceService.changeCategoryShopPriority(sSS.getRequestJ().getJSONArray("categories"),shopId);
                flagIsProductAndCategory = entityCategoryShops != null;

            }
            JSONObject object = new JSONObject();
            if(flagIsProductAndCategory) {
                if(entityCategoryShops != null) {
                    categoryShopDao.saveAll(entityCategoryShops);
                }
                if(entityPrices!=null) {
                    priceDao.saveAll(entityPrices);
                }
                sSS.getStatus().put("error", "ok");

                object.put("priorityChanged","ok");
                sSS.addPropertyObject("catalog",object);

            }else{

                JSONObject priceJ = priceService.getPriceUser(ctx, shopId);
                sSS.getBody().put("price", priceJ);


                sSS.getStatus().put("error", "найдены несуществующие товары или категории").put("code", CodeResponse.PRICE_ERROR);


                sSS.finalize(response);
                return;

            }

        }catch (JSONException e){

            sSS.getStatus().put("error","priority - не JSONArray");

        }

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    private JSONObject errorUncovnCategoryOrProduct(){
        return new JSONObject();
    }
}

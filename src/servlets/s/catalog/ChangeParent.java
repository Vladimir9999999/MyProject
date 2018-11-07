package servlets.s.catalog;

import org.json.JSONArray;
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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChangeParent",
urlPatterns = "/change.parent")
public class ChangeParent extends HttpServlet {
    String c = "ch_parent";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        NewShopServletService sSS = new NewShopServletService(ctx);

        if(sSS.initializeShop(request,response)){
            sSS.getStatus().put("case",c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");

        }else {
            return;
        }

        try{
            JSONArray services = null;
            JSONArray categories = null;

            if(sSS.getRequestJ().has("products")) {

                services = sSS.getRequestJ().getJSONArray("products");

            }

            if(sSS.getRequestJ().has("categories")) {

                categories = sSS.getRequestJ().getJSONArray("categories");

            }

            long newParentId = sSS.getRequestJ().getLong("parent");



            if(services != null) {

                PriceDao priceDao = ctx.getBean("jpaPrice", PriceDao.class);
                List<EntityPrice> prices = new ArrayList<>();
                for(int i=0; i<services.length(); i++){

                    EntityPrice price = priceDao.selectByShopIdByID(shopId, services.getLong(i) );
                    CategoryShopDao categoryShopDao = ctx.getBean("jpaCategoryShop",CategoryShopDao.class);

                    EntityCategoryShop entityCategoryShop = categoryShopDao.selectById(newParentId);

                    price.setCategoryShop(entityCategoryShop);


                    prices.add(price);
                }

                priceDao.saveAll(prices);

            }

            if(categories != null){
                CategoryShopDao categoryShopDao = ctx.getBean("jpaCategoryShop",CategoryShopDao.class);
                List <EntityCategoryShop> categoryShops = new ArrayList<>();

                for (int i=0; i<categories.length(); i++) {
                    EntityCategoryShop entityCategoryShop  = categoryShopDao.selectByShopIdAndId(shopId,categories.getLong(i));

                    entityCategoryShop.setParent(newParentId);

                    categoryShops.add(entityCategoryShop);
                }

                categoryShopDao.saveAll(categoryShops);


             }

            sSS.getStatus().put("error","ok");
        }catch (JSONException e){

            e.printStackTrace();
            sSS.getStatus().put("error","Некорректные данные");

        }
        JSONObject catalog = new JSONObject();

        catalog.put("parentChanged","ok");
        sSS.addPropertyObject("catalog",catalog);
        sSS.finalize(response);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}

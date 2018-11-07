package servlets.s.catalog;

import interfaces.ServletService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityCategoryShop;
import spring.entity.EntityPrice;
import spring.entity.EntityProduct;
import spring.interfaces.CategoryShopDao;
import spring.interfaces.PriceDao;
import utils.ArticleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "SelectPriceProduct"

           ,urlPatterns = "/select.price.product")
@Deprecated
public class SelectPriceProduct extends HttpServlet {

    private String c = "sel_pr_prod";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        long userId = 0;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ServletService sSS = new ShopServletService(ctx);
        if(((ShopServletService) sSS).initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");

            PriceDao priceProductDao = ctx.getBean("jpaPrice", PriceDao.class);
            CategoryShopDao categoryShopDao = ctx.getBean("jpaCategoryShop", CategoryShopDao.class);

            List<EntityPrice> priceProductList = priceProductDao.selectByShopId(shopId);
            List<EntityCategoryShop> categoryShopList = categoryShopDao.selectByShopId(shopId);
            System.out.println(categoryShopList);
            if (priceProductList.size() > 0 || categoryShopList.size() > 0) {
                sSS.setBody(responseFormater(priceProductList, categoryShopList));
            }
        }else{
            return;
        }
        sSS.getStatus().put("error", "ok");
        sSS.getStatus().put("case", c);
        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }


    private JSONObject responseFormater(List<EntityPrice> priceProductList, List<EntityCategoryShop> categoryShops){
        JSONObject resp = new JSONObject();
        Set <EntityCategoryShop> categories = new HashSet(categoryShops);
        JSONArray productsJ = new JSONArray();


        for(EntityPrice priceProduct: priceProductList){
            JSONObject prodJ = new JSONObject();
            EntityProduct product =priceProduct.getProductByProduct();
            prodJ.put("name",product.getName());

            prodJ.put("description",priceProduct.getDescription());
            prodJ.put("id",priceProduct.getId());
            prodJ.put("parent",priceProduct.getCategoryShop().getId());
            prodJ.put("priority",priceProduct.getPriority());
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
            ArticleService articleService = new ArticleService(ctx);

            prodJ.put("articles",articleService.articlesToJSONArray(priceProduct.getEntityArticles()));

            prodJ.put("image_id",priceProduct.getId());
            categories.add(priceProduct.getCategoryShop());
            productsJ.put(prodJ);

        }

        JSONArray categoriesJ= new JSONArray();
        for(EntityCategoryShop categoryShop: categories){

            JSONObject catJ = new JSONObject();
            catJ.put("id",categoryShop.getId())
                    .put("parent",categoryShop.getParent())
                    .put("name",categoryShop.getCategoryServiceByCategory().getName())
                    .put("priority",categoryShop.getPriority());
            categoriesJ.put(catJ);

        }

        resp.put("category_name","Разделы");
        resp.put("product_name","Меню");
        resp.put("categories",categoriesJ);
        resp.put("products",productsJ);
        return resp;
    }
}

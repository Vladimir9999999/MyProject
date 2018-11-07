package servlets.s.catalog;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
import java.util.List;

@WebServlet(name = "SelectProduct",
urlPatterns = "/select.product")
public class SelectProduct extends HttpServlet {

    private String c = "slct_product";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        WebApplicationContext  ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());



        long shopId;
        String tocken;

        ShopServletService sSS = new ShopServletService(ctx);



        if(sSS.initializeShop(request,response)){

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");

        }else {

            return;

        }

        sSS.getStatus().put("case",c);

        String token = sSS.getRequestJ().getString("tocken");

        try {

            long categoryL  = sSS.getRequestJ().getLong("category");

            CategoryShopDao categoryShopDao = ctx.getBean("jpaCategoryShop", CategoryShopDao.class);

            EntityCategoryShop categoryShop =categoryShopDao.selectById(categoryL);

            if(categoryShop.getShopId() == shopId) {

                PriceDao priceDao = ctx.getBean("jpaPrice", PriceDao.class);

                List<EntityPrice> prices = priceDao.selectByCategoryShop(categoryL);
                JSONArray categoryPrice = new JSONArray();


                sSS.getBody().put("price",categoryPrice);
                sSS.getStatus().put("error","ok");
            }else {

                sSS.getStatus().put("error", "не удалось получиьть категорию");

            }

        }catch (NumberFormatException e){

            sSS.getStatus().put("error", "Неорректные данные");

        }

        sSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

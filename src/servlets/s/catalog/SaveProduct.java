package servlets.s.catalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.NewShopServletService;
import servlets.ShopServletService;
import spring.entity.*;
import spring.interfaces.*;
import utils.FilesUtil;
import utils.PriceService;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "SaveProduct",
        urlPatterns = "/save.product")
@MultipartConfig
public class SaveProduct extends HttpServlet {

    private String c = "save_service";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        request.setCharacterEncoding("UTF-8");

        NewShopServletService sSS = new NewShopServletService(ctx);

        long shopId;
        String tocken;
        System.out.println(sSS.getStatus());

        if(sSS.initializeShop(request,response)){
            sSS.getStatus().put("case",c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");

        }else {
            return;
        }

        JSONObject catalog = new JSONObject();
        try{

            long category = sSS.getRequestJ().getLong("parent");

            CategoryShopDao categoryShopDao =  ctx.getBean("jpaCategoryShop",CategoryShopDao.class);
            EntityCategoryShop categoryShop = categoryShopDao.selectById(category);


            if(category !=1 && categoryShop.getShopId() != shopId ){

                catalog.put("productSave","не удалось");

                sSS.addPropertyObject("catalog",catalog);

                sSS.getStatus().put("error","категория не найдена");

            }else{


                PriceService priceService = new PriceService(ctx);

                EntityPrice price = priceService.savePrice(categoryShop,shopId,sSS.getRequestJ());

                sSS.getStatus().put("error","ok");

                if(sSS.getRequestJ().has("id")) {

                    catalog.put("changeProduct", priceService.productToJSon(price));

                }else {

                    catalog.put("newProduct", priceService.productToJSon(price));

                }
                System.out.println(catalog);
                sSS.addPropertyObject("catalog",catalog);

                Part filePart = request.getPart("image");

                String filePath = System.getProperty("upload.dir")+"/products/";

                if (filePart!=null) {

                    FilesUtil.setImageName(String.valueOf(price.getId()));
                    FilesUtil.saveLogo(filePart,filePath);

                }

            }

        }catch (NumberFormatException e){
            catalog.put("productSave","не удалось");
            sSS.getStatus().put("error","некорректные данные");

        }catch (JSONException e){

            e.printStackTrace();
            sSS.getStatus().put("error","ошибка json");

        }

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

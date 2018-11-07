package servlets.s.news;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityNews;
import spring.interfaces.NewsDao;
import utils.FilesUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "SaveNews"
          ,urlPatterns = "/save.news")

@MultipartConfig
public class SaveNewsS extends HttpServlet {

    private String c = "sav_news";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;
        JSONArray products;

        if(sSS.initializeShop(request,response)){

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case",c);
        }else{
            return;
        }

        NewsDao newsDao = ctx.getBean("jpaNews",NewsDao.class);

        EntityNews news = new EntityNews();
        news.setShopId(shopId);
        news.setHeader(sSS.getRequestJ().getString("header"));
        news.setBody(sSS.getRequestJ().getString("body"));

        newsDao.save(news); // Сохраняет в List<> и создает новую Entity


        if(sSS.getRequestJ().has("products")){

            products = sSS.getRequestJ().getJSONArray("products");

        }

        sSS.getBody().put("id", news.getId());

        sSS.getStatus().put("error", "ok");

        Part filePart = request.getPart("image");

        String filePath = System.getProperty("upload.dir") + "/shops/" + shopId + "/news/";

        if (filePart == null) {

            sSS.finalize(response);

        }else {

            FilesUtil.setImageName(String.valueOf(news.getId()));
            FilesUtil.saveLogo(filePart, filePath);

            sSS.finalize(response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        doPost(request, response);

    }
}

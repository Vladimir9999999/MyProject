package servlets.s;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityNews;
import spring.entity.EntityPhoto;
import spring.interfaces.NewsDao;
import spring.interfaces.PhotoDao;
import spring.interfaces.PollingDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "InterfacesS"
        ,urlPatterns = "/select.interface")

public class InterfaceS extends HttpServlet {

    String c = "slct_interface";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        if (sSS.initializeShop(request,response)){

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case",c);
        }else {
            return;
        }

        long like;
        long dislike;

        NewsDao newsDao = ctx.getBean("jpaNews",NewsDao.class);
        PhotoDao photoDao = ctx.getBean("jpaPhoto",PhotoDao.class);
        PollingDao pollingDao = ctx.getBean("jpaPolling",PollingDao.class);

        EntityNews entityNews = newsDao.findLastByShopId(shopId);

        if (entityNews != null) {

            JSONObject newsJ = new JSONObject(entityNews);
            sSS.getBody().put("news",newsJ);

        }

        EntityPhoto entityPhoto = photoDao.findLastByShopId(shopId);

        if (entityPhoto != null) {

            JSONObject photoJ = new JSONObject(entityPhoto);
            sSS.getBody().put("photo",photoJ);

        }

        like = pollingDao.counter(shopId,true);
        dislike = pollingDao.counter(shopId,false);

        JSONObject vote = new JSONObject();
        vote.put("like", like);
        vote.put("dislike", dislike);

        sSS.getBody().put("vote", vote);

        sSS.getStatus().put("error", "ok");

        sSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        doPost(request, response);
    }
}


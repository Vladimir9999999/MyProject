package servlets.s;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.interfaces.PollingDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SelectPolling"
           ,urlPatterns = "/select.polling")

public class SelectPollingS extends HttpServlet {

    private String c = "slct_plng";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        if(sSS.initializeShop(request,response)){

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case",c);
        }else{
            return;
        }

        long like;
        long dislike;

        PollingDao pollingDao = ctx.getBean("jpaPolling",PollingDao.class);

        like = pollingDao.counter(shopId,true);
        dislike = pollingDao.counter(shopId,false);

        sSS.getBody().put("like", like);
        sSS.getBody().put("dislike", dislike);

        sSS.getStatus().put("error", "ok");

        sSS.finalizeShop(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

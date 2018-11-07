package servlets.s.price;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityPrice;
import spring.interfaces.PriceDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectEmptyPrice"
           ,urlPatterns = "/select.empty.price")
public class SelectEmptyPrice extends HttpServlet {

    private final String c = "sel_emp_pr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        if (sSS.initialize(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        PriceDao priceDao = ctx.getBean("jpaPrice", PriceDao.class);

        List<EntityPrice> listPrice = priceDao.findAllByEntityArticles(null);

        if (listPrice != null) {

            JSONArray priceArrayJ = new JSONArray(listPrice);

            if (priceArrayJ.length() != 0) {

                sSS.getBody().put("prices", priceArrayJ);
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

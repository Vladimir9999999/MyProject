package servlets;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import spring.entity.EntityCashback;
import spring.interfaces.CashbackDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SelectCashback",
urlPatterns = "/select.cashback")
public class SelectCashback extends HttpServlet {

    private String c =  "slct_cb";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        long shopId;
        String tocken;

        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {
            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
        } else {

            return;

        }

        try{


            CashbackDao cashbackDao = ctx.getBean("jpaCashback",CashbackDao.class);

            EntityCashback cashback = cashbackDao.selectByShopId(shopId);

            if(cashback ==null){
                sSS.getStatus().put("error","запись отсутствует");
            }else {

                sSS.getBody().put("standard", (float) cashback.getStandard()/100);
                sSS.getBody().put("silver", (float) cashback.getSilver()/100);
                sSS.getBody().put("gold", (float) cashback.getGold()/100);
                sSS.getBody().put("partner", (float) cashback.getPartner()/100);

                sSS.getStatus().put("error","ok");

            }
        }catch (NumberFormatException e){

            sSS.getStatus().put("error","некорректные данные");

        }

        sSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }

}

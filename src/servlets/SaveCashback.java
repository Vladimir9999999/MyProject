package servlets;

import org.json.JSONException;
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

@WebServlet(name = "SaveCashback",
urlPatterns = "/save.cashback")
public class SaveCashback extends HttpServlet {

    private String c = "save_cb";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {
            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
        } else {

            return;

        }
        try {


            int standart = Math.round(sSS.getRequestJ().getFloat("standard")*100);
            int silver = Math.round(sSS.getRequestJ().getFloat("silver")*100);
            int gold  = Math.round(sSS.getRequestJ().getFloat("gold")*100);

            int partner = Math.round(sSS.getRequestJ().getFloat("partner")*100);

            CashbackDao cashbackDao  = ctx.getBean("jpaCashback",CashbackDao.class);

            EntityCashback entityCashback ;
            entityCashback = cashbackDao.selectByShopId(shopId);

            if(entityCashback == null){
                entityCashback = new EntityCashback();
            }

            entityCashback.setShopId(shopId);
            entityCashback.setStandard(standart);
            entityCashback.setSilver(silver);
            entityCashback.setGold(gold);
            entityCashback.setPartner(partner);


            cashbackDao.save(entityCashback);
            sSS.getStatus().put("error","ok");
        }catch (JSONException e ){

            sSS.getStatus().put("error","Некорректные данные");

        }

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

package servlets.s.delete_players_and_payments;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.interfaces.PaymentDao;
import spring.interfaces.PlayerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeletePlayer"
           ,urlPatterns = "/delete.player")
public class DeletePlayer extends HttpServlet {

    private String c = "del_play";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long id;
        String tocken;

        if (sSS.initializeShop(request, response)) {

            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        if (sSS.getRequestJ().has("id")) {

            id = sSS.getRequestJ().getLong("id");

        } else {
            sSS.getStatus().put("error", "Отсутствует id");
            sSS.finalize(response);
            return;
        }

        PlayerDao playerDao = ctx.getBean("jpaPlayer", PlayerDao.class);

        if (!playerDao.existsById(id)) {
            sSS.getStatus().put("error", "Player несуществует");
            sSS.finalize(response);
            return;
        }

        playerDao.deleteById(id);

        PaymentDao paymentDao = ctx.getBean("jpaPayment", PaymentDao.class);

        paymentDao.deleteAllByUserId(id);

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
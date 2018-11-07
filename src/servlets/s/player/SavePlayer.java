package servlets.s.player;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityPlayer;
import spring.interfaces.PlayerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SavePlayer"
           ,urlPatterns ="/save.player")

public class SavePlayer extends HttpServlet {

    private final String c = "sav_play";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initialize(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        PlayerDao playerDao = ctx.getBean("jpaPlayer", PlayerDao.class);

        EntityPlayer player;

        if (sSS.getRequestJ().has("id")) {

            player = playerDao.findById(sSS.getRequestJ().getLong("id"));

            if (player == null) {

                sSS.getStatus().put("error", "Запись не найдена");
                sSS.finalize(response);
                return;
            }

        } else {
            player = new EntityPlayer();
        }

        if (sSS.getRequestJ().has("name")) {
            player.setName(sSS.getRequestJ().getString("name"));
        }

        if (sSS.getRequestJ().has("patronymic")) {
            player.setPatronymic(sSS.getRequestJ().getString("patronymic"));
        }

        if (sSS.getRequestJ().has("surname")) {
            player.setSurname(sSS.getRequestJ().getString("surname"));
        }

        if (player.getName() == null && player.getPatronymic() == null
                && player.getSurname() == null) {

            sSS.getStatus().put("error", "Отсутствуют параметры");
            sSS.finalize(response);
            return;

        } else {

            player.setShopId(shopId);

            playerDao.save(player);
        }

        sSS.getBody().put("id", player.getId());

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
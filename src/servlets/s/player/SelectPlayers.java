package servlets.s.player;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.player.my_service.PlayerServletService;
import spring.entity.EntityPlayer;
import spring.interfaces.PlayerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectPlayers"
           ,urlPatterns = "/select.players")

public class SelectPlayers extends HttpServlet {

    private final String c = "sel_play";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);

        } else {

            return;

        }

        PlayerDao playerDao = ctx.getBean("jpaPlayer", PlayerDao.class);

        List<EntityPlayer> listPlayers = playerDao.findByShopId(shopId);

        if (listPlayers.size() != 0) {

            PlayerServletService pSS = new PlayerServletService(ctx);

            JSONArray playersArrayJ = pSS.getPlayersArrayJ(listPlayers);

            if (playersArrayJ != null && playersArrayJ.length() != 0) {

                sSS.getBody().put("players", playersArrayJ);
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
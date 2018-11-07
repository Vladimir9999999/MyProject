package servlets.s.message;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityMessage;
import spring.entity.EntityUserShops;
import spring.interfaces.MessageDao;
import spring.interfaces.UserShopDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SaveMessage"
           ,urlPatterns = "/save.message")

public class SaveMessage extends HttpServlet {

    private String c = "sav_mess";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        String tocken;
        JSONArray adresse = null;
        String messageS;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);

            if (!sSS.getRequestJ().has("message")) {

                sSS.getStatus().put("error", "Отсутствуют ключ message");
                sSS.finalize(response);
                return;
            }

            messageS = sSS.getRequestJ().getString("message");

            if (messageS.length() > 255) {

                sSS.getStatus().put("error", "Некорректная длина сообщения");
                sSS.finalize(response);
                return;
            }

            tocken = sSS.getRequestJ().getString("tocken");
            shopId = sSS.getRequestJ().getLong("shop_id");

            if (sSS.getRequestJ().has("adresse")) {
                adresse = sSS.getRequestJ().getJSONArray("adresse");
            }
        } else {
            return;
        }

        MessageDao messageDao = ctx.getBean("jpaMessage", MessageDao.class);

        EntityMessage message;

        if (sSS.getRequestJ().has("id")) {

            long id = sSS.getRequestJ().getLong("id");

            message = messageDao.findById(id);

            if (message == null) {

                sSS.getStatus().put("error", "Запись не найдена");
                sSS.finalize(response);
                return;
            }

        } else {

            message = new EntityMessage();
        }

        if ((messageS.trim().length() == 0)) {

            sSS.getStatus().put("error", "Отсутствует текст сообщения");
            sSS.finalize(response);
            return;
        }

        UserShopDao userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);

        List<EntityUserShops> entityUserShops;

        if (adresse == null) {

            entityUserShops = userShopDao.findByShop(shopId);

        } else {

            entityUserShops = new ArrayList<>();

            for (int i = 0; i < adresse.length(); i++) {

                EntityUserShops eu = userShopDao.findByUserIdAndShop(adresse.getLong(i), shopId);
                entityUserShops.add(eu);

            }
        }

        message.setEntityUserShopsList(entityUserShops);

        message.setMessage(messageS);

        message.setShopId(shopId);

        messageDao.save(message);

        sSS.getBody().put("id", message.getId());

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}


package servlets.s.send_message;

import Models.Message;
import constant.СonstantMessage;
import messagers.MessagerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityAccountUsers;
import spring.entity.EntitySession;
import spring.entity.EntityUserShops;
import spring.interfaces.AccountUserDao;
import spring.interfaces.SessionDao;
import spring.interfaces.UserShopDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SendMessageS"
           ,urlPatterns = "/select.send.message")

public class SendMessageS extends HttpServlet {

    String c = "sel_sen_mes";

    private final String ALL_GOOGLE = "all_google";
    private final String ALL_APPLE = "all_apple";
    private final String ALL_USERS = "all_users";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long phone;
        long shopId;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initialize(request, response)) {

            tocken = sSS.getRequestJ().getString("tocken");
            shopId = sSS.getRequestJ().getLong("shop_id");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        Message message = new Message();

        message.setType(СonstantMessage.MESSAGE);

        String title = sSS.getRequestJ().getString("title");

        if (title != null) {

            if (title.trim().length() == 0) {

                sSS.getStatus().put("error", "Отсутствует текст title");
                sSS.finalize(response);
                return;
            }

            message.setTitle(title);

        } else {

            sSS.getStatus().put("error", "Отсутствует ключ title");
            sSS.finalize(response);
            return;
        }

        String body = sSS.getRequestJ().getString("body");

        if (body != null) {

            if (body.trim().length() == 0) {

                sSS.getStatus().put("error", "Отсутствует текст body");
                sSS.finalize(response);
                return;
            }

            message.setBody(body);

        } else {

            sSS.getStatus().put("error", "Отсутствует ключ body");
            sSS.finalize(response);
            return;
        }

        SessionDao sessionDao = ctx.getBean("jpaSession", SessionDao.class);

        if (sSS.getRequestJ().has("dispatch")) {

            switch (sSS.getRequestJ().getString("dispatch")) {

                case ALL_GOOGLE:

                    List<EntitySession> sessionGoogle = sessionDao.findByGoogleTockenIsNotNull();

                    MessagerFactory.sendMessage(sessionGoogle, message);

                    sSS.getBody().put("Отправлено сообщение", "Всем по google_tocken");
                    sSS.getStatus().put("error", "ok");
                    sSS.finalize(response);
                    return;

                case ALL_APPLE:

                    List<EntitySession> sessionApple = sessionDao.findByAppleTockenIsNotNull();

                    MessagerFactory.sendMessage(sessionApple, message);

                    sSS.getBody().put("Отправлено сообщение", "Всем по apple_tocken");
                    sSS.getStatus().put("error", "ok");
                    sSS.finalize(response);
                    return;

                    //todo нужно проверить скрипт ниже

                case ALL_USERS:

                    UserShopDao userShopDao = ctx.getBean("jpaUserShop", UserShopDao.class);

                    List<EntityUserShops> userShopsList = userShopDao.findByShop(shopId);

                    for (EntityUserShops userShops : userShopsList) {

                        List<EntitySession> sessionList = sessionDao.findByUserId(userShops.getUserId());

                        MessagerFactory.sendMessage(sessionList, message);
                    }

                    sSS.getBody().put("Отправлено сообщение", "Всем пользователям");
                    sSS.getStatus().put("error", "ok");
                    sSS.finalize(response);
                    return;

            }
        }

        if (!sSS.getRequestJ().has("phone")) {

            sSS.getStatus().put("error", "Отсутствует номер телефона");
            sSS.finalize(response);
            return;
        }

        phone = sSS.getRequestJ().getLong("phone");

        AccountUserDao accountUserDao = ctx.getBean("jpaAccountUser", AccountUserDao.class);

        EntityAccountUsers accountUsers = accountUserDao.selectByMobile(phone);

        List<EntitySession> sessionList = sessionDao.findByUserId(accountUsers.getId());

        MessagerFactory.sendMessage(sessionList, message);

        sSS.getBody().put("Отправлено сообщение на номер", phone);
        sSS.getStatus().put("error", "ok");
        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}


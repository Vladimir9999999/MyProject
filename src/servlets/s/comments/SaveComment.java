package servlets.s.comments;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityAccountUsers;
import spring.entity.EntityOrders;
import spring.entity.EntityComment;
import spring.interfaces.AccountUserDao;
import spring.interfaces.OrderDao;
import spring.interfaces.CommentDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "SaveComment"
           ,urlPatterns = "/save.comment")

public class SaveComment extends HttpServlet {

    private String c = "sav.com";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tocken;
        long shopId;
        long clientId = 0;
        long orderId = 0;
        String text = null;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            if (!sSS.getRequestJ().has("client_id") && !sSS.getRequestJ().has("order_id") &&
                    !sSS.getRequestJ().has("text") && !sSS.getRequestJ().has("shoe_size") &&
                    !sSS.getRequestJ().has("diameter")) {

                sSS.getStatus().put("error", "Отсутствуют ключи")
                        .put("case",c);
                sSS.finalizeShop(response);
                return;
            }

            tocken = sSS.getRequestJ().getString("tocken");
            shopId = sSS.getRequestJ().getLong("shop_id");

            if (sSS.getRequestJ().has("text")) {
                text = sSS.getRequestJ().getString("text");

                if (text.length() > 255) {

                    sSS.getStatus().put("error", "Некорректная длина сообщения");
                    sSS.finalizeShop(response);
                    return;
                }
            }

            if (sSS.getRequestJ().has("client_id")) {
                clientId = sSS.getRequestJ().getLong("client_id");

                if (clientId <= 0) {

                    sSS.getStatus().put("error", "Некорректно указан client_id");
                    sSS.finalizeShop(response);
                    return;
                }
            }

            if (sSS.getRequestJ().has("order_id")) {
                orderId = sSS.getRequestJ().getLong("order_id");

                if (orderId <= 0) {

                    sSS.getStatus().put("error", "Некорректно указан order_id");
                    sSS.finalizeShop(response);
                    return;
                }
            }

            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        CommentDao commentDao = ctx.getBean("jpaComment", CommentDao.class);

        EntityComment comment;

        if (sSS.getRequestJ().has("id")) {

            comment = commentDao.findByIdAndShopId(sSS.getRequestJ().getLong("id"), shopId);

            if (comment == null) {

                sSS.getStatus().put("error", "Запись не найдена");
                sSS.finalizeShop(response);
                return;
            }

        } else {
            comment = new EntityComment();
        }

        if (sSS.getRequestJ().has("shoe_size")) {

            byte minSize = 14;
            byte maxSize = 57;

            int shoeSize = sSS.getRequestJ().getInt("shoe_size");

            if (shoeSize > minSize && shoeSize < maxSize) {

                comment.setShoeSize((byte) shoeSize);
            } else {

                sSS.getStatus().put("error", "Некорректно указан размер обуви");
                sSS.finalizeShop(response);
                return;
            }
        }

        if (text != null) {

            if ((text.isEmpty() || text.trim().length() == 0)) {

                sSS.getStatus().put("error", "Отсутствует текст сообщения");
                sSS.finalizeShop(response);
                return;
            }

            comment.setText(text);
        }

        if (clientId > 0) {

            AccountUserDao accountUserDao = ctx.getBean("jpaAccountUser", AccountUserDao.class);
            EntityAccountUsers accountUsers = accountUserDao.selectById(clientId);

            if (accountUsers == null) {
                sSS.getStatus().put("error", "client_id несуществует");
                sSS.finalizeShop(response);
                return;
            }

            comment.setClientId(clientId);
        }

        if (orderId > 0) {

            OrderDao orderDao = ctx.getBean("jpaOrder", OrderDao.class);
            EntityOrders orders = orderDao.findById(orderId);

            if (orders == null) {
                sSS.getStatus().put("error", "order_id несуществует");
                sSS.finalizeShop(response);
                return;
            }

            comment.setOrderId(orderId);
        }

        if (sSS.getRequestJ().has("diameter")) {

            byte minSize = 5;
            byte maxSize = 41;

            int diameter = sSS.getRequestJ().getInt("diameter");

            if (diameter > minSize && diameter < maxSize) {

                comment.setDiameter((byte) diameter);

            } else {

                sSS.getStatus().put("error", "Некорректно указан диаметер");
                sSS.finalizeShop(response);
                return;
            }
        }

        if (comment.getShoeSize() == null && comment.getDiameter() == null
                && (comment.getText() == null)) {

            sSS.getStatus().put("error", "Отсутствуют параметры");
            sSS.finalizeShop(response);
            return;
        } else {

            comment.setTime(new Timestamp(new Date().getTime()));

            comment.setShopId(shopId);

            commentDao.save(comment);
        }

        sSS.getBody().put("id", comment.getId());

        sSS.getStatus().put("error", "ok");

        sSS.finalizeShop(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}


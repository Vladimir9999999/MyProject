package servlets.s.comments;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import servlets.s.comments.my_service.CommentServletService;
import spring.entity.EntityComment;
import spring.interfaces.CommentDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectComment"
           ,urlPatterns = "/select.comment")

public class SelectComment extends HttpServlet {

    private String c = "sel.com";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ShopServletService sSS = new ShopServletService(ctx);

        String tocken;
        long shopId;
        long clientId = 0;
        long orderId = 0;

        if (sSS.initializeShop(request, response)) {

            tocken = sSS.getRequestJ().getString("tocken");
            shopId = sSS.getRequestJ().getLong("shop_id");

            if (sSS.getRequestJ().has("client_id")) {
                clientId = sSS.getRequestJ().getLong("client_id");
            }
            if (sSS.getRequestJ().has("order_id")) {
                orderId = sSS.getRequestJ().getLong("order_id");
            }
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        CommentDao commentDao = ctx.getBean("jpaComment", CommentDao.class);

        if (!sSS.getRequestJ().has("order_id") && !sSS.getRequestJ().has("client_id")) {

            List<EntityComment> listComments = commentDao.findByShopId(shopId);

            if (listComments != null && listComments.size() != 0) {

                CommentServletService commentSS = new CommentServletService();

                JSONArray commentsArrayJ = commentSS.getCommentsArrayJ(listComments);

                if (commentsArrayJ != null && commentsArrayJ.length() != 0) {

                    sSS.getBody().put("all_comments", commentsArrayJ);

                    sSS.getStatus().put("error","ок");

                    sSS.finalizeShop(response);
                    return;
                }
            }
        }

        if (clientId > 0) {

            List<EntityComment> listComments = commentDao.findByClientId(clientId);

            if (listComments != null && listComments.size() != 0) {

                CommentServletService commentSS = new CommentServletService();

                JSONArray commentsArrayJ = commentSS.getCommentsArrayJ(listComments);

                if (commentsArrayJ != null && commentsArrayJ.length() != 0) {

                    sSS.getBody().put("comments_client", commentsArrayJ);

                }
            }
        }

        if (orderId > 0) {

            List<EntityComment> listComments = commentDao.findByOrderId(orderId);

            if (listComments != null && listComments.size() != 0) {

                CommentServletService commentSS = new CommentServletService();

                JSONArray commentsArrayJ = commentSS.getCommentsArrayJ(listComments);

                if (commentsArrayJ != null && commentsArrayJ.length() != 0) {

                    sSS.getBody().put("comments_order", commentsArrayJ);

                }
            }
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalizeShop(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

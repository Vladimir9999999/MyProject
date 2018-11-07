package servlets.s.article;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityArticle;
import spring.interfaces.ArticleDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteArticle"
        ,urlPatterns = "/delete.article")
public class DeleteArticle extends HttpServlet {

    private String c = "del_art";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long articleId;
        String tocken;

        if (sSS.initializeShop(request, response)) {

            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        if (!sSS.getRequestJ().has("article_id")) {

            sSS.getStatus().put("error", "Отсутствует article_id");
            sSS.finalize(response);
            return;
        }

        articleId = sSS.getRequestJ().getLong("article_id");

        ArticleDao articleDao = ctx.getBean("jpaArticle", ArticleDao.class);

        if (!articleDao.existsById(articleId)) {
            sSS.getStatus().put("error", "Article несуществует");
            sSS.finalize(response);
            return;
        }

        EntityArticle article = articleDao.selectById(articleId);

        article.setEntityPrice(null);

        articleDao.save(article);

        articleDao.deleteAllByEntityPrice(null);

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}


package servlets.s.article;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import utils.ArticleService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveArticle"
           ,urlPatterns = "/save.article")
public class SaveArticle extends HttpServlet {

    private String c = "sav_art";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tocken;
        long priceId;
        JSONArray articlesArrayJ;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initialize(request, response)) {

            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        if (!sSS.getRequestJ().has("price_id")) {

            sSS.getStatus().put("error", "Отсутствует price_id");
            sSS.finalize(response);
            return;
        }

        priceId = sSS.getRequestJ().getLong("price_id");

        if (!sSS.getRequestJ().has("articles")) {

            sSS.getStatus().put("error", "Отсутствует ключ articles");
            sSS.finalize(response);
            return;
        }

        articlesArrayJ = sSS.getRequestJ().getJSONArray("articles");

        if (articlesArrayJ == null || articlesArrayJ.length() == 0) {

            sSS.getStatus().put("error", "Отсутствуют articles");
            sSS.finalize(response);
            return;
        }

        ArticleService aS = new ArticleService(ctx);

        boolean check = aS.saveArticles(priceId, articlesArrayJ);

        if (!check) {

            sSS.getStatus().put("error", "Запись не найдена");
            sSS.finalize(response);
            return;
        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}

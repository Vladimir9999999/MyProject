package servlets;

import interfaces.ServletService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import spring.entity.EntitySession;
import spring.interfaces.SessionDao;
import utils.CodeResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddGoogleTocken",
        urlPatterns = "/add.gtocken")

public class AddGoogleTocken extends HttpServlet {

    private String c = "sv_ggl_tck";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        String tocken;

        long id;
        String googleTocken = null;
        String appleTocken = null;

        ServletService sS = ServletService.getServletService(request,ctx);

        if(sS == null){

            sS = new ShopServletService(ctx);
            sS.getStatus().put("error", "отсутствует employee_id");
            sS.getStatus().put("code",CodeResponse.AUTHENTICATION_ERROR);
            sS.finalize(response);
            return;
        }

        if(sS.initialize(request,response)){

            tocken = sS.getRequestJ().getString("tocken");

            sS.getStatus().put("case",c);

            if (sS.getRequestJ().has("google_tocken")) {

                googleTocken = sS.getRequestJ().getString("google_tocken");

                if (googleTocken.length() > 255) {

                    sS.getStatus().put("error", "Некорректная длина google_tocken");
                    sS.finalize(response);
                    return;
                }
            }

            if (sS.getRequestJ().has("apple_tocken")) {

                appleTocken = sS.getRequestJ().getString("apple_tocken");

                if (appleTocken.length() > 255) {

                    sS.getStatus().put("error", "Некорректная длина apple_tocken");
                    sS.finalize(response);
                    return;
                }
            }

        }else{
            return;
        }

        id = sS.getId();

        SessionDao sessionDao = ctx.getBean("jpaSession", SessionDao.class);

        EntitySession session = sessionDao.findByUserIdAndTocken(id,tocken);

        if (googleTocken != null) {

            session.setGoogleTocken(googleTocken);
        }

        if (appleTocken != null) {

            session.setAppleTocken(appleTocken);
        }

        if (googleTocken != null || appleTocken != null) {

            sessionDao.save(session);

        }else {

            sS.getStatus().put("error", "Отсутствует google_tocken и apple_tocken");
            sS.finalize(response);
            return;
        }

        sS.getStatus().put("error", "ok");

        sS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
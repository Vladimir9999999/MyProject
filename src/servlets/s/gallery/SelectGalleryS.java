package servlets.s.gallery;

import org.json.JSONArray;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityPhoto;
import spring.interfaces.PhotoDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SelectGalleryS"
          ,urlPatterns = "/select.gallery")

public class SelectGalleryS extends HttpServlet {

    private String c = "slct_glr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;

        if(sSS.initializeShop(request,response)){

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case",c);
        }else{
            return;
        }

        PhotoDao photoDao = ctx.getBean("jpaPhoto",PhotoDao.class);

        List<EntityPhoto> photoList = photoDao.findByShopId(shopId);

        if (photoList.size() != 0) {

            JSONArray photoArrayShop = new JSONArray(photoList);

            sSS.getBody().put("photos", photoArrayShop);

        }

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }

}


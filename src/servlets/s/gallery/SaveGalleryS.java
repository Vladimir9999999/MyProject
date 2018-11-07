package servlets.s.gallery;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityPhoto;
import spring.interfaces.PhotoDao;
import utils.FilesUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "SaveGallery"
          ,urlPatterns = "/save.gallery")

@MultipartConfig
public class SaveGalleryS extends HttpServlet {

    private String c = "sav_glry";

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

        EntityPhoto photo = new EntityPhoto();

        if(sSS.getRequestJ().has("description")) {

            photo.setDescription(sSS.getRequestJ().getString("description"));

        }

        photo.setShopId(shopId);

        photoDao.save(photo);//создается id фото

        sSS.getBody().put("id", photo.getId());

        sSS.getStatus().put("error", "ok");

        Part filePart = request.getPart("image");

        String filePath = System.getProperty("upload.dir") + "/shops/" + shopId + "/gallery/";

        if (filePart != null) {

            FilesUtil.setImageName(String.valueOf(photo.getId()));
            FilesUtil.saveLogo(filePart, filePath);

            sSS.finalizeShop(response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        doPost(request, response);
    }
}


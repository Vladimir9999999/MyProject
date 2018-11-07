package servlets.s;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityPartners;
import spring.interfaces.PartnerDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddPartner",
urlPatterns = "/add.partner")
public class AddPartner extends HttpServlet {
    private String c = "add_prtnr";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        long partnerId;

        if(sSS.initializeShop(request,response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            partnerId = sSS.getRequestJ().getLong("partner_id");

            sSS.getStatus().put("case", c);

        }else{
            return;
        }
        PartnerDao partnerDao= ctx.getBean("jpaPartner",PartnerDao.class);


        if(partnerDao.selectPartner(shopId,partnerId)!= null ||partnerDao.selectPartner(partnerId,shopId) != null){

            sSS.getStatus().put("error","Вы уже являетесь партнером данного предприятия");
            sSS.finalizeShop(response);
            return;

        }

        EntityPartners entityPartners = new EntityPartners();

        entityPartners.setShop1(shopId);
        entityPartners.setShop2(partnerId);

        partnerDao.save(entityPartners);

        sSS.getBody().put("id",entityPartners.getId());
        sSS.getStatus().put("error","ok");

        sSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}

package servlets.s.order;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityRemark;
import spring.entity.EntityUserShops;
import spring.interfaces.RemarkDao;
import spring.interfaces.UserShopDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddUserRemark",
urlPatterns = "/remark.user")

public class AddUserRemark extends HttpServlet {
    private final String c = "rmk_usr";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;
        long userId;

        String tocken;
        String remark;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            userId = sSS.getRequestJ().getLong("user_id");
            remark = sSS.getRequestJ().getString("remark");

        } else {

            return;

        }

        UserShopDao orderDao = ctx.getBean("jpaUserShop",UserShopDao.class);
        EntityUserShops eus = orderDao.findByUserIdAndShop(userId,shopId);

        if(eus ==null ){

            sSS.getStatus().put("error","Ордер не найден");
            sSS.finalizeShop(response);
            return;
        }

        RemarkDao remarkDao = ctx.getBean("jpaRemark", RemarkDao.class);

        EntityRemark entityRemark = new EntityRemark();

        entityRemark.setAdresse(userId);
        entityRemark.setMarketPlace(shopId);
        entityRemark.setMsg(remark);
        entityRemark.setType(EntityRemark.REMARK_TYPE_USER_SHOP);

        remarkDao.save(entityRemark);

        sSS.getStatus().put("error","ok");

        sSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

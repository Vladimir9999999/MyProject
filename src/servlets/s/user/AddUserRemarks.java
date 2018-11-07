package servlets.s.user;

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

@WebServlet(name = "AddUserRemarks",
urlPatterns = "/sdd.remark.user")
public class AddUserRemarks extends HttpServlet {
    String c = "usr_rmk_add";
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
            userId = sSS.getRequestJ().getLong("orderId");
            remark = sSS.getRequestJ().getString("remark");

        } else {

            return;

        }

        UserShopDao userShopDao = ctx.getBean("jpaUserShop",UserShopDao.class);
        EntityUserShops eus = userShopDao.findByUserIdAndShop(userId,shopId);

        if(eus ==null ){

            sSS.getStatus().put("error","Пользователь не найден");
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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

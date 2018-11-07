package servlets.s.buyer;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityAccountUsers;
import spring.entity.EntityPersonalDataUser;
import spring.entity.EntityUserShops;
import spring.interfaces.AccountUserDao;
import spring.interfaces.PersonalDataUserDao;
import spring.interfaces.UserShopDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveAccountUser", urlPatterns = "/save.buyer")
public class SaveAccountUser extends HttpServlet {
    private String c = "save_buyr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long shopId;
        String tocken;
        long phone;
        long userId;
        String name;
        Boolean sex;
        float casbackActive;

        if (sSS.initializeShop(request, response)) {

            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            phone = sSS.getRequestJ().getLong("phone");
            userId = sSS.getRequestJ().getLong("user_id");
            name = sSS.getRequestJ().getString("name");
            int s = sSS.getRequestJ().getInt("sex");
            casbackActive = sSS.getRequestJ().getFloat("cash_back_active");

            if(s < 0 || s >1){
                sex = null;
            }else {
                sex = s == 1;
            }
            sSS.getStatus().put("case", c);
        } else {
            return;
        }


        AccountUserDao accountUserDao = ctx.getBean("jpaAccountUser",AccountUserDao.class);

        EntityAccountUsers accountUsers = accountUserDao.selectByMobile(phone);

        if(accountUsers == null) {

            accountUsers = accountUserDao.selectById(userId);
            if(accountUsers == null){
                sSS.getStatus().put("error", "Пользователь не найден");
                sSS.finalize(response);
                return;
            }
            accountUsers.setLogin(String.valueOf(phone));
            accountUserDao.save(accountUsers);

        }

        if(accountUsers.getId() == userId){

            UserShopDao userShopDao = ctx.getBean("jpaUserShop",UserShopDao.class);
            EntityUserShops entityUserShops = userShopDao.findByUserIdAndShop(userId,shopId);


            entityUserShops.setCashbackActive(casbackActive);
            userShopDao.save(entityUserShops);

        }else {

            sSS.getStatus().put("error", "Пользоватль с этим номером телефона уже существует!");
            sSS.finalize(response);
            return;

        }

//todo создать пользователя если не найден телефон
// todo отправить смс при добавлении


        PersonalDataUserDao personalDataUserDao = ctx.getBean("jpaPersonalDataUser",PersonalDataUserDao.class);

        EntityPersonalDataUser entityPersonalDataUser = personalDataUserDao.findByUserId(userId);

        if(entityPersonalDataUser== null) {

            entityPersonalDataUser = new EntityPersonalDataUser();

        }

        entityPersonalDataUser.setName(name);
        entityPersonalDataUser.setSex(sex);
        entityPersonalDataUser.setUserId(userId);
        personalDataUserDao.save(entityPersonalDataUser);

        sSS.getStatus().put("error","ok");
        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

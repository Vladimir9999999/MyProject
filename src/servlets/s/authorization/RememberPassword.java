package servlets.s.authorization;

import Models.AccountEntity;
import Models.EmploeeEntity;
import Models.ShopEntity;
import dao.AccountDao;
import dao.EmployeeDao;
import dao.ShopDao;
import org.json.JSONObject;
import utils.ConstructProtocol;
import utils.GenerateToken;
import utils.SMSCodeUtil;
import utils.Security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlets.remember.password",
        urlPatterns = "/rem.pass")
public class RememberPassword extends HttpServlet {



    private String c= "new_pass";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        JSONObject respJSON =  new JSONObject();
        JSONObject status= new JSONObject();
        status.put("case",c);

        String phone = request.getParameter("login");
        String sms = request.getParameter("sms");
        String password = request.getParameter("password");

        AccountDao accountDao = new AccountDao(AccountDao.BD_SHOP);

        Security s = new Security();
        ConstructProtocol protocol = new ConstructProtocol("send");
        if(!s.isPhone(phone)){

            protocol.addError(protocol.getStatus(),"неверный формат номера");
            response.getWriter().print(protocol.getStatus());
            return;

        }


        if (phone != null && sms != null) {
            if (!accountDao.verificaion(phone)) {

                if(password==null){

                    status.put("error","отсутствует новый пароль");
                    respJSON.put("status",status);

                    response.getWriter().print(respJSON);
                    return;
                }

                AccountEntity accountEntity = new AccountEntity();
                accountEntity.setLogin(phone);
                accountEntity.setPassword(password);
                accountEntity.setSmsCode(Integer.parseInt(sms));

                if (SMSCodeUtil.isSMSCode(accountEntity)) {

                    accountDao.udate(accountEntity);

                    respJSON = auth(accountEntity);
                    response.getWriter().print(respJSON);
                    return;

                } else {
                    status.put("error","sms код неверен, или уже использован");
                }

            }else {
                status.put("error","пользователь не найден");
            }

            respJSON.put("status",status);
            response.getWriter().print(respJSON);
            return;
        }

        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }


    private JSONObject auth(AccountEntity accountShop) {

        AccountDao accountDao = new AccountDao(AccountDao.BD_SHOP);

        if (accountDao.auth(accountShop)) {

            EmploeeEntity emploeeEntity = new EmploeeEntity();
            emploeeEntity.setId(accountShop.getId());

            EmployeeDao employeeDao = new EmployeeDao();
            employeeDao.selectEmployee(emploeeEntity);


            ShopEntity shopEntity = new ShopEntity();
            shopEntity.setId(emploeeEntity.getShopId());

            ShopDao shopDao = new ShopDao();
            shopDao.selectShop(shopEntity);

            StringBuilder builder = new StringBuilder();

            builder //.append("{\"status\":")       .append("\"авторизация успешна\"")
                    .append("{\"user_id\":\"").append(accountShop.getId()).append("\"")
                    //.append(",\"name\":\"")       .append(emploeeEntity.getName()).append("\"")
                    .append(",\"shop_id\":\"").append(shopEntity.getId()).append("\"")
                    .append(",\"server_ip\":\"").append(shopEntity.getServerIp()).append("\"")
                    .append(",\"tocken\":\"").append(GenerateToken.generateToken(accountShop)).append("\"")
                    .append(",\"privilege\":\"").append(emploeeEntity.getPrivilege()).append("\"")
                    //.append(",\"orders\":")       .append(OrdersService.ordersToString(orders))
                    .append("}");

            ConstructProtocol constructProtocol = new ConstructProtocol(c);

            constructProtocol.addBody(new JSONObject(new String(builder)));


            return constructProtocol.addError(constructProtocol.getStatus(), "ok");
        }
        return null;
    }
}

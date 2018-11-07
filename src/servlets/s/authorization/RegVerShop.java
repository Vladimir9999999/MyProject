package servlets.s.authorization;


import dao.AccountDao;
import org.json.JSONObject;
import utils.ConstructProtocol;
import utils.SMSCodeUtil;
import utils.Security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "servlets.ver_shop.shop",
//        urlPatterns = "reg.ver")

@WebServlet(name = "servlets.RegVerShop",
        urlPatterns = "/reg.ver")

public class RegVerShop extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        ConstructProtocol constructProtocol = new ConstructProtocol("verification");

        String login = request.getParameter("login");




        if(login!=null) {

            Security s = new Security();
            if(!s.isPhone(login)){

                constructProtocol.addError(constructProtocol.getStatus(),"неверный формат номера");
                response.getWriter().print(constructProtocol.getStatus());
                return;

            }

            AccountDao shopDao = new AccountDao(AccountDao.BD_SHOP);

            if(!shopDao.verificaion(login)) {

                constructProtocol.addError(constructProtocol.getStatus(),"Телефон уже зарегистрирован");

            }else {
                constructProtocol.addError(constructProtocol.getStatus(),"ok");
                SMSCodeUtil.createAddSMSCode(login);

            }


            response.getWriter().print(constructProtocol.getStatus());

        }else {

            String error = "отсутствуют необходимые параметры" ;

            constructProtocol.addBody(new JSONObject("{}"));

            constructProtocol.addError(constructProtocol.getStatus(),error);

            response.getWriter().print(constructProtocol.getStatus());

        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
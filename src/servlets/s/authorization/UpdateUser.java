package servlets.s.authorization;

import Models.AccountEntity;
import dao.AccountDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlets.update.user",
        urlPatterns = "/update.user")
public class UpdateUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String idStr = request.getParameter("id");

        String newPassword = request.getParameter("new_password");
        String token = request.getParameter("token");

        if( newPassword != null && token != null && idStr != null){

            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setId(Integer.parseInt(idStr));
            accountEntity.setPassword(newPassword);

            AccountDao accountDao = new AccountDao(AccountDao.BD_SHOP);
            accountDao.udate(accountEntity);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}

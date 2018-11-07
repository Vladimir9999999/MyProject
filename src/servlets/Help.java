package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Help",
            urlPatterns = "/help")
public class Help extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        StringBuilder builderHtml = new StringBuilder();
        builderHtml.append("<html>\n")
        .append("<H2>reg.ver</H2> - ожидает номер телефона(<B>login</b>)  <b>error:ок</b>, <b>case:ver</b> если номер не зарегистрирован\n")
        .append("<H2>new.shop</H2> <ul>- ожидает <li> - номер телефона(<b>login</b>) высылает смс на этот номер если номера нет в базе <b>error:ok</b> <b>case:send</b>- все хорошо<br>\n")
        .append("                            </li><li> - номер телефона(<b>login</b>), смс(<b>sms</b>), пароль(<b>password</b>) проверяет соответствие смс кода и номера телефона\n")
        .append("                            создает учетную запись магазина если все хорошо(<b>error:ok</b>) <b>case:new</b> возвращает <b>jsonBoby:{user_id=1,shop_id=1,server_ip=128.0.0.1}</b>")
        .append("</li></ul></body>\n")

        .append("<H2>rem.ver</H2> - ожидает номер телефона(<B>login</b>)  <b>error:ок</b> <b>case:ver</b> если номер зарегистрирован\n")
        .append("<H2>rem.pass</H2> <ul>- ожидает <li> - номер телефона(<b>login</b>) высылает смс на этот номер если номер есть в базе <b>error:ok</b> <b>case:send</b> - все хорошо<br>\n")
        .append("                            </li><li> - номер телефона(<b>login</b>), смс(<b>sms</b>), пароль(<b>password</b>) проверяет соответствие смс кода и номера телефона\n")
        .append("                            создает учетную запись магазина если все хорошо(<b>error:ok</b>) <b>case:rem</b> возвращает <b>jsonBoby:{user_id=1,shop_id=1,server_ip=128.0.0.1}</b>")
        .append("</li></ul></body>\n")
        .append("</html>");

        response.getWriter().print(new String(builderHtml));

  }
}

package servlets;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import spring.entity.EntitySupport;
import spring.interfaces.SupportDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveSupport",urlPatterns = "/save.support")
public class SaveSupport extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String mail = request.getParameter("mail");
        String message = request.getParameter("message");

        EntitySupport entitySupport= new EntitySupport();

        entitySupport.setMail(mail);
        entitySupport.setMessage(message);
        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        SupportDao supportDao =  ctx.getBean("jpaSupport",SupportDao.class);
        supportDao.save(entitySupport);
        response.getWriter().print("ok");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

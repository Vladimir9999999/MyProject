package servlets;

import utils.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SaveLoadSession", urlPatterns = "./manage")
public class SaveLoadSession extends HttpServlet {
    private static String passwd = "j6h12Q22";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("c");
        String password = request.getParameter("p");
        if(password.equals(passwd)) {
            switch (command) {
                case "save":
                    SessionManager.saveList(System.getProperty("upload.dir"));
                    response.getWriter().print("saved");
                    break;
                case "load":
                    System.out.println(System.getProperty("upload.dir"));
                    SessionManager.load(System.getProperty("upload.dir"));
                    response.getWriter().print("loaded");
                    break;
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

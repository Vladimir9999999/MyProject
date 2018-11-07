package servlets.s.file;

import utils.FilesUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "UploadLogo",
urlPatterns = "/upload.logo")
@MultipartConfig
public class UploadLogo extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        Part filePart = request.getPart("file");
        String path = System.getProperty("upload.dir")+"/shops";
        FilesUtil.saveLogo(filePart,path);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String html = "<html>" +
                "<form action=\"upload.logo\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                "    <input type=\"text\" name=\"description\" />\n" +
                "    <input type=\"file\" name=\"file\" />\n" +
                "    <input type=\"submit\" />\n" +
                "</form>" +
                "</html>";
        response.getWriter().println(html);
    }
}

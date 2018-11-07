package servlets.s.catalog;

import Models.CategoryYML;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.s.catalog.my_service.CatalogServletService;
import spring.entity.EntityShop;
import spring.interfaces.ShopDao;
import utils.Exel;
import utils.YMLParse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ImportInExel", urlPatterns = "/import.exel")
public class ImportInExel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Exel exel = new Exel();

        List<CategoryYML> categoryYMLList = exel.readFile();

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopDao shopDao = ctx.getBean("jpaShop", ShopDao.class);

        EntityShop entityShop = shopDao.findById(3);

        CatalogServletService catalogServletService = new CatalogServletService(ctx, entityShop);
        catalogServletService.saveCat(categoryYMLList, 0,0);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

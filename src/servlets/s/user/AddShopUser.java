package servlets.s.user;

import spring.entity.EntityCategoryShop;
import spring.interfaces.CategoryShopDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddShopUser",
urlPatterns = "/add.shop_user")
public class AddShopUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //проверка через QRGenerator

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");



    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }

    boolean isChildren(CategoryShopDao categoryShopDao, long idCategory,long idParent){

        List <EntityCategoryShop> categoryShops = categoryShopDao.selectByParent(idCategory);

        for(EntityCategoryShop categoryShop:categoryShops){

        }

        return true;
    }

}

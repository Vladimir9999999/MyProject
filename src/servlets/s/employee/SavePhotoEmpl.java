package servlets.s.employee;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import servlets.ShopServletService;
import spring.interfaces.EmployeeDao;
import utils.EmployeeManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "SavePhotoEmp",
urlPatterns = "/upload.photoEmp")
@MultipartConfig

public class SavePhotoEmpl extends HttpServlet {

    private  String c = "photo_emp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());

        ShopServletService aSS = new ShopServletService(ctx);

        Part filePart = request.getPart("photo");

        JSONObject requestJ  =  new JSONObject(request.getParameter("request"));

        long shopId;
        String tocken;

        if(aSS.initializeShop(request,response)){

            shopId = requestJ.getLong("shop_id");
            tocken = requestJ.getString("tocken");

        }else {
            return;
        }



        String filePath = System.getProperty("upload.dir")+"/shops/"+shopId+"/employees/";
        EmployeeManager empolyeeManager = new EmployeeManager(ctx,filePath);


        EmployeeDao employeeDao  = ctx.getBean("jpaEmployee",EmployeeDao.class);


        aSS.getStatus().put("case",c);

        try {

            long employeeId = requestJ.getLong("id");

            if(employeeDao.existByShopIdAndId(shopId,employeeId)){


                empolyeeManager.savePhoto(filePart, shopId, employeeId);

                aSS.getStatus().put("error", "ok");

            }else {

                aSS.getStatus().put("error","Сотрудника несуществует");

            }

        }catch (NumberFormatException e){
            aSS.getStatus().put("error","некорректные данные");
        } catch (Exception e) {
            aSS.getStatus().put("error","ошибка записи файла");
            e.printStackTrace();
        }

        aSS.finalizeShop(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

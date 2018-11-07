package servlets.s.catalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.NewShopServletService;
import servlets.ShopServletService;
import spring.entity.EntityCategoryService;
import spring.entity.EntityCategoryShop;
import spring.interfaces.CategoryServiceDao;
import spring.interfaces.CategoryShopDao;
import utils.FilesUtil;
import utils.HtmlLoader;
import utils.MultipartUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SaveCategory",
            urlPatterns = "/save.category")
@MultipartConfig
public class SaveCategory extends HttpServlet {

    private String c = "save_cat";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        NewShopServletService sSS = new NewShopServletService(ctx);
        sSS.setRequestJ(new JSONObject(request.getParameter("request")));

        if(!sSS.getRequestJ().has("employeeId")){
            sSS.getRequestJ().put("employeeId",sSS.getRequestJ().get("employee_id"));
        }

        String ipNode = System.getProperty("ip.node");

        String uri = "http://" + ipNode + ":8102/catalog/category";

        MultipartUtility multipartUtility = new MultipartUtility(uri, "UTF-8");
        multipartUtility.addFormField("request", sSS.getRequestJ().toString());
        List<String> res = multipartUtility.finish();

        Part filePart = request.getPart("image");
        String filePath = System.getProperty("upload.dir") + "/categories/";
        JSONObject resJ = new JSONObject(res.get(0));
        JSONObject catJ = resJ.getJSONObject("catalog");
        long catId;
        if(catJ.has("changeCategory")){


            catId = catJ.getJSONObject("changeCategory").getLong("id");
            catJ.getJSONObject("changeCategory").put("image","http://94.255.72.28:8080/ice/categories/"+catId+".jpg");
        }else{
            catId = catJ.getJSONObject("newCategory").getLong("id");
            catJ.getJSONObject("newCategory").put("image","http://94.255.72.28:8080/ice/categories/"+catId+".jpg");
        }

        if (filePart != null) {

            FilesUtil.setImageName(String.valueOf(catId));
            FilesUtil.saveLogo(filePart, filePath);

        }
        System.out.println(res);
        response.getWriter().print(resJ.toString());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }

    public static boolean hasParent(long shopId, long parent, CategoryShopDao dao , EntityCategoryShop categoryShop){


        if(parent == 1) {

            categoryShop.setParent(1);

        }else{

            if(dao.existsParent(shopId,parent)){

                categoryShop.setParent(parent);

            }else {
                return false;
            }
        }

        return true;
    }
}

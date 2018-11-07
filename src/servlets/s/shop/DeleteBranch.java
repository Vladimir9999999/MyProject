package servlets.s.shop;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import spring.entity.EntityBranch;
import spring.interfaces.BranchDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteBranch"
        ,urlPatterns = "/delete.branch")
public class DeleteBranch extends HttpServlet {

    String c = "del_bran";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        long id;
        String tocken;

        if (sSS.initializeShop(request, response)) {

            tocken = sSS.getRequestJ().getString("tocken");
            sSS.getStatus().put("case", c);
        } else {
            return;
        }

        if (sSS.getRequestJ().has("id")) {

            id = sSS.getRequestJ().getLong("id");

        } else {
            sSS.getStatus().put("error", "Отсутствует id");
            sSS.finalize(response);
            return;
        }

        BranchDao branchDao = ctx.getBean("jpaBranch", BranchDao.class);

        if (!branchDao.existsById(id)) {
            sSS.getStatus().put("error", "Branch несуществует");
            sSS.finalize(response);
            return;
        }

        EntityBranch branch = branchDao.findById(id);

        branch.setShop(null);

        branchDao.save(branch);

        branchDao.deleteAllByShop(null);

        sSS.getStatus().put("error", "ok");

        sSS.finalize(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}

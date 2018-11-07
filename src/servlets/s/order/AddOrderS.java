package servlets.s.order;

import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.NewShopServletService;
import servlets.ShopServletService;
import utils.OrdersService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddOrderS",
urlPatterns = "/add.order")

public class AddOrderS extends HttpServlet {

    private String c = "add_ordr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long userId = 0;

        String tocken;
        long employeeId;
        int length;

        WebApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        NewShopServletService aSS = new  NewShopServletService(ctx);

        double summ;
        long orderId=0;

        if (aSS.initializeShop(request, response)) {

            aSS.getStatus().put("case", c);

        } else {

            return;

        }

        OrdersService ordersService = new OrdersService(ctx);
       JSONObject newOrder =  ordersService.addOrderShop(aSS.getRequestJ());
        if(newOrder != null) {

            aSS.addPropertyObject("orders",newOrder);

            aSS.getStatus().put("error", "ok");
        }else {
            aSS.getStatus().put("error",ordersService.getErrorMessage());
        }

        aSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }

}

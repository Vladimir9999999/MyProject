package servlets.s.order;

import Models.QrCode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import utils.QRManager;
import utils.QrHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddClientOrder",
urlPatterns = "/add.client.order")
public class AddClientOrder extends HttpServlet {

    private final String c = "add_cl_ordr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String hash;
        long orderId;
        long shopId;
        String tocken;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);


        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);

            hash = sSS.getRequestJ().getString("hash");
            orderId = sSS.getRequestJ().getLong("order_id");
            shopId = sSS.getRequestJ().getLong("shop_id");
        } else {

            return;

        }

        QrCode code = new QrCode();

        code.setHash(hash);
        code = QRManager.getQr(code);

        if (code != null) {


            if (code.getType() == QrCode.USER) {

                QrHandler qrHandler = new QrHandler(code, ctx);

                sSS.getStatus().put("error", qrHandler.addUserToOrder(shopId,orderId));

            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

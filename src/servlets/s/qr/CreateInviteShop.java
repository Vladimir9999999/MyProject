package servlets.s.qr;

import Models.QrCode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servlets.ShopServletService;
import utils.Hasher;
import utils.QRManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "craete_qr_shop",
            urlPatterns = "/create.invite")

public class CreateInviteShop extends HttpServlet {

    private String c = "cr_qr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long shopId;

        String tocken;
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {

            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");

        } else {
            return;
        }


        Hasher hasher = new Hasher();

        String hash = hasher.generateHash(shopId);
        QrCode qr = new QrCode();


        qr.setHost(shopId);
        qr.setHash(hash);
        qr.setDate(new Date());
        qr.setType(QrCode.SERVICE);

        QRManager.addQr(qr);

        sSS.getStatus().put("error", "ok");
        sSS.getBody().put("hash", hash);
        sSS.finalize(response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }

}
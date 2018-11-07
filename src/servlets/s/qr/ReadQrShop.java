package servlets.s.qr;

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

@WebServlet(name = "read_qr_shop",
        urlPatterns = "/read.qr")

public class ReadQrShop extends HttpServlet {

    String c = "rd_qr";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        long shopId;
        String tocken;
        String hash;

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        ShopServletService sSS = new ShopServletService(ctx);

        if (sSS.initializeShop(request, response)) {
            sSS.getStatus().put("case", c);
            shopId = sSS.getRequestJ().getLong("shop_id");
            tocken = sSS.getRequestJ().getString("tocken");
            hash = sSS.getRequestJ().getString("hash");
        } else {

            return;

        }

        QrCode code = new QrCode();

        code.setHash(hash);
        code  = QRManager.getQr(code);

        if (code!=null) {


                if(code.getType()==QrCode.USER) {

                    QrHandler qrHandler = new QrHandler(code, ctx);
                    sSS.getStatus().put("error",qrHandler.readService(shopId));


                }
        }
        sSS.finalizeShop(response);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
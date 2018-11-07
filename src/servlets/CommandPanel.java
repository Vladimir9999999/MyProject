package servlets;

import Models.Message;
import Models.QrCode;
import constant.СonstantMessage;
import messagers.MessagerFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import spring.entity.EntitySession;
import spring.interfaces.SessionDao;
import utils.OrdersService;
import utils.QRManager;
import utils.ServersActual;
import utils.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CommandPanel"
           ,urlPatterns = "/command")

public class  CommandPanel extends HttpServlet {

    private final String INV_SERIAL_FILE = System.getProperty("jboss.modules.dir")+"/invites.az";

    private final String ADD_SERVER_SHOP = "addS";
    private final String ADD_SERVER_USER = "addU";

    private final String DELETE_ALL_ORDER = "delO";

    private final String DELETE_SERVER_SHOP = "delS";
    private final String DELETE_SERVER_USER = "delU";

    private final String SERIALISE_INVITES = "srInv";
    private final String UNSERIAL_INVITES = "unsInv";
    private final String CREATE_INVITE = "crInv";
    private final String PRINT_INVITE = "prtInv";
    private final String CLEAR_SESSIONS = "clr";
    private final String STATISTICS = "stt";
    private final String REBOOT = "rbt";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        System.out.println("AHTUNG" +ctx.getServletContext().getContextPath());
        final String admPassword = "340 обезьян в попу сунули банан";

        String path = System.getProperty("user.dir") + "/bin/reboot.sh";

        JSONObject requestJ = new JSONObject(request.getParameter("request"));

        JSONObject status = new JSONObject();

        JSONObject responseJ = new JSONObject();

        if(!requestJ.getString("password").equals(admPassword)){

            status.put("error","ok");

            System.out.println("нас ебут чувак!!!");

            response.getWriter().print(status);

            return;
        }

        switch (requestJ.getString("c")) {

            case CLEAR_SESSIONS:

                SessionManager.runClear();

                break;
            case DELETE_ALL_ORDER:

                OrdersService ordersService = new OrdersService(ctx);
                ordersService.deleteAllOrder();

                break ;

            case SERIALISE_INVITES:

                FileOutputStream fos = new FileOutputStream(INV_SERIAL_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(QRManager.getListQrCode());
                oos.flush();
                oos.close();

                fos.close();
                break;
            case UNSERIAL_INVITES:

                FileInputStream fis = new FileInputStream(INV_SERIAL_FILE);

                try (ObjectInputStream oin = new ObjectInputStream(fis)){

                    List<QrCode> codes  = (List<QrCode>) oin.readObject();

                    for (QrCode code: codes){
                        if(code.getHash().equals("87338733")){
                            continue;
                        }
                        QRManager.addQr(code);

                    }

                } catch (ClassNotFoundException e) {

                    e.printStackTrace();

                }
                break;
            case CREATE_INVITE:

                if (!requestJ.has("hash")) {

                    status.put("error", "Отсутствует hash");

                    return;
                }
                String hash = requestJ.getString("hash");

                QrCode qrCode = new QrCode();

                qrCode.setHost(0);
                qrCode.setHash(hash);
                qrCode.setType(QrCode.USER);
                qrCode.setDate(new Date());
                QRManager.addQr(qrCode);
                break;

            case PRINT_INVITE:
                List<QrCode> codes = QRManager.getListQrCode();

                status.put("qrCodes", new JSONArray(codes));

                break;

            case ADD_SERVER_SHOP:

                ServersActual.addServerS(requestJ.getString("server"));
                break;


            case DELETE_SERVER_SHOP:
                ServersActual.deleteServerS(requestJ.getString("server"));
                break;


            case REBOOT:
                //todo сделать кроссплатформенное решение
                String[] cmd = {"sh", path, requestJ.getString("srvr")};

                StringBuilder o = new StringBuilder();
                Process p = Runtime.getRuntime().exec(cmd);
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                String s;
                try {

                    while ((s = stdInput.readLine()) != null) {
                        o.append(s).append("\n");
                    }
                    s = null;
                    o.append("error:");
                    while ((s = stdError.readLine()) != null) {
                        o.append(s).append("\n");
                    }

                } catch (Exception e) {
                    response.getWriter().print(e.toString());
                } finally {
                    stdInput.close();
                    stdError.close();
                }

                status.put("console", new String(o));
                break;

        }

        status.put("error", "ok");
        responseJ.put("status",status);

        response.getWriter().print(responseJ);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);

    }
}

package servlets;

import Models.Session;
import interfaces.ServletService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import spring.entity.EntityEmployee;
import spring.interfaces.EmployeeDao;
import utils.CodeResponse;
import utils.MultipartUtility;
import utils.ServersActual;
import utils.SessionManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShopServletService extends ServletService  {

    public final static long SHOP_ID_ICE = 1414;
    public final static    long DEFAULT_EMPLOYEE = 1414;
    public static int roadTime = 0;

    public static long count = 0;
    private WebApplicationContext ctx;
    private long id;

    public ShopServletService(WebApplicationContext ctx) {
        this.ctx = ctx;
        count ++;
    }


    public boolean initializeShop(HttpServletRequest request, HttpServletResponse response) throws IOException {

        requestJ = requestToJSON(request);

        resp = new JSONObject();

        if (!requestJ.has("employee_id")) {

            status.put("error", "отсутствует employee_id");
            status.put("code",CodeResponse.AUTHENTICATION_ERROR);
            finalize(response);
            return false;

        }

        if (!requestJ.has("tocken")) {

            status.put("error", "отсутствует tocken");
            status.put("code",CodeResponse.AUTHENTICATION_ERROR);
            finalize(response);
            return false;
        }

        id= requestJ.getLong("employee_id");
        status = new JSONObject();
        body  = new JSONObject();




        return auth(request,response);
    }


    @Deprecated
    public void finalizeShop (HttpServletResponse response) throws IOException {

        finalize(response);

    }

    @Override
    public boolean initialize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return initializeShop(request, response);
    }
    @Override
    public  JSONArray badServers(JSONArray badServers){

        System.out.println("BAD_SERVERS:");
        System.out.println(badServers);

        for(int i = 0; i < badServers.length(); i++){

            ServersActual.changePriorityS(badServers.getString(i));

        }

        return new JSONArray(ServersActual.serversS);

    }

    @Override
    protected boolean auth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Session session = new Session();


        session.setUserAgent(request.getHeader("User-Agent"));



        if(requestJ.has("employee_id")) {

            try {

                session.setUserId(requestJ.getLong("employee_id"));

            }catch (JSONException e){

                status.put("error", "Ошибка авторизации");
                status.put("code",CodeResponse.AUTHENTICATION_ERROR);
                finalize(response);
                return false;
            }
            session.setToken(requestJ.getString("tocken"));

            if (!SessionManager.searshSession(session, ctx)) {

                status.put("error", "Ошибка авторизации");
                status.put("code",CodeResponse.AUTHENTICATION_ERROR);
                finalize(response);
                return false;

            }else {

                EmployeeDao employeeDao = ctx.getBean("jpaEmployee", EmployeeDao.class);

                EntityEmployee entityEmployee = employeeDao.selectById(session.getUserId());

                requestJ.put("shop_id",entityEmployee.getShopId());

                long shopId = entityEmployee.getShopId();

                String shopIdS = String.valueOf(shopId);

                String urlS = request.getRequestURI();

                String requestS = request.getParameter("request");

                String url = urlS + requestS;

                String ping = "0";
                String responseS = "response";

                String ipNode = System.getProperty("ip.node");

                String uri = "http://" + ipNode + ":8104/log/save";
                try {
                    MultipartUtility multipartUtility = new MultipartUtility(uri, "UTF-8");

                    multipartUtility.addFormField("url", url);
                    multipartUtility.addFormField("shopId", shopIdS);
                    multipartUtility.addFormField("ping", ping);
                    multipartUtility.addFormField("response", responseS);

                    multipartUtility.finish();
                }catch (Exception ignored){

                }
                return true;
            }

        }else{
            status.put("error","Провал авторизации");
            status.put("code",CodeResponse.AUTHENTICATION_ERROR);

        }
        return false;
    }

    @Override
    public long getId() {
        return id;
    }
}

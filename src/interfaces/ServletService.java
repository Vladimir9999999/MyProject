package interfaces;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import servlets.ShopServletService;
import servlets.s.message.my_service.MessageServletService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ServletService {

    protected JSONObject status = new JSONObject();
    protected JSONObject body = new JSONObject();
    protected JSONObject resp = new JSONObject();
    protected JSONObject requestJ;
    private JSONArray messagesJ;
    protected WebApplicationContext ctx;
    protected HttpServletRequest request;
    public static ServletService getServletService(HttpServletRequest request, WebApplicationContext ctx) {
        JSONObject req = requestToJSON(request);

        if (req.has("employee_id")) {

            return new ShopServletService(ctx);

        }

        return null;
    }

    protected static JSONObject requestToJSON(HttpServletRequest request) {
        return new JSONObject(request.getParameter("request"));
    }

    public void setMessagesJ(JSONArray messagesJ) {
        this.messagesJ = messagesJ;
    }

    public void finalize(HttpServletResponse response) throws IOException {


        if (requestJ.has("bad_servers")) {

            resp.put("new_servers", badServers(requestJ.getJSONArray("bad_servers")));
        }

        status.put("server", System.getProperty("jboss.server.name"));
        resp.put("jsonBody", body);
        resp.put("status", status);

        response.getWriter().print(resp);
        System.out.println("RESPONSE: " + resp+"HASH:");

        status = new JSONObject();
        body = new JSONObject();
        resp = new JSONObject();
        requestJ = new JSONObject();

    }

    public abstract JSONArray badServers(JSONArray badServers);


    protected boolean auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return false;
    }

    public JSONObject getStatus() {
        return status;
    }

    public void setStatus(JSONObject status) {
        this.status = status;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public JSONObject getResp() {
        return resp;
    }

    public void setResp(JSONObject resp) {
        this.resp = resp;
    }

    public JSONObject getRequestJ() {
        return requestJ;
    }

    public void setRequestJ(JSONObject requestJ) {
        this.requestJ = requestJ;
    }

    public abstract boolean initialize(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public abstract long getId();
}

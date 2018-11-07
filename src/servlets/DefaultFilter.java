package servlets;

import org.json.JSONException;
import org.json.JSONObject;
import utils.CodeResponse;
import utils.ServersActual;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "DefaultFilter",

urlPatterns = "/*")
public class DefaultFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        HttpServletRequest request = (HttpServletRequest) req;

        String URI = request.getRequestURI();
        resp.setContentType("application/json");
        JSONObject status = new JSONObject();
        String reqS = request.getParameter("request");



        if (reqS != null) {
            try {


                new JSONObject(reqS);
                chain.doFilter(req, resp);

            } catch (JSONException e) {
                e.printStackTrace();
                status.put("error", "request - не JSON")
                        .put("case", URI);
                resp.getWriter().print(new JSONObject().put("status", status));
            }
        } else {

            status.put("error", "Отсутствует request")
                    .put("case", URI);

            resp.getWriter().print(new JSONObject().put("status", status));
        }

    }


    public void init(FilterConfig config) throws ServletException {

    }

}

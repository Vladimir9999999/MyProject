package servlets.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

@WebFilter(filterName = "UrlLoggerFilter",
urlPatterns = "/*")
public class UrlLoggerFilter implements Filter {
    private static boolean onOff  = true;
    private static boolean onOffUserAgentLogging  = true;

    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpServletRequest request  = (HttpServletRequest) req;
        String command = request.getParameter("logger");
        String uaLogComm = request.getParameter("log_ua");

        long start = new Date().getTime();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SESSION: ")
                .append(start).append("HASH: ").append(request.hashCode())
                .append("\n");

        if(onOffUserAgentLogging){
            stringBuilder.append("USER_AGENT: ")
                    .append(request.getHeader("User-Agent")).append("\n")
                    .append("IP_ADDRESS: ").append(request.getRemoteAddr())
                    .append("\n");
        }
        if(onOff) {

            stringBuilder.append("URL: "+request.getRequestURI()+"\n");

            Enumeration enumeration = req.getParameterNames();
            stringBuilder.append("PARAMETERS : \n");



            while (enumeration.hasMoreElements()) {
                String parameterName = (String) enumeration.nextElement();
                stringBuilder.append(parameterName)
                        .append("=")
                        .append(request.getParameter(parameterName))
                        .append("\n");
            }
            stringBuilder.append("\n");
        }

        if(command !=null){

            switch (command){

                case "on":
                    onOff=true;
                    break;
                case "off":
                    onOff= false;
                    break;

            }

        }

        if(uaLogComm !=null){
            switch (uaLogComm){
                case "on":
                    onOffUserAgentLogging = true;
                case "off":
                    onOffUserAgentLogging = false;
            }
        }

        System.out.println(new String(stringBuilder));

        chain.doFilter(req, resp);
        long timeout = new Date().getTime()-start;
        System.out.println("SESSION: "+start+" HASH: "+request.hashCode()+" TIMEOUT:"+timeout);


    }

    public void init(FilterConfig config) throws ServletException {

    }

}

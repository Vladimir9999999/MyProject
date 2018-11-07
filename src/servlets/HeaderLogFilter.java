package servlets;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

@WebFilter(urlPatterns = {"/cashe*"})
public class HeaderLogFilter implements Filter {

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response,
                          FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        System.out.println("----- Request ---------");
        Collections.list(req.getHeaderNames())
                .forEach(n -> System.out.println(n + ": " + req.getHeader(n)));



        System.out.println("----- response ---------");

        resp.getHeaderNames()
                .forEach(n -> System.out.println(n + ": " + resp.getHeader(n)));

        System.out.println("response status: " + resp.getStatus());

        long lastModifiedFromBrowser = req.getDateHeader("If-Modified-Since");
        long lastModifiedFromServer = getLastModifiedMillis();

        if (lastModifiedFromBrowser != -1 &&
                lastModifiedFromServer <= lastModifiedFromBrowser) {
            //setting 304 and returning with empty body
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        chain.doFilter(request, response);

    }

    @Override
    public void destroy () {
    }


    private static long getLastModifiedMillis () {
        //Using hard coded value, in real scenario there should be for example
        // last modified date of this servlet or of the underlying resource
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2017, 1, 8,
                10, 30, 20),
                ZoneId.of("GMT"));
        return zdt.toInstant().toEpochMilli();
    }

}
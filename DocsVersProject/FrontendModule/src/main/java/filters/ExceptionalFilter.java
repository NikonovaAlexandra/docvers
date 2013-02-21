package filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Дмитрий Юрьевич
 * Date: 17.02.13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionalFilter implements Filter {
    private String errorPage;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       errorPage = filterConfig.getInitParameter("error-page");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        try{
            filterChain.doFilter(servletRequest, servletResponse);

       }catch (Exception e){

            response.sendRedirect(errorPage);

            return;
       }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

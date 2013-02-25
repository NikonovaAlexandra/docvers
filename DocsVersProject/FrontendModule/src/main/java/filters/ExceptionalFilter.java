package filters;


import exception.BusinessException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String message;
        try {
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (ServletException e) {
            if(e.getRootCause() instanceof BusinessException){
               message = e.getCause().getClass()+"\n "+e.getMessage();
                System.out.println(message);
            }else{
               message = "An error has occured!\n "+e.getCause().getClass()+"\n "+e.getMessage()+"\n Please contact your administrator!";
            }
            request.getSession().setAttribute("message", message);
            response.sendRedirect(errorPage);
            return;
        }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

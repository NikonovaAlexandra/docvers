package filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.03.13
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class ExternalExceptionalFilter implements Filter {
    private String errorPage;
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        errorPage = filterConfig.getInitParameter("error-page");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String message;
        try {
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (ServletException e) {
            if (e.getCause() != null) {

                message = "An error has occured!\n " + e.getCause().getClass() + "\n Please contact your administrator!";
            } else {
                message = "An error has occured!\n " + "\n Please contact your administrator!";
            }
            logger.error("System Exception in application: " + message +
                    e.getMessage());
            logger.error("Servlet Exception in application: " + e.getMessage());
            request.getSession().setAttribute("message", message);

            try {
                response.sendRedirect(errorPage);
            } catch (IOException e1) {
                logger.error("IOException raised in Exceptional Filter while redirect.");
            }

        } catch (IOException e) {

            logger.error("IOException raised in Exceptional Filter.");
        }
    }

    @Override
    public void destroy() {

    }
}

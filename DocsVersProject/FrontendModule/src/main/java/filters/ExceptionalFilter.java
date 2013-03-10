package filters;


import exception.BusinessException;
import exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ConnectionPoolFactory;

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
            if (e.getRootCause() instanceof BusinessException) {

                message = e.getCause().getClass() + "\n " + e.getMessage();
                logger.error("Business Exception in application: " + message);
            } else if (e.getRootCause() instanceof SystemException) {
                message = "An error has occured!\n " + e.getCause().getClass() + "\n " +
                        e.getMessage() + "\n Please contact your administrator!";
                logger.error("System Exception in application: " + message);
            } else {
                message = "Servlet Exception in application: " + e.getMessage();
                logger.error("Servlet Exception in application: " + e.getMessage());
            }
            request.getSession().setAttribute("message", message);

            try {
                response.sendRedirect(errorPage);
            } catch (IOException e1) {
                logger.error("IOException raised in Exceptional Filter while redirect.");
            }
            return;
        } catch (IOException e) {

            logger.error("IOException raised in Exceptional Filter.");
        }
    }

    @Override
    public void destroy() {
        // todo: close connections somewhere else
        try {
            ConnectionPoolFactory.getInstance().getConnectionPool().closeAllConnections();
        } catch (SystemException e) {
            logger.error("System Exception while destroying Exceptional Filter.");
        } catch (BusinessException e) {
            logger.error("Business Exception while destroying Exceptional Filter.");
        }
    }
}
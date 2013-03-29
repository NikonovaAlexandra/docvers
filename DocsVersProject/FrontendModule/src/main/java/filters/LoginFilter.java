package filters;

import beans.AuthorBean;
import exception.BusinessException;
import exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.SessionFactoryUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 24.02.13
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public class LoginFilter implements Filter {
    private FilterConfig filterConfig;
    private HttpServletRequest request;
    private String loginPage;
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            loginPage = filterConfig.getServletContext().getInitParameter("login_page");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            boolean authorized = false;
            if (request instanceof HttpServletRequest) {
                if (isResourceRequest((HttpServletRequest) request) || ((HttpServletRequest) request).getRequestURI().equals("/Download")) {
                    authorized = true;
                } else {
                    HttpSession session = ((HttpServletRequest) request).getSession(false);
                    if (session != null) {
                        AuthorBean token = (AuthorBean) session.getAttribute("user");
                        String qryStr = ((HttpServletRequest) request).getQueryString();
                        if ((token != null) || "cLogin".equalsIgnoreCase(qryStr)) {
                            authorized = true;
                        }
                    }
                }
            }

            if (authorized) {
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                chain.doFilter(request, response);
            } else if (filterConfig != null) {
                if (loginPage != null && !"".equals(loginPage)) {
                    filterConfig.getServletContext().getRequestDispatcher(loginPage).forward(request, response);
                }
            } else {
                throw new ServletException("Unauthorized access, unable to forward to login page");
            }
        } catch (ServletException e) {
            if (e.getRootCause() instanceof BusinessException) {
                logger.error("Business Exception in Login Filter: " + e.getRootCause().getClass() + " " + e.getMessage());
            } else if (e.getRootCause() instanceof SystemException) {
                logger.error("System Exception in Login Filter: " + e.getRootCause().getClass() + " " + e.getMessage());
            } else {
                logger.error("Servlet Exception in Login Filter: " + e.getMessage());
            }
         } catch (IOException e) {
            logger.error("IOException raised in Login Filter. " + e.getMessage());

        }

    }

    private boolean isResourceRequest(HttpServletRequest request) {
        if (request.getRequestURI().matches("(.*)(/[js/|css/|images/])(.*)")) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}

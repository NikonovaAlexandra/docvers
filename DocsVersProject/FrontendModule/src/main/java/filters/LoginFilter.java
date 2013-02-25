package filters;

import beans.AuthorBean;

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
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.print("Authentication: Request received ...");
            try {
                boolean authorized = false;
                if (request instanceof HttpServletRequest) {
                    HttpSession session = ((HttpServletRequest)request).getSession(false);
                    if (session != null) {
                        AuthorBean token = (AuthorBean) session.getAttribute("user");
                        //session.removeAttribute("user");
                        String qryStr = ((HttpServletRequest)request).getQueryString();
                        System.out.println(qryStr);
                        if((token != null) || "cLogin".equalsIgnoreCase(qryStr)){
                            authorized = true;
                            ((HttpServletRequest) request).getSession().removeAttribute("logmessage");
                        }


                    }
                }

                if (authorized) {
                    chain.doFilter(request, response);
                } else if (filterConfig != null) {
                    ServletContext context = filterConfig.getServletContext();
                    String login_page = context.getInitParameter("login_page");
                    System.out.print("Authentication: Login page = " + login_page);
                    if (login_page != null && !"".equals(login_page)) {
                        context.getRequestDispatcher(login_page).forward(request, response);
                    }
                } else {
                    throw new ServletException ("Unauthorized access, unable to forward to login page");
                }
            } catch (IOException io) {
                System.out.println("IOException raised in AuthenticationFilter");
            } catch (ServletException se) {
                System.out.println("ServletException raised in AuthenticationFilter");
            }
            System.out.print(" Authentication: Response dispatched ...");
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

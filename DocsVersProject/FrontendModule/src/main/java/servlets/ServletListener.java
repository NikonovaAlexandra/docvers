package servlets;

import dao.DAOType;
import db.AllScriptSInDirectoryRunner;
import exception.BusinessException;
import exception.NullFileException;
import exception.SystemException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Config;
import service.ConnectionPoolFactory;
import service.SessionFactoryUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.03.13
 * Time: 9:36
 * To change this template use File | Settings | File Templates.
 */
public class ServletListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(ServletListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        logger.trace("Initializing context...");
        try {
            String configPath = System.getenv("docsvers.config.properties");
            if (configPath == null)
                throw new NullFileException("File not found.");
            Properties properties = new Properties();
            properties.load(new FileInputStream(configPath));

            Config.init(properties);

            DAOType type = Config.getConnType();
            sc.setAttribute("type", type);
            String filePath = Config.getUploadPath();
            sc.setAttribute("filePath", filePath);
            String encoding = event.getServletContext().getInitParameter("encoding");
            sc.setAttribute("encoding", encoding);
            String path = Config.getHibernateConfPath();
            if (!type.equals(DAOType.JDBC)) {
                SessionFactoryUtil.init(path);
            }
            try {

                if (Config.isInMemory()) {
                    AllScriptSInDirectoryRunner.getInstance(false).run();
                } else {
                    AllScriptSInDirectoryRunner.getInstance(true).run();
                }
                ConnectionPoolFactory.init();

            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
            }
        } catch (Exception e1) {
            logger.error(e1.getLocalizedMessage());
        }


}

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        try {
            ConnectionPoolFactory.getInstance().getConnectionPool().closeAllConnections();
            SessionFactory sf = SessionFactoryUtil.getInstance().getSessionFactory();
            if (!sf.isClosed()) sf.close();
            logger.trace("Destroying context...");
        } catch (SystemException e) {
            logger.error("System Exception while destroying Exceptional Filter.");
        } catch (BusinessException e) {
            logger.error("Business Exception while destroying Exceptional Filter.");
        }
    }
}

package session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.02.13
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class UserSession implements HttpSession {

    @Override
    public long getCreationTime() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getLastAccessedTime() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ServletContext getServletContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getAttribute(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getValue(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getValueNames() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAttribute(String name, Object value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putValue(String name, Object value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeAttribute(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeValue(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void invalidate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isNew() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

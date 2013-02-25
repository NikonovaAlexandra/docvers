package util;

import beans.AuthorBean;
import entities.Author;
import exception.BusinessException;
import exception.SystemException;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 25.02.13
 * Time: 0:06
 * To change this template use File | Settings | File Templates.
 */
public class Authentication {
    public static AuthorBean authenticate(Author author, String login, String password) throws BusinessException, SystemException {
            if(author.getLogin().equals(login) && author.getPassword().equals(password)) {
                return new AuthorBean(login, password);
            } else return null;
    }
}

package servlets.documentServlet;

import beans.DocumentBean;
import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.ObjectAlreadyExistsException;
import exception.SystemException;
import util.DBOperations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.02.13
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class GetAllDocumentsServlet extends HttpServlet {
    private DBOperations operations = DBOperations.getInstance();

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<DocumentBean> docs = operations.getAllDocuments();
            showDocuments(docs, request, response);
        } catch (SystemException e) {
            System.out.println(e.getCause()+e.getMessage());
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                request.setAttribute("noanydocmessage","You have nor any document");
                RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllDocuments");
                reqDispatcher.forward(request, response);
            } else {
                throw new ServletException(e);
            }
        }
    }

    private void showDocuments(List<DocumentBean> docs, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("documentList", docs);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllDocuments");
        reqDispatcher.forward(request, response);

    }
}

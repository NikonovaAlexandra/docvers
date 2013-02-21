package servlets.documentServlet;

import beans.DocumentBean;
import exception.NullConnectionException;
import util.DBOperations;
import util.RequestParser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.02.13
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class AddDocumentServlet extends HttpServlet {
    private RequestParser parser = RequestParser.getInstance();
    private DBOperations operations = DBOperations.getInstance();

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException   {
        DocumentBean documentBean = null;
        try {
            documentBean = parser.getDocumentBean(request);
            operations.addDocument(documentBean);
            showSuccessfulAdditionPage(documentBean, request, response);
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (NullConnectionException e) {
            e.printStackTrace();
        }

    }

    private void showSuccessfulAdditionPage(DocumentBean doc, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "Document \"" + doc.getName()+"\" was added successfully";
        request.setAttribute("message", message);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AddDocument");
        reqDispatcher.forward(request, response);

    }


}

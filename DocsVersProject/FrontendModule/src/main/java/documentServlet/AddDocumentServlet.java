package documentServlet;

import dao.author.AuthorDAO;
import dao.author.AuthorDAOImpl;
import dao.document.DocumentDAO;
import dao.document.DocumentDAOImpl;
import db.Outer;
import entities.Author;
import entities.Document;
import exception.NullConnectionException;
import util.ConnectionGetter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.02.13
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class AddDocumentServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            addDocument(request);
            successfulAddition(request, response);
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (NullConnectionException e) {
            e.printStackTrace();
        }

    }

    private void successfulAddition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "Document was added successfully";
        request.setAttribute("message", message);
        request.getRequestDispatcher("/AddDocument.jsp").forward(request, response);

    }

    private void addDocument(HttpServletRequest request) throws SQLException, NullConnectionException {
        Connection conn = null;
        try{
            String login = getLogin();
            String name = request.getParameter("docname");
            String description = request.getParameter("docdescription");
            conn = ConnectionGetter.getConnection();
            AuthorDAO dao = new AuthorDAOImpl(conn);
            Author author = dao.getAuthorByLogin(login);
            Document doc = new Document(author.getId(), name,description);
            DocumentDAO documentDAO = new DocumentDAOImpl(conn);
            documentDAO.addDocument(doc);
            List<Document> docs = documentDAO.getAllDocuments();
            for(Document d: docs) {
                System.out.println(d.getId()+" "+d.getAuthorID()+" "+d.getName());
            }
        }finally{
        conn.close();
        }

    }

    public String getLogin() {
        return "login";
    }
}

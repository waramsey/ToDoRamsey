
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateNewList
 */
@WebServlet("/CreateNewList")
public class CreateNewList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String listName = request.getParameter("newList");
		listName = listName.replaceAll("\\s+", "");
		
		Connection connection = null;
		String makeTable = " CREATE TABLE " + listName + " (\n" + 
				"  id INT NOT NULL AUTO_INCREMENT,\n" + 
				"  taskName VARCHAR(30) NOT NULL,\n" + 
				"  completed BOOLEAN NOT NULL,\n" + 
				"  PRIMARY KEY (id));";
	
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			PreparedStatement preparedStmt = connection.prepareStatement(makeTable);
			System.out.print(preparedStmt);
			preparedStmt.execute();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Responder.respond(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

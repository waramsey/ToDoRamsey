
/**
 * @file Insert.java
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteList")
public class DeleteList extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public DeleteList() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String listName = request.getParameter("listName");

	   //Check if listName is a valid list
	   Connection connection = null;

	   try {
		   DBConnection.getDBConnection();
	       connection = DBConnection.connection;
	       DatabaseMetaData md = connection.getMetaData();
		   ResultSet rs = md.getTables(null, null, "%", null);
		   boolean found = false;
		   while (rs.next()) {
			   if (rs.getString(3).equals(listName)) {
				   found = true;
			   }
		   }
	       
	       connection.close();
		   
		   if (!found) {
			   return;
		   }
	   } catch (Exception e) {
	         e.printStackTrace();
	   }
	   
      String deleteSql = " DROP TABLE " + listName;

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(deleteSql);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      Responder.respond(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}

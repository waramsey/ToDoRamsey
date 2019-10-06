import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Responder {

	
	
	
	
	public static void respond(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
	    PrintWriter out;
		try {
			out = response.getWriter();
			String title = "Todo List";
		    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		    out.println(docType + //
		            "<html>\n" + //
		            "<head><title>" + title + "</title></head>\n" + //
		            "<body>\n" + //
		            "<h1 class=\"title\">" + title + "</h1>\n" + //
			    "<div class=\"all-tasks\"></div>\n" + //
			    "<h2>My Lists</h2>\n" + //
			    
			    "<ul>" + getAllLists(request) +"</ul>\n" + //		TODO	just noting that this is a change
			    
			    "<form action=\"CreateNewList\" method=\"post\">\n" + //
			    "<input type=\"text\" name=\"newList\" placeholder=\"new list name\" aria-label=\"new list name\"/>\n" + //
			    "<button aria-label=\"create new list\">+</button></form></div>\n" + //
			    
			    "<div><div><h2>YouTube</h2></div>\n" + //			TODO	THE NAME IN THIS H2 SHOULD BE VARIABLE
			    
			    "<div><div>" + //
			    
			    //TODO		THIS ENTIRE CHUNK SHOULD BE VARIABLE (TASK NAMES)
			    "<div><input type=\"checkbox\" id=\"task-1\"/>\n" + //
			    "<label for=\"task-1\"><span class=\"custom-checkbox\"></span>record todo list video</label></div>" + //
			    

			    "</div><div><form id=\"Insert\" action=\"Insert\"><p>Add</p>\n" + //
			    "<input type=\"text\" name=\"newTask\" placeholder=\"new task name\" aria-label=\"new task name\"/>\n" + //
			    "<p>to</p><input type=\"text\" name=\"listName\" placeholder=\"list name\" aria-label=\"list name\"/>\n" + //
			    "<button aria-label=\"create new task\">+</button></form></div>\n" + //
			    "<div><form id=\"clearTasks\" action=\"clearTasks\"><button>Clear completed tasks></button></form>\n" + //
			    "<form id=\"DeleteList\" action=\"DeleteList\">\n" + //
			    "<input type=\"text\" name=\"listName\" placeholder=\"list name\" aria-label=\"list name\"/>\n" + //
			    "<button>Delete list</button>\n" + //
			    "</form></div></div></div></body></html>\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves all of the tables in the database and puts them
	 * in a string of list elements for respond().
	 * @param request - the servlet request
	 * @return append - the string of list elements
	 */
	private static String getAllLists(HttpServletRequest request) {
		   Connection connection = null;

		   try {
			   DBConnection.getDBConnection();
		       connection = DBConnection.connection;
		       DatabaseMetaData md = connection.getMetaData();
			   ResultSet rs = md.getTables(null, null, "%", null);
			   
			   String append = "";
			   while (rs.next()) {
				   append += "<li>" + rs.getString("TABLE_NAME") + "</li>"; //TODO resolve issue with getting more than just tables' names.
			   }
		       
		       connection.close();			   
			   
			   return append;
		   } catch (Exception e) {
		         e.printStackTrace();
		   }
		return null;
	}
}

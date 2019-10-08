import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Responder {

	
	private static String currentList;

	
	protected static void respond(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");		
		
	    PrintWriter out;
	    
	    if (currentList == null || currentList.contentEquals("Select A List!")) {
	    	currentList = "Select A List!";
	    }
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
			    
				"<ul><form id=\"SelectTable\" action=\"SelectTable\" method=\"get\">" + getAllLists(request) +"</ul>\n" + //		TODO	just noting that this is a change
			    "<input type=\"text\" name=\"pickList\" placeholder=\"Pick A List\" aria-label=\"list name\"/>\n" + //
			    "<button aria-label=\"pickList\">Go</button></form>" +
			    
			    "<form action=\"CreateNewList\" method=\"post\">\n" + //
			    "<input type=\"text\" name=\"newList\" placeholder=\"Add A List\" aria-label=\"Add A List\"/>\n" + //
			    "<button aria-label=\"create new list\">+</button></form></div>\n" + //
			    
			    "<div><div><h2>" + currentList + "</h2></div>\n" + //			TODO	just noting that this is a change
			    
			    "<div><div>" + TasksInTable(request) + //		TODO 	just noting that this is a change


			    "</div><div><form id=\"Insert\" action=\"Insert\"><h3>Add...</h3>\n" + //
			    "<input type=\"text\" name=\"newTask\" placeholder=\"New Task Name\" aria-label=\"New Task Name\"/>\n" + //
			    "<h3>...to...</h3><input type=\"text\" name=\"listName\" placeholder=\"List Name\" aria-label=\"List Name\"/>\n" + //
			    "<button aria-label=\"create new task\">+</button></form></div>\n" + //
			    "<div><h3>Delete</h3><form id=\"DeleteList\" action=\"DeleteList\">\n" + //
			    "<input type=\"text\" name=\"listName\" placeholder=\"Delete A List\" aria-label=\"Delete A List\"/>\n" + //
			    "<button>Delete list</button>\n" + //
			    "</form></div></div></div></body></html>\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}
	
	
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
		       PreparedStatement preparedStmt = connection.prepareStatement(
		    		   "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " + 
		    		   "WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='todoDB'");
			   ResultSet rs = preparedStmt.executeQuery();
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
	
	private static String TasksInTable(HttpServletRequest request) {
		Connection connection = null;

		   try {
			   DBConnection.getDBConnection();
		       connection = DBConnection.connection;
		       PreparedStatement preparedStmt = connection.prepareStatement("SELECT taskName FROM " + currentList);
			   System.out.print(currentList);
		       ResultSet rs = preparedStmt.executeQuery();
			   
			   String append = "";
			   while (rs.next()) {
				   append += "<div><input type=\"checkbox\" id=\"task-1\"/><label for=\"task-1\">" + rs.getString("taskName") + "</label></div>";
			   }
		       
		       connection.close();			   
			   
			   return append;
		   } catch (Exception e) {
		         e.printStackTrace();
		   }
		return "<div><p>No List Selected</p></div>";
	}
	
	public static void ChooseList(String id) {
		System.out.println("Hooray!  You got here!");
		currentList = id;
		//TODO How to update the html once this method is called?
	}

}

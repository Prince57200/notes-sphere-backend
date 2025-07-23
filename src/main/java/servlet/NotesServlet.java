package servlet;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NotesDAO;
import model.Notes;


public class NotesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public NotesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response type (JSON for APIs, HTML for web pages)
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	
	        
	        try {
	            // Get data from database
	            NotesDAO dao = new NotesDAO();
	            List<Notes> notes = dao.getAllNotes();
	            
	            // Convert to JSON (using simple concatenation for example)
	            // In real apps, use Gson/Jackson libraries
	            
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.registerModule(new JavaTimeModule()); // 💡 THIS FIXES THE ERROR
	            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 👈 key line!
	            String json = mapper.writeValueAsString(notes);

	
	            // Send the JSON response
	            response.getWriter().write(json);
	            
	            
	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("{\"error\":\"Unable to fetch notes\"}");
	            e.printStackTrace();
	        }
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		System.out.println(id);
		if(id!= null)
		{
			// Set response type (JSON for APIs, HTML for web pages)
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
           // response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setContentType("text/plain");
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			System.out.println(title);
			 try {
		        	
		            NotesDAO notesDAO = new NotesDAO();
		            System.out.println(id);
		            boolean success=notesDAO.updateNotes(id,title,description);

		            if (success) {

		                response.setStatus(HttpServletResponse.SC_OK);
		                response.getWriter().write("{\"message\":\"Note Update successfully\"}");
		            } 
		            else {
		            		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		                response.getWriter().write("{\"message\":\"soooory bhai Update nahi hua\"}");
		            }
		        } catch (Exception e) {
		            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		            response.getWriter().write("{\"error\":\"Invalid JSON\"}");
		        }
		}
		else {
			
				// Set response type (JSON for APIs, HTML for web pages)
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	           // response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
				response.setHeader("Access-Control-Allow-Headers", "Content-Type");
				response.setHeader("Access-Control-Allow-Credentials", "true");
				
				String title = request.getParameter("title");
				String description = request.getParameter("description");
				System.out.println(title);
				
				if (title == null || description == null || title.trim().isEmpty()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Title and description are required.");
					return;
				}
				
				Notes note = new Notes();
				note.setTittle(title);
				note.setDescription(description);
				NotesDAO notesDAO = new NotesDAO();
				notesDAO.saveNotes(note);
				response.setContentType("text/plain");
				response.getWriter().println("Note saved successfully.");
			
		    }
	   }
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    // Set CORS headers for preflight
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    
	    response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			System.out.print("i am here");
			response.setContentType("application/json");
//			response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
//	        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", " DELETE, OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        
	        
	        String noteId = request.getParameter("id"); // assuming ID

        try {
        	
            NotesDAO dao = new NotesDAO();
            System.out.println("error aa gayi hai=>"+noteId);
            boolean success=dao.DeleteNotes(noteId);

            if (success) {

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\":\"Note deleted successfully\"}");
            } 
            else {
            		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\":\"soooory bhai delete nahi hua\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid JSON\"}");
            System.out.println(e + "error aa gayi hai=>"+noteId);
        }
    }

}

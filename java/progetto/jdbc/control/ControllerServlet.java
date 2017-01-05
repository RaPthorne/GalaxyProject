package progetto.jdbc.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import progetto.jdbc.persistence.UploadCSV;

/**
 * Servlet implementation class ControllerServlet
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String comando = request.getParameter("comando");
		
		String stato = request.getParameter("stato");
		
		String risultato = "";
		
		if (comando.substring(0,6).equals("import")) {
			
			if (stato.equals("admin")){
			 
			try {
				risultato = Controller.importFileCommand(comando.substring(7));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
			
			}
			
			else {
				
				risultato = "Permesso negato";
				request.setAttribute("risultato",risultato);
				request.setAttribute("stato",stato);
				
				RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
				rd.forward(request, response);
				
			}
		}
		
		else if (comando.substring(0,8).equals("register")){
			
			if (stato.equals("admin")){
				
				try {
					risultato = Controller.registerUserCommand(comando.substring(9));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("risultato",risultato);
				request.setAttribute("stato",stato);
				
				RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
				rd.forward(request, response);
			 }
			

			else {
				
				risultato = "Permesso negato";
				request.setAttribute("risultato",risultato);
				request.setAttribute("stato",stato);
				
				RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
				rd.forward(request, response);
				
			}
			
			
		}
		
		else if (comando.substring(0,12).equals("searchbyname")) {
			
			try {
				risultato = Controller.searchByNameCommand(comando.substring(13));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
		}
		
		else if (comando.substring(0,13).equals("searchbyrange")) {
			
			try {
				risultato = Controller.searchByRange(comando.substring(14));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
		}
		
		else if (comando.substring(0,17).equals("searchforredshift")) {
			
			try {
				risultato = Controller.searchForRedshiftCommand(comando.substring(18));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
		}
		
		else if (comando.substring(0,10).equals("searchflux")) {
			
			try {
				risultato = Controller.searchFluxes(comando.substring(11));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
		}
		
		else if (comando.substring(0,21).equals("searchrapportfluxcont")) {
			
			try {
				risultato = Controller.searchRapportFluxCont(comando.substring(22));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
		}
		
		
		else if (comando.substring(0,17).equals("searchrapportflux")) {
			
			try {
				risultato = Controller.searchRapportFluxCommand(comando.substring(18));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
		}
		
		else if (comando.substring(0,8).equals("getstats")) {
			

			try {
				risultato = Controller.getStats(comando.substring(9));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
		}
		
		else {
			
			risultato = "Comando errato!";
			request.setAttribute("risultato",risultato);
			request.setAttribute("stato",stato);
			
			RequestDispatcher rd=request.getRequestDispatcher("inviocomandi.jsp");
			rd.forward(request, response);
		}
	}

}

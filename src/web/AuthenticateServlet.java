package web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class AuthenticateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		displayLoginForm(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("user");
		username = username != null ? username : "";
		String password = request.getParameter("password");
		password = password != null ? password : "";
		
		if (authenticate(username, password)) {
			response.sendRedirect(response.encodeRedirectURL("WelcomeServlet"));
		} else {
			displayErrorMessage(request, response);
		}
	}

	private void displayLoginForm(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		
		request.getRequestDispatcher("/WEB-INF/templates/header").include(request, response);
		request.getRequestDispatcher("/WEB-INF/templates/container-div").include(request, response);
		request.getRequestDispatcher("/WEB-INF/templates/login-form").include(request, response);
		request.getRequestDispatcher("/WEB-INF/templates/footer").include(request, response);
	}
	
	private void displayErrorMessage(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		
		request.getRequestDispatcher("/WEB-INF/templates/header").include(request, response);
		request.getRequestDispatcher("/WEB-INF/templates/container-div").include(request, response);
		request.getRequestDispatcher("/WEB-INF/templates/error-message").include(request, response);
		request.getRequestDispatcher("/WEB-INF/templates/footer").include(request, response);
	}

	private boolean authenticate(String username, String password) {
		try {
			ServletContext context = getServletContext();
			Map<String, String> users = (Map<String, String>) context.getAttribute("auth");
			
			return (users != null) && users.containsKey(username) && (users.get(username).compareTo(encode(password)) == 0);
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	private String encode(String text) {
		try {
			return Base64.encode(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}

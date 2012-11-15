package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public WelcomeServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (isDocumentRequested(request)) {
			String fileUrl = request.getParameter(getDocumentParameterName());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(fileUrl);
			requestDispatcher.include(request, response);
		} else {
			request.getRequestDispatcher("/DocumentListerServlet").forward(request, response);
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
	private boolean isDocumentRequested(ServletRequest request) {
		String documentParameterName = getServletContext().getInitParameter("documentParameterName");
		return (null != request.getParameter(documentParameterName));
	}
	
	private String getDocumentParameterName() {
		return getServletContext().getInitParameter("documentParameterName");
	}
}










package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DocumentListerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		printHtmlHeader(response.getWriter());
		printFileList(response.getWriter());
		printHtmlFooter(response.getWriter());
	}

	private void printHtmlHeader(PrintWriter printWriter)
	{
		printWriter.println(
				"<!DOCTYPE html> \n" +
				"<html> \n" +
				"<head> \n" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> \n" +
				"<title>Filter</title> \n" +
				"</head> \n" +
				"<body>"
		);
	}
	
	private void printHtmlFooter(PrintWriter printWriter)
	{
		printWriter.println(
				"</body> \n" +
				"</html>"
		);
	}
	
	private void printFileList(PrintWriter printWriter) {
		Set<String> fileNames = getServletContext().getResourcePaths("/WEB-INF/docs");
		printWriter.println("<h4>Documents</h4>");
		printWriter.println("<ul>");
		for (String fileName : fileNames) {
			String url = "/filter/WelcomeServlet?doc=" + encodeUrl(fileName);
			printWriter.println("<li>" + anchor(fileName, url) + "</li>");
		}
		printWriter.println("</ul>");
	}
	
	private String encodeUrl(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	private String anchor(String text, String url) {
		return "<a href=\"" + url + "\">" + text + "</a>";
	}
}

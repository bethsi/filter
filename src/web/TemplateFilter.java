package web;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class TemplateFilter implements Filter {
	private static ServletContext context;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		ByteResponseWrapper brw = new ByteResponseWrapper((HttpServletResponse) response);
		
		chain.doFilter(request, brw);
		
		String template = new String(brw.getBytes(), Charset.forName("UTF-8"));
		template = template.replaceAll("@CONTEXTPATH@", context.getContextPath());
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(template);
	}

	@Override
	public void destroy() {
	}
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		if (context == null) {
			context = fConfig.getServletContext();
		}
	}

}

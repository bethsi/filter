package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ModelListener implements ServletContextListener {
    @Override
	public void contextInitialized(ServletContextEvent event) {
    	String filename = "/WEB-INF/auth/passwords";
    	ServletContext context = event.getServletContext();
    	
    	InputStreamReader inputStreamReader = new InputStreamReader(context.getResourceAsStream(filename));
    	BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
    	
    	try {
    		context.setAttribute("auth", getAuthMap(bufferedReader));
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			bufferedReader.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }
    
    private Map<String, String> getAuthMap(BufferedReader source) throws IOException {
    	Map<String, String> result = new HashMap<String, String>();
    	
    	String line;
    	while ((line = source.readLine()) != null) {
    		String[] attr = line.split(",");
    		if (attr.length == 2) {
    			result.put(attr[0], attr[1]);
    		}
    	}
    	
    	return result;
    }
}

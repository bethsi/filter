package web

import javax.servlet.{Filter, FilterChain, ServletRequest, ServletResponse, FilterConfig}
import javax.servlet.http.HttpServletResponse
import java.nio.charset._;

class SwearInsertFilter extends Filter  {
	private val determiners = List[String]("a", "an", "the", "A", "An", "The")
    
    override def init(fc: FilterConfig): Unit = {}
	override def destroy(): Unit = {}
    
	override def doFilter(request: ServletRequest, response: ServletResponse,
			chain: FilterChain): Unit = {
		
		var brw: ByteResponseWrapper = new ByteResponseWrapper(response.asInstanceOf[HttpServletResponse])
		
		chain doFilter(request, brw)
		
		val textArray = new String(brw.getBytes(), Charset.forName("UTF-8")).split(" ")
		response.setCharacterEncoding("UTF-8")
		response.getWriter println insertSwearWordsIntoArray(textArray).mkString(" ") 
	}
	
	private def insertSwearWordsIntoArray(words: Array[String]): Array[String] = {
	    def insertSwearWordAfterDeterminers(word: String): String = word match {
	    	case det if determiners contains det => det + " f**king"
	    	case _ => word
	    }
	    
	    words map insertSwearWordAfterDeterminers
	}
}
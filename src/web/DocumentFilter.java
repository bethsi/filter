package web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class DocumentFilter implements Filter {

	@Override
	public void destroy() {
	}

	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ByteResponseWrapper brw = new ByteResponseWrapper((HttpServletResponse) response);
        
		chain.doFilter(request, brw);
        
		String[] words = splitTextIntoWords(new String(brw.getBytes(), Charset.forName("UTF-8")));
        for (int i = 0; i < words.length; ++i) {
    		words[i] = obfuscateCharactersExceptFirstAndLast(words[i]);
        }
        
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(mergeStringArray(words));
	}

	private String[] splitTextIntoWords(String text) {
		return text.split(" ");
	}
	
	private String obfuscateCharactersExceptFirstAndLast(String word) {
		int indexFrom = firstLetterIndex(word) + 1;
		int indexTo = lastLetterIndex(word) - 1;
		
		if (indexFrom < indexTo) {
			word = permutateCharactersInRangeOfString(word, indexFrom, indexTo);
		}
		
		return word;
	}

	private int firstLetterIndex(String word) {
		int indexBegin = 0;
		while ((! Character.isLetter(word.charAt(indexBegin))) && indexBegin > 0) {
			++indexBegin;
		}
		return indexBegin;
	}
	
	private int lastLetterIndex(String word) {
		int indexEnd = word.length() - 1;
		while ((! Character.isLetter(word.charAt(indexEnd))) && indexEnd > 0) {
			--indexEnd;
		}
		return indexEnd;
	}
	
	private String permutateCharactersInRangeOfString(String word, int from, int to) {
		for (int i = from; i < to; ++i) {
			word = swapCharactersInString(word, i, randomInt(i + 1, to));
		}
		return word;
	}
	
	private String swapCharactersInString(String word, int indexA, int indexB) {
		char[] wordSeq = word.toCharArray();
		char temp = wordSeq[indexA];
		wordSeq[indexA] = wordSeq[indexB];
		wordSeq[indexB] = temp;
		
		return new String(wordSeq);
	}

	private int randomInt(int from, int to) {
		if (from > to) { throw new IllegalArgumentException(); }
		
		if (from == to) {
			return from;
		} else {
			Random rand = new Random();
			return from + rand.nextInt(to-from);
		}
	}  
	
	private String mergeStringArray(String[] words) {
		StringBuilder result = new StringBuilder();
		for (String word : words) {
			result.append(word)
				  .append(" ");
		}
		return result.deleteCharAt(result.length()-1).toString();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}

package servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Servlet implementation class SearchServlet
 */
@WebServlet(description = "Front end for Search Action", urlPatterns = { "/SearchServlet" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
/*
Enumeration<String> headers = request.getHeaderNames();
while (headers.hasMoreElements()) {
	String key = headers.nextElement();
	System.out.println("key : " + key + ", value : " + request.getHeader(key));	
}
*/
		
		BufferedReader br0 = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br0 != null){
            json = br0.readLine();
        }
		ObjectMapper reqMapper = new ObjectMapper();		
		Map<String, String> jsonMap = reqMapper.readValue(json, Map.class);
		
		String keyword = jsonMap.get("keyword");

		String urlEncodedKeyword = URLEncoder.encode(keyword,"utf-8");
		
		String urlDaumAPI = "http://apis.daum.net/search/book";
		String urlParameter = "apikey=DAUM_SEARCH_DEMO_APIKEY&output=json&q="+urlEncodedKeyword;
		
		URL url = new URL(urlDaumAPI);		
		HttpURLConnection urlC = (HttpURLConnection)url.openConnection();
		
		urlC.setDoOutput(true);
		
		DataOutputStream dos = new DataOutputStream(urlC.getOutputStream());
		dos.writeBytes(urlParameter);
		dos.flush();
		dos.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
		String inputLine = null;
		StringBuffer responseSB = new StringBuffer();
		
		while ( (inputLine = br.readLine()) != null ) {
			responseSB.append(inputLine);
		}
		br.close();
		
		String result = responseSB.toString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(result);
		
		// response to ajax
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();				
		out.print(result);
		
		
		// dispatch to jsp
		/*
		request.setAttribute("resultJsonNode", rootNode);
		request.setAttribute("keyword", keyword);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/result.jsp");
		dispatcher.forward(request, response);
		*/
	}

}

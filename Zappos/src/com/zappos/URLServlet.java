package com.zappos;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class URLServlet extends HttpServlet {
	
	 public void doGet(HttpServletRequest req, HttpServletResponse res)
	            throws ServletException, IOException {

	        URL urldemo = new URL("http://api.zappos.com/Search?limit=10&key=b05dcd698e5ca2eab4a0cd1eee4117e7db2a10c4/");
	        URLConnection yc = urldemo.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                yc.getInputStream()));
	        String inputLine;
	        StringBuffer jsonString = new StringBuffer();
	        while ((inputLine = in.readLine()) != null){
	           // System.out.println(inputLine);
	        	jsonString.append(inputLine);
	        }
	        in.close();
	        String response  = jsonString.toString();
	        System.out.println("Repsponse:");
	        System.out.println(response);
	        

	    }

}

package com.appstudio.android.sample.ch_25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class SendServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(SendServlet.class.getName());
	private static String AUTH_TOKEN = null;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException,ServletException {
		req.setAttribute("send",
				Integer.toString(send(req.getParameter("device_id"), 
						req.getParameter("registration_id"),
						req.getParameter("message"))));		
		req.setAttribute("auth_token", AUTH_TOKEN);		
		getServletConfig().getServletContext().getRequestDispatcher("/send.jsp").forward(req, resp);
	}

	private int send(String device_id, String registration_id, String message){
		URL url;
		HttpURLConnection con;
		int resCode= -1;
		if(AUTH_TOKEN == null){
			try{
				AUTH_TOKEN = getAuthToken();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		try {
			url = new URL("https://android.apis.google.com/c2dm/send");
			StringBuilder param = new StringBuilder();
			param.append("").append("registration_id").append("=").append(registration_id);
			param.append("&").append("collapse_key").append("=").append("1");
			param.append("&").append("delay_while_idle").append("=").append("1");
			param.append("&").append("data.msg").append("=").append(URLEncoder.encode(message, "UTF-8"));

			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Length", Integer.toString(param.toString().getBytes().length));
			con.setRequestProperty("Authorization", "GoogleLogin auth="+AUTH_TOKEN);
			
			OutputStream outstream = con.getOutputStream();
			outstream.write( param.toString().getBytes("UTF-8") );
			outstream.flush();
			outstream.close();
			
			resCode = con.getResponseCode();
			// Read the response
		    BufferedReader reader = new BufferedReader(new InputStreamReader(
		            con.getInputStream()));
		    String line = null;
		    StringBuilder resbody = new StringBuilder();
		    while ((line = reader.readLine()) != null) {
		    	resbody.append(line);
		    }

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resCode;
	}

	private String getAuthToken() throws Exception{
		StringBuilder postBuilder = new StringBuilder();
		postBuilder.append("accountType=HOSTED_OR_GOOGLE");
//		postBuilder.append("&").append("accountType").append("=").append("HOSTED_OR_GOOGLE");
		postBuilder.append("&").append("Email").append("=").append("mhb8436@gmail.com");
		postBuilder.append("&").append("Passwd").append("=").append("********");
		postBuilder.append("&").append("service").append("=").append("ac2dm");
		postBuilder.append("&").append("source").append("=").append("com.appstudio.android.sample.ch_25");
		
		byte[] post = postBuilder.toString().getBytes("UTF-8");
		URL url = new URL("https://www.google.com/accounts/ClientLogin");
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", Integer.toString(post.length));
		
		OutputStream out = conn.getOutputStream();
		out.write(post);
		out.close();
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
		
		String sid = reader.readLine();
		String lsid = reader.readLine();
		String auth = reader.readLine();
		
		return auth.substring(5,auth.length());
		
	}

}

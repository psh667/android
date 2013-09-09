package com.appstudio.android.sample.ch_25;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.mortbay.log.Log;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class DeviceServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(DeviceServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException,ServletException {
		System.out.println( "device_id:"+req.getParameter("device_id"));
		System.out.println( "registration_id:"+req.getParameter("registration_id"));
		
		req.setAttribute("key", 
				store(req.getParameter("device_id"), 
						req.getParameter("registration_id")));
		getServletConfig().getServletContext().getRequestDispatcher("/device.jsp").forward(req, resp);
	}

	private Key store(String device_id, String registration_id) {
		
        Key key = KeyFactory.createKey("registration_id", registration_id);
        
        Entity device = new Entity("Registration", key);
        device.setProperty("device_id", device_id);
        device.setProperty("registration_id", registration_id);

        DatastoreService datastore =
                DatastoreServiceFactory.getDatastoreService();
        return datastore.put(device);

	}
}

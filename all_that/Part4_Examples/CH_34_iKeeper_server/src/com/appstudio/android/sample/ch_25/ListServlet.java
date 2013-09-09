package com.appstudio.android.sample.ch_25;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class ListServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(ListServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException,ServletException {
		req.setAttribute("list",
				select(req.getParameter("device_id"), 
						req.getParameter("registration_id")));
			getServletConfig().getServletContext().getRequestDispatcher("/list.jsp").forward(req, resp);
	}


	private List<Entity> select(String device_id, String registration_id) {
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("Registration", null);
		return datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
	}

}

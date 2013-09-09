package com.appstudio.appengine.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class UserServlet extends BaseServlet {
  private static final Logger logger = Logger.getLogger(UserServlet.class.getCanonicalName());
  public void doGet(HttpServletRequest req, 
                    HttpServletResponse resp) 
                         throws IOException, ServletException {
    super.doGet(req, resp);
    String action = req.getParameter(UserService.ACTION);
    UserService userService = new UserService();
    
    PrintWriter out = resp.getWriter();
    if (action.equals(UserService.UPSERT)) {
      User user = User.createUser(req);
      userService.create(user);
    }else if (action.equals(UserService.GET)) {
      System.out.println("test===");
      logger.log(Level.WARNING, "test===");
      Gson gson = new Gson();
      out.print(gson.toJson(userService.get(req.getParameter(User.TEL_NO))));
    }else if (action.equals(UserService.FIND_BY_NAME)) {
      Gson gson = new Gson();
      out.print(gson.toJson(userService.getByName(req.getParameter(User.NAME))));
    }else if(action.equals(UserService.ALL_LIST)) {
      Gson gson = new Gson();
      out.print(gson.toJson(userService.allList()));
    }else if (action.equals(UserService.DELETE)) {
      userService.delete(req.getParameter(User.TEL_NO));
    } 
  }
  
  public void doPost(HttpServletRequest req, 
      HttpServletResponse resp) 
          throws IOException, ServletException {
    doGet(req, resp);
  }
}

package com.appstudio.appengine.sample;

import java.util.List;

import com.google.gson.JsonElement;

public class UserService {
  final static public String ACTION = "action";
  final static public String UPSERT = "upsert"; 
  final static public String DELETE = "delete";
  final static public String FIND_BY_NAME = "findbyname";
  final static public String GET = "get";
  final static public String ALL_LIST = "allList";
  
  public void create( User user )  {
    UserDao.createOrUpdate(user);
  }
  
  public List<User> getByName(String name)  {
    return UserDao.getByName(name);
  }
  
  public void delete(String telNo)  {
    UserDao.delete(telNo);
  }  
  
  public User get(String telNo)  {
    return UserDao.get(telNo);
  }

  public List<User> allList() {
    return UserDao.allList();
  }
}

package com.appstudio.appengine.sample;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class UserDao {

  public static void createOrUpdate(User user) {
    Key key = KeyFactory.createKey(User.KIND,user.getTelNo());
    Entity entity = Util.findEntity(key);    
    if (entity == null) {
      entity = new Entity(User.KIND, user.getTelNo());
      entity.setProperty(User.NAME, user.getName());
    } else {
      entity.setProperty(User.NAME, user.getName());
    }
    Util.persistEntity(entity);
  }
  
  public static User get(String telNo) {
    Key key = KeyFactory.createKey(User.KIND, telNo);
    return User.createUser(Util.findEntity(key));
  }  

  public static List<User> getByName(String name) {
    ArrayList<User> list = new ArrayList<User>();
    Iterable<Entity> entities = Util.listEntities(User.KIND, 
                                              User.NAME, name);
    for(Entity entity : entities)  {
      list.add(User.createUser(entity));
    }
    return list;
  }  
  
  public static void delete(String telNo)  {
    Key key = KeyFactory.createKey(User.KIND, telNo);    
    Util.deleteEntity(key);
  }

  public static List<User> allList() {
    ArrayList<User> list = new ArrayList<User>();
    Iterable<Entity> entities = Util.listEntities(User.KIND, 
                                        null, null, User.NAME);
    for(Entity entity : entities)  {
      list.add(User.createUser(entity));
    }
    return list;
  }  
}

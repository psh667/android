package com.appstudio.appengine.sample;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Entity;

public class User {
  // KIND는 모든 객체에서 유니크 해야 한다. 
  final static public String KIND = "USER";  
  final static public String TEL_NO = "TEL_NO";
  final static public String NAME = "NAME";
  
  private String telNo;
  private String name;
  
  public String getTelNo() {
    return telNo;
  }
  public void setTelNo(String telNo) {
    this.telNo = telNo;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  // 앱엔진의 데이터스토어 객체를 도메인 객체로 변환
  static public User createUser(Entity entity)  {
    User user = new User();
    user.setTelNo((String)entity.getKey().getName());
    user.setName((String)entity.getProperty(NAME));
    return user;
  }
  
  // HttpRequest의 값을 도메인 객체로 변환
  static public User createUser(HttpServletRequest req)  {
    User user = new User();
    user.setTelNo((String)req.getParameter(TEL_NO));
    user.setName((String)req.getParameter(NAME));
    return user;
  }  
}

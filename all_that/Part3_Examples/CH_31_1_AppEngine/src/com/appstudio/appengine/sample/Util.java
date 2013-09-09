// Copyright 2011, Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.appstudio.appengine.sample;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceConfig;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.ImplicitTransactionManagementPolicy;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;


/**
 * This is the utility class for all servlets. It provides method for inserting,
 * deleting, searching the entity from data store. Also contains method for
 * displaying the entity in JSON format.
 * 
 */
public class Util {

  private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());
  private static DatastoreServiceConfig config = 
      DatastoreServiceConfig.
      Builder.
      withImplicitTransactionManagementPolicy(
                    ImplicitTransactionManagementPolicy.NONE);
  private static DatastoreService datastore = 
                 DatastoreServiceFactory.getDatastoreService(config);
  // 데이터스토어 저장 공간에 저장한다.
  public static void persistEntity(Entity entity) {
    datastore.put(entity);
  }

  // 데이터스토어에서 키에 해당되는 객체를 삭제한다.
  public static void deleteEntity(Key key) {
    datastore.delete(key);  	
  }

  // 데이터스토어에서 키목록에 해당되는 객체목록을 삭제한다.
  public static void deleteEntity(final List<Key> keys){
    datastore.delete(new Iterable<Key>() {
      public Iterator<Key> iterator() {
        return keys.iterator();
      }
    });
  }
  // 키에 해당하는 객체를 반환
  public static Entity findEntity(Key key) {
    try {  
      return datastore.get(key);
    } catch (EntityNotFoundException e) {
      return null;
   }
  }

  // kind 테이블의 searchBy 컬럼이 searchFor인 객체의 목록 반환
  public static Iterable<Entity> listEntities(String kind, 
                          String searchBy, String searchFor) {
    Query q = new Query(kind);
    if (searchFor != null && !"".equals(searchFor)) {
      q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
    }
    PreparedQuery pq = datastore.prepare(q);
    return pq.asIterable();
  }
  
  // kind 테이블의 searchBy 컬럼값이 searchFor인 객체의 목록 반환
  // 이때 sortBy에 의해 정렬
  public static Iterable<Entity> listEntities(String kind, 
           String searchBy, String searchFor, String sortBy) {
    Query q = new Query(kind);
    if (searchFor != null && !"".equals(searchFor)) {
      q.addFilter(searchBy, FilterOperator.EQUAL, searchFor);
      
    }
    q.addSort(sortBy);
    PreparedQuery pq = datastore.prepare(q);
    return pq.asIterable();
  } 
  
  public static String getErrorMessage(Exception ex) 
                                           throws IOException{
    return "Error:"+ex.toString();
  }

  public static DatastoreService getDatastoreServiceInstance()
  {
    return datastore;
  }

}
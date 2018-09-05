/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev.testtest;

import java.sql.Date;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.namedev.entity.User;
import org.namedev.resource.UserResource;

/**
 *
 * @author Max <Max at Nameless Development>
 */
public class JUnitTest {
  
  private static ArrayList<User> users;
  private static UserResource ur;
  
  public JUnitTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
    ur = new UserResource();
    users = new ArrayList<User>();
  }
  
  @Before
  public void setUp() {
  }
  
  @Test
  public void createMasterAndSlaveUserTest(){
    User u = new User();
    u.setUsername("TestMasterUser");
    u.setEmail("testuser@namedev.org");
    u.setPwd_hash("5F4DCC3B5AA765D61D8327DEB882CF99");
    u.setEmail_confirmed_at(new Date(118, 6, 12));
    
    Response master_ret = ur.createUser(u.getEmail(), u.getUsername(), u.getPwd_hash(), -404);
    
    if(master_ret.getStatus() > 399){
      Assert.fail("Got Response with code "+master_ret.getStatus());
    }
    
    u.setId((long) master_ret.getEntity());
    
    users.add(u);
    
    Response slave_ret = createSlaveUserTest(u);
    
    if(slave_ret.getStatus() >= 400){
      Assert.fail("Got Response with code "+master_ret.getStatus());
    }
  }
  
  public Response createSlaveUserTest(User master){
    User u = new User();
    u.setUsername("TestSlaveUser");

    u.setMaster(master);
    
    Response slave_ret = ur.createUser(null, u.getUsername(), null, master.getId());
    
    u.setId((long)slave_ret.getEntity());
    
    users.add(u);
    
    return slave_ret;
  }
  
  @Test
  public void deleteMasterAndSlaveUserTest(){
    User[] user_arr = users.toArray(new User[0]);
    for(User user: user_arr){
      long id = user.getId();
      Response ret = ur.deleteUser(user.getId());
      if(ret.getStatus() >= 400){
        Assert.fail("Got a Response with code "+ret.getStatus());
      }
    }
  }
  
  @After
  public void tearDown() {
  }

  @AfterClass
  public static void tearDownClass() {
  }
}

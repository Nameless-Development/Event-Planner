/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev.testtest;

import java.sql.Date;
import java.util.List;
import javax.ws.rs.core.Response;
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
  
  private static UserResource ur;
  
  public JUnitTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
    ur = new UserResource();
  }
  
  @Before
  public void setUp() {
  }
  
  @Test
  public void createMasterAndSlaveUserTest(){
    User u = new User();
    u.setUsername("TestMasterUser");
    u.setEmail("testuser@namedev.org");
    u.setPwd_hash(User.TEST_PASSWORD);
    u.setEmail_confirmed_at(new Date(118, 6, 12));
    
    Response master_ret = ur.createUser(u.getEmail(), u.getUsername(), u.getPwd_hash(), -404);
    
    if(master_ret.getStatus() > 399){
      Assert.fail("Got Response with code "+master_ret.getStatus());
    }
    
    u.setId((long) master_ret.getEntity());
    
    
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
    
    return slave_ret;
  }
  
  @Test
  public void deleteUserTest(){
    List<User> test_users = ur.findAllTestUsers();
    for(User u: test_users){
      ur.deleteUser(u.getId());
    }
  }
  
  @After
  public void tearDown() {
  }

  @AfterClass
  public static void tearDownClass() {
  }
}

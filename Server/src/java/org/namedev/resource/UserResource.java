/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev.resource;

import com.sun.istack.internal.Nullable;
import java.util.List;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.namedev.DatabaseResource;
import org.namedev.entity.User;

/**
 * REST Web Service
 *
 * @author Max <Max at Nameless Development>
 */
@Path("/User")
public class UserResource extends DatabaseResource{
  
  
  @PUT
  @Path("/create")
  @Produces(MediaType.TEXT_PLAIN)
  public Response createUser(@FormParam("email") String email,
                             @FormParam("username") String username,
                             @FormParam("pwd_hash") String hash,
                             @FormParam("user_master") @Nullable long master_id) {
    connect();
    entman.getTransaction().begin();
    
    
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setEmail_confirmed_at(null);
    user.setPwd_hash(hash);
    
    if(master_id != -404){
      Query q = entman.createQuery("SELECT u FROM User u WHERE u.id LIKE :master_id");
      q.setParameter("master_id", master_id);
      User master = (User) q.getSingleResult();
      user.setMaster(master);
    }
    
    try {
      entman.persist(user);
      entman.getTransaction().commit();
      return Response
        .status(Response.Status.CREATED)
        .entity(user.getId())
        .build();
    } catch (Exception ex) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } finally {
      entman.close();
    }

  }
/*
  @POST
  @Path("/update/{id}")
  @Produces(MediaType.TEXT_XML)
  public Response updateUser(@PathParam("id") Long id, @FormParam("username") String username) {
    User user = controller.querySingle("User.findById", new Pair("id", id));

    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    user.setUsername(username);

    
    if (controller.update("User.update", user)) {
      return Response.status(Response.Status.OK).build();
    } else {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    
  }
*/
  @DELETE
  @Path("/delete/{id}")
  @Produces(MediaType.TEXT_XML)
  public Response deleteUser(@PathParam("id") long id) {
    connect();
    entman.getTransaction().begin();
    User user = findUserById(id);
    
    if (user == null) {
      entman.close();
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    List<User> slaves = findSlavesOfUser(id);
    
    try {
      for(User s: slaves){
        entman.persist(s);
        entman.remove(s);
      }
      entman.persist(user);
      entman.remove(user);
      entman.getTransaction().commit();
      return Response.status(Response.Status.OK).build();
    } catch (Exception ex) {
      ex.printStackTrace();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } finally{
      entman.close();
    }
  }
  
  private User findUserById(long id){
    System.out.println("Searching for User"+id);
    Query q = entman.createQuery("SELECT u FROM User u WHERE u.id LIKE :id");
    q.setParameter("id", id);
    User ret = (User) q.getSingleResult();
    return ret;
  }
  
  private List<User> findSlavesOfUser(long id){
    User master = findUserById(id);
    Query q = entman.createQuery("SELECT u FROM User u WHERE u.master = :master");
    q.setParameter("master", master);
    List<User> ret = q.getResultList();
    return ret;
  }
  
  public List<User> findAllTestUsers(){
    connect();
    Query q = entman.createQuery("SELECT u FROM User u WHERE u.pwd_hash = :pwd");
    q.setParameter("pwd", User.TEST_PASSWORD);
    List<User> ret = q.getResultList();
    return ret;
  }
}

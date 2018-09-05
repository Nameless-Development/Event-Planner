/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev.resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lib.db.controller.Controller;
import lib.db.controller.Pair;
import org.namedev.entity.User;

/**
 * REST Web Service
 *
 * @author Max <Max at Nameless Development>
 */
@Path("/User")
public class UserResource {

  private final Controller<User> controller;
  
  @Context
  private UriInfo context;

  /**
   * Creates a new instance of UserResource
   */
  public UserResource() {
    controller = new Controller<>("EPS-PU", User.class);
  }
  
  /*
  @GET
  @Path("/getById/{id}")
  @Produces(MediaType.TEXT_XML)
  public Response getUserById(@PathParam("id") Long id){
    
  }
  */
  @PUT
  @Path("/create")
  @Produces(MediaType.TEXT_PLAIN)
  public Response createUser(@FormParam("email") String email,
                             @FormParam("username") String username,
                             @FormParam("pwd_hash") String hash,
                             @FormParam("user_master") long master_id) {
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setEmail_confirmed_at(null);
    user.setPwd_hash(hash);
    
    if(master_id != -404){
      User master = controller.querySingle("User.findById", new Pair("id", master_id));
      user.setMaster(master);
    }
    
    try {
      controller.insert(user);
    } catch (Exception ex) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    return Response
            .status(Response.Status.CREATED)
            .entity(user.getId())
            .build();
  }

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

  @DELETE
  @Path("/delete/{id}")
  @Produces(MediaType.TEXT_XML)
  public Response deleteUser(@PathParam("id") long id) {
    System.out.println("Deleting user "+id);
    System.out.flush();
    User user = controller.querySingle("User.findById", new Pair("id", id));

    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    try {
      controller.delete(user);
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.flush();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    return Response.status(Response.Status.OK).build();
  }
  
  /**
   * Retrieves representation of an instance of org.namedev.resource.UserResource
   * @return an instance of java.lang.String
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public String getXml() {
    //TODO return proper representation object
    throw new UnsupportedOperationException();
  }

  /**
   * PUT method for updating or creating an instance of UserResource
   * @param content representation for the resource
   */
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  public void putXml(String content) {
    throw new UnsupportedOperationException();
  }
}

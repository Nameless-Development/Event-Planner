/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev.test;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lib.db.controller.Controller;
import lib.db.controller.Pair;
import org.namedev.XmlSerializable;

/**
 * REST Web Service
 *
 * @author Max
 */
@Path("/TestUser")
public class TestUserResource {

  private final Controller<TestUserEntity> controller;

  @Context
  private UriInfo context;

  /**
   * Creates a new instance of TestResource
   */
  public TestUserResource() {
    controller = new Controller<>("EPS-PU", TestUserEntity.class);
  }

  @PUT
  @Path("/create")
  @Produces(MediaType.TEXT_XML)
  public Response createTestUser(@FormParam("username") String username) {
    TestUserEntity user = new TestUserEntity();
    user.setUsername(username);

    try {
      controller.insert(user);
    } catch (Exception ex) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    return Response
            .status(Response.Status.CREATED)
            .build();
  }

  @POST
  @Path("/update/{id}")
  @Produces(MediaType.TEXT_XML)
  public Response updateTestUser(@PathParam("id") Long id, @FormParam("username") String username) {
    TestUserEntity user = controller.querySingle("Person.findById", new Pair("id", id));

    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    user.setUsername(username);

    if (controller.update("Person.update", user)) {
      return Response.status(Response.Status.OK).build();
    } else {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DELETE
  @Path("/delete/{id}")
  @Produces(MediaType.TEXT_XML)
  public Response deleteTestUser(@PathParam("id") Long id) {
    TestUserEntity user = controller.querySingle("Person.findById", new Pair("id", id));

    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    try {
      controller.delete(user);
    } catch (Exception ex) {
      Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    return Response.status(Response.Status.OK).build();
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev.test;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lib.db.controller.Controller;
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
    public Response createTestUser(@FormParam("username") String username){
        TestUserEntity user = new TestUserEntity();
        user.setUsername(username);
        
        try{
            controller.insert(user);
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity(XmlSerializable.getXmlStream().toXML(user))
                .build();
    }
}

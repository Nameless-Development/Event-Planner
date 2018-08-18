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

/**
 * REST Web Service
 *
 * @author Max
 */
@Path("/Test")
public class TestResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestResource
     */
    public TestResource() {
    }

    /**
     * Retrieves representation of an instance of org.nameless_dev.test.TestResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    public String getXml() {
        //TODO return proper representation object
        return "<text>This works. a bit</text>";
    }

    /**
     * PUT method for updating or creating an instance of TestResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_XML)
    public void putXml(String content) {
    }
    
    @GET
    @Path("/ping")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String ping(@QueryParam("from") String from){
        System.err.println(from+": Ping recieved");
        return from+": Ping recieved";
    }
}

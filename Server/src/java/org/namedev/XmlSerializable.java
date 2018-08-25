/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import org.namedev.test.TestUserEntity;

/**
 * An Interface that all Entity classes that are supposed to be transferred between Client and Server
 * must implement.
 * @author Max
 */
public interface XmlSerializable {
    /**
     * Creates a new instance of {@code XStream} and initializes it with
     * the common aliases and attributes used within this project.
     * 
     * @return the XStream instance
     */
    public static XStream getXmlStream(){
        XStream stream = new XStream(new StaxDriver());
        
        stream.alias("TestUser", TestUserEntity.class);
        
        //variables which are saved as attributes instead of fields
        stream.useAttributeFor(TestUserEntity.class, "id");
        
        return stream;
    }
}

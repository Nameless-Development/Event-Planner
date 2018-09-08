/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Max <Max at Nameless Development>
 */
public abstract class DatabaseResource {
  
  public final String PERSISTENCE_UNIT = "EPS-PU";
  protected EntityManagerFactory factory;
  protected EntityManager entman;
  
  public void connect(){
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    entman = factory.createEntityManager();
  }
}

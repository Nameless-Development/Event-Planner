/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.namedev.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author Max <Max at Nameless Development>
 */
@Entity
@Table(name = "tbl_user")
@NamedQueries({
  @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id LIKE :id")
})
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "user_email")
  private String email;
  
  @Column(name = "user_email_confirmed_at")
  private Date email_confirmed_at;
  
  @Column(name = "user_username")
  private String username;
  
  @Column(name = "user_pwd_hash")
  private String pwd_hash;
  
  @ManyToOne
  @JoinColumn(name = "user_master")
  private User master;
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getEmail_confirmed_at() {
    return email_confirmed_at;
  }

  public void setEmail_confirmed_at(Date email_confirmed_at) {
    this.email_confirmed_at = email_confirmed_at;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPwd_hash() {
    return pwd_hash;
  }

  public void setPwd_hash(String pwd_hash) {
    this.pwd_hash = pwd_hash;
  }

  public User getMaster() {
    return master;
  }

  public void setMaster(User master) {
    this.master = master;
  }
  
  //TODO actual representation
  public String toString(){
    String ret = "User "+id+" [email = "+email+", username = "+username+"]";
    return ret;
  }
}

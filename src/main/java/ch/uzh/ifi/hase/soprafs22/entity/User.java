package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Status;
import ch.uzh.ifi.hase.soprafs22.constant.UserType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long userId;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = true, unique = true)
  private UserType type;

  @Column(nullable = false)
  private   Status status;

  @Column(nullable = false, unique = true)
  private String token;



  public Long getUserId() {return userId; }

  public void setUserId(Long userId) {this.userId = userId;}

  public String getUsername() {return username; }

  public void setUsername(String username) {this.username = username;}

  public String getPassword() {return password; }
  public void setPassword(String password) {this.password = password;}

  public String getToken() {return token;}

  public void setToken(String token) {this.token = token;}

  public UserType getUserType() {return type;}

  public void setUserType(UserType type) {this.type = type;}

  public Status getStatus() {return status;}

  public void setStatus(Status status) {this.status = status;}

}

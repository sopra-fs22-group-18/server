package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.Status;

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
  private String username;//primary key part2



  @Column(nullable = false)
    private String password;

  @Transient
  @Column(nullable = false, unique = true)
  private String token;

  @Transient
  @Column(nullable = false)
  private Status status;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;}

  public void setPassword(String password) {
    this.password = password;}

  public String getUsername() {
    return username;}

  public void setUsername(String username) {
    this.username = username;}

  public String getToken() {
    return token;}

  public void setToken(String token) {
    this.token = token;}

  

public Status getStatus() {
  return status;}

public void setStatus(Status status) {
  this.status = status;}
}


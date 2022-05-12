package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
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

  @Id //primary key part1
  @GeneratedValue
  private Long userId;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = true)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = true)
  private UserType type;

  @Column(nullable = false)
  private UserStatus userStatus;

  @Column(nullable = false, unique = true)
  private String token;
  
  @Column(nullable = true)
  private String avatarUrl;

  @Column(nullable = true)
  private String bio;

  @Column(nullable = true)
  private Integer participated_sessions = 0;

  @Column(nullable = true)
  private Integer wonSessions = 0;

  public Long getUserId() {return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public String getUsername() {return username; }

  public void setUsername(String username) {this.username = username;}

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public String getPassword() { return password; }

  public void setPassword(String password) { this.password = password; }

  public String getToken() { return token; }

  public void setToken(String token) { this.token = token; }

  public UserType getUserType() {return type;}

  public void setUserType(UserType type) {this.type = type;}

  public UserStatus getUserStatus() {return userStatus;}

  public void setUserStatus(UserStatus userStatus) {this.userStatus = userStatus;}

  public String getAvatarUrl() { return avatarUrl; }

  public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

  public String getBio() {return bio;}

  public void setBio(String bio) {this.bio = bio;}
  public Integer getParticipated_sessions() {return participated_sessions;}

  public void setParticipated_sessions(Integer participated_sessions) {this.participated_sessions = participated_sessions;}

  public Integer getWonSessions(){return wonSessions;}

  public void setWonSessions(Integer wonSessions){this.wonSessions = wonSessions;}

}

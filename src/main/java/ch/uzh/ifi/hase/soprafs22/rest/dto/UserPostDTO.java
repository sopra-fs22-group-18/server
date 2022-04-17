package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserType;
import java.awt.Image;

public class UserPostDTO {
  private Long userId;
  private String password;
  private String username;

  private UserType type;
  private UserStatus userStatus;
  private String token;
  private Image  profilePic;

  public Long getUserId() {return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public String getUsername() {return username; }

  public void setUsername(String username) {this.username = username;}

  public String getPassword() {return password; }
  public void setPassword(String password) {this.password = password;}

  public String getToken() {return token;}

  public void setToken(String token) {this.token = token;}

  public UserType getUserType() {return type;}

  public void setUserType(UserType type) {this.type = type;}

    public UserStatus getUserStatus() {return userStatus;}

    public void setUserStatus(UserStatus userStatus) {this.userStatus = userStatus;}

    public UserType getType() { return type; }

  public void setType(UserType type) { this.type = type; }

  public Image getImage() { return profilePic; }

  public void setImage(Image profilePic) { this.profilePic = profilePic; }

}


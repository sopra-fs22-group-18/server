package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserType;
import java.awt.Image;

public class UserPostDTO {
  private String password;
  private String username;


  public String getUsername() {return username; }

  public void setUsername(String username) {this.username = username;}

  public String getPassword() {return password; }
  public void setPassword(String password) {this.password = password;}


}


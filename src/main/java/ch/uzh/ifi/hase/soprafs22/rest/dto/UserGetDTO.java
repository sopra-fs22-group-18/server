package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;

import java.util.Date;

public class UserGetDTO {
  private Long userId;
  private String username;
  private String token;

  public Long getUserId() {return userId;}

  public void setUserId(Long userId) {this.userId = userId;}

  public String getUsername() {return username;}

  public void setUsername(String username) {this.username = username;}


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

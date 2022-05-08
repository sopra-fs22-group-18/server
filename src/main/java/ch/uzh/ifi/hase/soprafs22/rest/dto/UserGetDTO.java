package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;

import java.util.Date;

public class UserGetDTO {
  private Long userId;
  private String username;
  private String token;
  private Integer participated_sessions;
  private Integer wonSessions;

  public Long getUserId() {return userId;}

  public void setUserId(Long userId) {this.userId = userId;}

  public String getUsername() {return username;}

  public void setUsername(String username) {
      this.username = username;
  }

  public String getToken() {
      return token;
  }

  public void setToken(String token) {
      this.token = token;
  }

  public Integer getParticipated_sessions() {
      return participated_sessions;
  }

  public void setParticipated_sessions(Integer participated_sessions) {
      this.participated_sessions = participated_sessions;
  }

  public Integer getWonSessions(){
      return wonSessions;
  }

  public void setWonSessions(Integer wonSessions) {
      this.wonSessions = wonSessions;
  }
}

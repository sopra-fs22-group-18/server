package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;

import java.util.Date;

public class UserGetDTO {
  private Long userId;
  private String username;
  private String name;
  private String token;
  private String avatarUrl;
  private String bio;
  private Integer participated_sessions;
  private Integer wonSessions;

  public Long getUserId() {return userId;}

  public void setUserId(Long userId) {this.userId = userId;}

  public String getUsername() {return username;}

  public void setUsername(String username) {this.username = username;}

  public String getName() {return name;}

  public void setName(String name) {this.name = name;}

  }

  public String getToken() {
        return token;
    }

  public void setToken(String token) {
        this.token = token;
    }

  public String getAvatarUrl() {return avatarUrl;}

  public void setAvatarUrl(String avatarUrl) {this.avatarUrl = avatarUrl;}

  public String getBio() {return bio;}

    public void setBio(String bio) {this.bio = bio;}

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

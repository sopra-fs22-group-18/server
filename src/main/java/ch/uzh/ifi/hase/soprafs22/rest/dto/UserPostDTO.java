package ch.uzh.ifi.hase.soprafs22.rest.dto;

public class UserPostDTO {
  private Long userId;
  private String password;
  private String username;

  public Long getUserId() {return userId;}

  public void setUserId(Long userId) {this.userId = userId;}

  public String getPassword() {return password;}

  public void setPassword(String password) {this.password = password;}

  public String getUsername() {return username;}

  public void setUsername(String username) {this.username = username;}
}

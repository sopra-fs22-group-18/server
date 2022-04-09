package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;

import java.util.Date;

public class UserGetDTO {
  private Long userId;
  private String username;
  private Date creation_date;
  private boolean logged_in;
  private Date birthday;

  public Long getUserId() {return userId;}

  public void setUserId(Long userId) {this.userId = userId;}

  public String getUsername() {return username;}

  public void setUsername(String username) {this.username = username;}

  public void setCreation_date(Date creation_date) {this.creation_date = creation_date;}

  public boolean getLogged_in() {return logged_in;}

  public void setLogged_in(boolean logged_in) {this.logged_in = logged_in;}

  public Date getBirthday() {return birthday;}

  public void setBirthday(Date birthday) {this.birthday = birthday;}

  public boolean isLogged_in() {
        return logged_in;
    }

}

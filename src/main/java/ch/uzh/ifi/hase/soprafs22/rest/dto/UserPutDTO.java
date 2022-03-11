package ch.uzh.ifi.hase.soprafs22.rest.dto;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import java.util.Date;

public class UserPutDTO {
    private Long id;
    private String username;
    private Date birthday;
    private String password;
    
    public Long getId() {
      return id;
    }
  
    public void setId(Long id) {
      this.id = id;
    }
  
  
    public String getUsername() {
      return username;
    }
  
    public void setUsername(String username) {
      this.username = username;
    }
  
   
    public Date getBirthday() {
      return birthday;
  }
  
  public void setBirthday(Date birthday) {
      this.birthday = birthday;
  }
}
  
  




















    


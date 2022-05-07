package ch.uzh.ifi.hase.soprafs22.rest.dto;


import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;

import java.util.Set;

public class SessionGetDTO {

  private Long sessionId;
  private User host;
//  private User winner;
  private Set<User> participants;
  private int maxParticipants;
  private SessionStatus sessionStatus;
  private String title;
  private String hostUsername;
  private String identifier;
  private String imageUrl;


    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

//    public User getHost() {
//        return host;
//    }
//
//    public void setHost(User host) {
//        this.host = host;
//    }

//    public User getWinner() {
//        return winner;
//    }
//
//    public void setWinner(User winner) {
//        this.winner = winner;
//    }
//
//    public Set<User> getParticipants() {
//        return participants;
//    }
//
//    public void setParticipants(Set<User> participants) {
//        this.participants = participants;
//    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }


    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public String getImageUrl() {return imageUrl;}

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;}
}

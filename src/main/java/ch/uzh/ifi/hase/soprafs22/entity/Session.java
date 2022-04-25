package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SESSION")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long sessionId;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "hostId")
    private User host;

    @ManyToOne
    @JoinColumn(name = "winnerId")
    private User winner;

    @ManyToMany
    private Set<User> participants = new HashSet<>();

    @Column(nullable = false)
    private int maxParticipants = 2;

    @Column(nullable = false)
    private Date created_date = new Date();

    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String imageUrl;


    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) { this.host = host; }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User participant) { this.participants.add(participant);
        if (participants.size() == maxParticipants) { this.setSessionStatus(SessionStatus.ONGOING);}
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Date getCreatedDate() {
        return created_date;
    }

    public void setCreatedDate(Date created_date) {
        this.created_date = created_date;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) { this.sessionStatus = sessionStatus; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}



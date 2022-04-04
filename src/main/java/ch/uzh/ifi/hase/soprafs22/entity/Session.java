package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.ReportReason;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "Session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long sessionId;

    @Column(nullable = false)
    private User host;

    @Column(nullable = false)
    private ArrayList<User> participants;

    @Column(nullable = false)
    private User winner;

    @Column(nullable = false)
    private int maxParticipants;

    @Column(nullable = false)
    private ReportReason reason;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public ReportReason getReason() {
        return reason;
    }

    public void setReason(ReportReason reason) {
        this.reason = reason;
    }
}
package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.ReportReason;

import javax.persistence.*;
import java.io.Serializable;

/**
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "Report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long reportId;

    @Column(nullable = false)
    private Comment comment;

    @Column(nullable = false)
    private Session session;

    @Column(nullable = false)
    private User user;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private ReportReason reason;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportReason getReason() {
        return reason;
    }

    public void setReason(ReportReason reason) {
        this.reason = reason;
    }
}
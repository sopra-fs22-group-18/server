
package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;

import java.util.Date;

public class CommentGetDTO {

    private Long commentId;
    private Session session;
    private User user;
    private String commentText;
    private Date createdDate;

    public Long getCommentId() { return commentId; }

    public void setCommentId(Long commentId) { this.commentId = commentId; }

    public Session getSession() { return session; }

    public void setSession(Session session) { this.session = session; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getCommentText() { return commentText; }

    public void setCommentText(String commentText) { this.commentText = commentText; }

    public Date getCreatedDate() { return createdDate; }

    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

}
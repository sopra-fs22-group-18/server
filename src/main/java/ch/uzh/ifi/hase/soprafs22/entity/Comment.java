package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "Comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long commentId;

    @Column(nullable = false)
    private User user;

    @Column(nullable = false)
    private Post post;

    @Column(nullable = false)
    private int totalUpvotes;

    @Column(nullable = false)
    private int totalDownvotes;

    @Column(nullable = false)
    private String commentText;

    @Column(nullable = false)
    private Date createdDate;

    @Column(nullable = false)
    private int upDownDifference;



    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getTotalUpvotes() {
        return totalUpvotes;
    }

    public void setTotalUpvotes(int totalUpvotes) {
        this.totalUpvotes = totalUpvotes;
    }

    public int getTotalDownvotes() {
        return totalDownvotes;
    }

    public void setTotalDownvotes(int totalDownvotes) {
        this.totalDownvotes = totalDownvotes;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getUpDownDifference() {
        return upDownDifference;
    }

    public void setUpDownDifference(int upDownDifference) {
        this.upDownDifference = upDownDifference;
    }

}
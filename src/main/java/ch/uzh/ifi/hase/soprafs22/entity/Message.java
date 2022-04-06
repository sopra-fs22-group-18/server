package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE")
public class Message {
    private String from;
    private String to;
    private String content;
    
    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public String getContent() {
        return this.content;
    }

    //standard constructors, getters, setters
}
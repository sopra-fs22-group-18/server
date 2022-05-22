package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.MessageType;
import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;

public class Message {
    private String from;
    private String content;
    private MessageType Messagetype;
    private SessionStatus sessionStatus;

    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessagetype() {
        return Messagetype;
    }

    public void setMessagetype(MessageType messagetype) {
        Messagetype = messagetype;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}

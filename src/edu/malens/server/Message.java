package edu.malens.server;

public class Message {

    public Integer fromId;
    public String content;
    public String fromName;

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Message(Integer fromId, String content, String fromName) {
        this.fromId = fromId;
        this.content = content;
        this.fromName = fromName;
    }
}

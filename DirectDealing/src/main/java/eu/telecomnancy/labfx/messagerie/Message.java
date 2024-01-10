package eu.telecomnancy.labfx.messagerie;

import java.sql.Timestamp;

public class Message {
    private int messageId;
    private int conversationId;
    private int senderId;
    private String messageText;
    private Timestamp sendTime;

    // Constructeur
    public Message(int messageId, int conversationId, int senderId, String messageText, Timestamp sendTime) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.messageText = messageText;
        this.sendTime = sendTime;
    }

    // Getters et setters de messageId
    public int getMessageId() {
        return messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    // Getters et setters de conversationId
    public int getConversationId() {
        return conversationId;
    }
    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    // Getters et setters de senderId
    public int getSenderId() {
        return senderId;
    }
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    // Getters et setters de messageText
    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    // Getters et setters de timestamp
    public Timestamp getSendTime() {
        return sendTime;
    }
    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    

}

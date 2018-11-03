/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.time.LocalDate;

/**
 *
 * @author schueler
 */
public class Message {
    private int messageId;
    private Student sender;
    private String message;
    private LocalDate sendDate;
    private Room chatRoom;

    public Message(int messageId,Student sender, String message, LocalDate sendDate, Room chatRoom) {
        this.messageId= messageId;
        this.sender = sender;
        this.message = message;
        this.sendDate = sendDate;
        this.chatRoom = chatRoom;
    }

    public Room getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(Room chatRoom) {
        this.chatRoom = chatRoom;
    }
    
    

    public LocalDate getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    
    
    public Student getSender() {
        return sender;
    }

    public void setSender(Student sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Message{" + "messageId=" + messageId + ", sender=" + sender + ", message=" + message + ", sendDate=" + sendDate + ", chatRoom=" + chatRoom + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.messageId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (this.messageId != other.messageId) {
            return false;
        }
        return true;
    }

    
    
    

    
}

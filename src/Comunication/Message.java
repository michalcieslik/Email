package Comunication;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String command;
    private String account;
    private String password;
    private boolean status;


    public Message(String alert, String account, String password, boolean status) {
        this.command = alert;
        this.account = account;
        this.password = password;
        this.status = status;
    }

    public String getCommand() {
        return command;
    }

    public String getAccount() {
        return account;
    }

    public boolean isStatus() {
        return status;
    }

    public String getReciver() {
        return null;
    }

    public int getMessageId() {
        return -1;
    }

    public String getTitle() {
        return null;
    }

    public String getDate() {
        return null;
    }

    public String getText() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return null;
    }

    public ArrayList<Message> getListOfMails() {
        return null;
    }

    public void setText(String text){

    }
}

package Client;

import Comunication.Message;

import java.util.ArrayList;

public class Account {
    private String emailAddress;
    private String password;
    private ArrayList<Message> listOfMails = new ArrayList<>();

    public Account(String emailAddress, String password){
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public void addMail(Message mail){
        listOfMails.add(mail);
    }

    public void deleteMail(Message mail){
        for(int j = (listOfMails.size() - 1); j >= 0; j--){
            if(listOfMails.get(j).getMessageId() == mail.getMessageId()){
                listOfMails.remove(j);
                return;
            }
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Message> getListOfMails() {
        return listOfMails;
    }

    public void clear(){
        this.listOfMails.clear();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setListOfMails(ArrayList<Message> listOfMails) {
        this.listOfMails = listOfMails;
    }
}

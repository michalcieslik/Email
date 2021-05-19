package Comunication;

public class Mail extends Message {
    private String title;
    private String reciver;
    private String date;
    private String text;
    private int messageId;

    public Mail(String alert, String account, String password, boolean status, String sender, int messageId, String title, String date, String text) {
        super(alert, account, password,status);
        this.reciver = sender;
        this.messageId = messageId;
        this.title = title;
        this.date = date;
        this.text = text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getReciver() {
        return reciver;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

}

package Comunication;

public class DeleteMail extends Message {
    int messageId;

    public DeleteMail(String alert, String account, String password, boolean status, int messageId) {
        super(alert, account, password, status);
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }
}

package Server;

import Comunication.*;

import java.sql.SQLException;

public class InterpreterServer {
    private ConnEmails connEmails;
    private ConnAcc connAcc;
    private boolean status;

    public InterpreterServer() throws SQLException {
        connAcc = new ConnAcc();
        connEmails = new ConnEmails();
    }

    public Message Do(Message message) throws Exception {

        switch (message.getCommand()) {
            case "Mail":
                status = insertMail(message);
                return new Message("Mail", message.getAccount(), null, status);

            case "LogIn":
                status = logIn(message);
                return new Message("LogIn", message.getAccount(), null, status);

            case "Register":
                status = register(message);
                return new Message("Register", message.getAccount(), null, status);

            case "SendMails":
                return sendMails(message);

            case "DeleteAccount":
                status = deleteAccount(message);
                return new Message("DeleteAccount", message.getAccount(), null, status);

            case "DeleteMail":
                status = deleteMail(message);
                return new Message("DeleteMail", message.getAccount(), null, status);

            case "ChangePassword":
                status = changePassword(message);
                return new Message("ChangePassword", message.getAccount(), null, status);

            default:
                return new Message("Fail", message.getAccount(), null, false);
        }
    }

    public boolean deleteAccount(Message message) throws Exception {
        String account = message.getAccount();
        String password = Security.decrypt(message.getPassword());
        return connAcc.deleteAccount(account, password);
    }

    public boolean insertMail(Message message) throws SQLException {
        String account = message.getAccount();
        String sender = message.getReciver();
        String title = message.getTitle();
        String date = message.getDate();
        String text = message.getText();
        return connEmails.insertMess(account, sender, title, date, text);
    }

    public boolean logIn(Message message) throws Exception {
        String account = message.getAccount();
        String password = Security.decrypt(message.getPassword());
        return connAcc.isAcc(account, password);
    }

    public boolean changePassword(Message message) throws Exception {
        String account = message.getAccount();
        String password = Security.decrypt(message.getPassword());
        String newPassword = Security.decrypt(message.getNewPassword());
        return connAcc.changePassword(account, password, newPassword);
    }

    public boolean register(Message message) throws Exception {
        String account = message.getAccount();
        String password = Security.decrypt(message.getPassword());
        return connAcc.createAccount(account, password);
    }

    public boolean deleteMail(Message message) throws Exception {
        String account = message.getAccount();
        String password = Security.decrypt(message.getPassword());
        int id = message.getMessageId();
        return connEmails.deleteMess(account, password, id);
    }

    public Message sendMails(Message message) throws Exception {
        String account = message.getAccount();
        String password = Security.decrypt(message.getPassword());
        return connEmails.getListMess(account, password);
    }

}

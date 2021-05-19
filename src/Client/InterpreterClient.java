package Client;

import Comunication.Message;
import Comunication.Security;

public class InterpreterClient {


    public boolean Do(Message receivedMessage, Account account, Message sendMessage) throws Exception {

        switch (receivedMessage.getCommand()) {
            case "Mail":
                if (receivedMessage.isStatus()) {
                    //account.addMail(sendMessage);
                    System.out.println("Udało sie wysłac");
                    return true;
                } else {
                    System.out.println("Nie udało sie wysłac");
                    return false;
                }
            case "LogIn":
                if (receivedMessage.isStatus()) {
                    account.setEmailAddress(sendMessage.getAccount());
                    account.setPassword(sendMessage.getPassword());
                    //wyslac by pobrac maile
                    System.out.println("Zalogowano");
                    return true;
                } else {
                    System.out.println("Nie udało sie zalogowac");
                    return false;
                }
            case "Register":
                if (receivedMessage.isStatus()) {
                    System.out.println("Zarejestrowano");
                    return true;
                } else {
                    System.out.println("Nie udalo sie zarejestrowac");
                    return false;
                }
            case "SendMails":
                if (receivedMessage.isStatus()) {
                    account.clear();
                    if (receivedMessage.getListOfMails().size()>0) {
                        for (int i = 0; i < receivedMessage.getListOfMails().size(); i++) {
                            String decrypted = Security.decrypt(receivedMessage.getListOfMails().get(i).getText());
                            receivedMessage.getListOfMails().get(i).setText(decrypted);
                        }
                    }
                    account.setListOfMails(receivedMessage.getListOfMails());
                    return true;
                } else {
                    System.out.println("Nie udalo sie pobrac maili");
                    return false;
                }
            case "DeleteAccount":
                if (receivedMessage.isStatus()) {
                    System.out.println("Usunieto");
                    return true;
                } else {
                    System.out.println("Nie udalo sie usunac");
                    return false;
                }
            case "DeleteMail":
                if (receivedMessage.isStatus()) {
                    account.deleteMail(receivedMessage);
                    System.out.println("Usunieto");
                    return true;
                } else {
                    System.out.println("Nie udalo sie usunac");
                    return false;
                }
            case "ChangePassword":
                if (receivedMessage.isStatus()) {
                    account.setPassword(sendMessage.getNewPassword());
                    System.out.println("Zmieniono haslo");
                    return true;
                } else {
                    System.out.println("Nie udalo sie zmienic hasla");
                    return false;
                }
            case "Fail":
                System.out.println("Server fatal error");
                return false;
            default:
                System.out.println("Bład");
                return false;
        }
    }
}

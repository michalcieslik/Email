package Comunication;

public class ChangePassword extends Message {
    private String newpassword;

    public ChangePassword(String alert, String account, String password, boolean status, String newpassword) {
        super(alert, account, password, status);
        this.newpassword = newpassword;
    }

    @Override
    public String getNewPassword() {
        return newpassword;
    }
}

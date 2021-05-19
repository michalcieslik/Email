package GUI;


import Comunication.Security;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ChangePasswordAlert {
    String password = null;


    public String display(String oldPassword) throws Exception {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ChangePassword");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangePasswordAlertScene.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ChangePasswordAlertController changePasswordAlertController = fxmlLoader.getController();
        changePasswordAlertController.closeButton.setOnAction(e -> window.close());
        changePasswordAlertController.applyButton.setOnAction(e -> {
            try {
                String encryptedOldPasswordTF = Security.encrypt(changePasswordAlertController.oldPasswordTextField.getText());
                String encryptedNewPassword = Security.encrypt(changePasswordAlertController.newPasswordTextField.getText());
                if (!changePasswordAlertController.newPasswordTextField.getText().equals("") && !changePasswordAlertController.confirmNewPasswordTextField.getText().equals("")
                        && !changePasswordAlertController.oldPasswordTextField.getText().equals("") && encryptedOldPasswordTF.equals(oldPassword)) {
                    if (changePasswordAlertController.newPasswordTextField.getText().equals(changePasswordAlertController.confirmNewPasswordTextField.getText())) {
                        password = changePasswordAlertController.newPasswordTextField.getText();
                        window.close();
                    } else {
                        changePasswordAlertController.alertLabel.setText("Passwords are no identical.");
                    }
                } else {
                    changePasswordAlertController.alertLabel.setText("Please complete all text boxes.");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        Scene scene = new Scene(root, 300, 325);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
        return password;
    }

}

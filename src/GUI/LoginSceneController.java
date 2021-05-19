package GUI;

import Client.Client;
import Comunication.Message;
import Comunication.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginSceneController {
    public Client client;
    public Button loginButton;
    public Button registerButton;
    public Label loginLabel;
    public Label passwordLabel;
    public TextField loginTextField;
    public TextField passwordTextField;
    public Label alertLabel;
    private final String backOfTheAddress = "@poczta.pl";

    public void initialize() {
        loginTextField.textProperty().addListener((obs, oldText, newText) -> {
            // do what you need with newText here
            if(newText.equals(""))
                return;
            if (newText.endsWith(backOfTheAddress)) {
                return;
            }
            if ((newText.contains(backOfTheAddress) && !newText.endsWith(backOfTheAddress)) || (!newText.contains(backOfTheAddress) && oldText.endsWith(backOfTheAddress))) {
                loginTextField.setText(oldText);
                return;
            }
            if (!newText.contains(backOfTheAddress)) {
                loginTextField.setText(newText + backOfTheAddress);
                return;
            }
        });
    }

    public void registerClick(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterScene.fxml")));
        //bierzemy scene głowna
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 800, 500));
        window.show();

    }

    public void loginClick(ActionEvent actionEvent) throws Exception {
        if (!loginTextField.getText().equals("") && !passwordTextField.getText().equals("")) {
            try {
                client = new Client(6666, "192.168.178.69");
                String encryptedPassword = Security.encrypt(passwordTextField.getText());
                Message message = new Message("LogIn", loginTextField.getText(), encryptedPassword, true);
                client.openConnection();
                client.send(message);
                if (client.read()) {
                    client.getAccount().setEmailAddress(loginTextField.getText());
                    client.getAccount().setPassword(encryptedPassword);
                    message = new Message("SendMails", client.getAccount().getEmailAddress(), encryptedPassword, true);
                    client.send(message);
                    client.read();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    MainPageController mainPageController = fxmlLoader.getController();
                    mainPageController.setClient(client);
                    mainPageController.refreshLabels();
                    mainPageController.loadListview();
                    //bierzemy scene głowna
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(new Scene(root, 800, 500));
                    window.show();
                } else {
                    new Alert().display("Wrong account or password!");
                    refreshLabels();
                }
            } catch (IOException e) {
                new Alert().display("Cannot connect to the server!");
            }
        } else {
            new Alert().display("Please complete empty boxes!");
            refreshLabels();
        }
    }

    public void refreshLabels() {
        loginTextField.clear();
        passwordTextField.clear();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void loginKeyPressed(KeyEvent keyEvent) {
        if (loginTextField.getCaretPosition() == loginTextField.getText().length()){
            loginTextField.positionCaret(loginTextField.getText().length() - backOfTheAddress.length());
        }
    }
}

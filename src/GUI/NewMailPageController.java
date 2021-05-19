package GUI;

import Client.Client;
import Comunication.Mail;
import Comunication.Message;
import Comunication.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class NewMailPageController {
    public Client client;
    public Button goBackButton;
    public Button sendButton;
    public Button logOutButton;
    public TextField fromTextField;
    public TextField toTextField;
    public TextField titleTextField;
    public TextArea textAreaField;

    public void setClient(Client client) {
        this.client = client;
        Tooltip tooltip = new Tooltip("Go Back");
        goBackButton.setTooltip(tooltip);
        tooltip = new Tooltip("Send");
        sendButton.setTooltip(tooltip);
        tooltip = new Tooltip("Log Out");
        logOutButton.setTooltip(tooltip);
    }

    public void goBackButtonClicked(ActionEvent actionEvent) throws IOException {
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
    }

    public void sendButtonClicked(ActionEvent actionEvent) throws Exception {
        if (!toTextField.getText().isEmpty() && !fromTextField.getText().equals(toTextField.getText())) {
            String reciver = toTextField.getText();
            String title = titleTextField.getText();
            String date = getData();
            String text = Security.encrypt(textAreaField.getText());
            Message message = new Mail("Mail", reciver, null, true,
                    client.getAccount().getEmailAddress(), -1, title, date, text);
            try {
                client.send(message);
                if (client.read()) {
                    new Alert().display("Email successfully sent!");
                    refreshLabels();
                } else
                    new Alert().display("No account found!");
            } catch (IOException e) {
                new Alert().display("Connection with the server has been lost!");
            }
        } else {
            new Alert().display("Incorrect sender!");
        }

    }

    public void logOutButtonClicked(ActionEvent actionEvent) {
        try {
            client.closeConnection();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginScene.fxml")));
            //bierzemy scene głowna
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 800, 500));
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setFromTextField() {
        fromTextField.setText(client.getAccount().getEmailAddress());
        fromTextField.setEditable(false);
        fromTextField.setDisable(true);
    }

    public String getData() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }

    public void refreshLabels() {
        toTextField.setText("");
        titleTextField.setText("");
        textAreaField.setText("");
    }
}

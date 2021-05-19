package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Alert {

    public void display(String message) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Alert");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AlertScene.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AlertController alertController = fxmlLoader.getController();
        alertController.alertLabel.setText(message);
        alertController.okButton.setOnAction(e -> window.close());
        Scene scene = new Scene(root, 300,100);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }
}

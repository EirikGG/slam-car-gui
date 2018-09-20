package GUI.Controller.Login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import static GUI.SdcStart.sceneTitle;

/**
 * Controls login screen, gets credentials and changes screen.
 *
 * @author Eirik G. Gustafsson
 * @version 20.09.2018
 */
public class Login {
    // Manages credentials.
    private Credentials credentials;
    // Username field.
    @FXML private JFXTextField usernameField;
    // Password field.
    @FXML private JFXPasswordField passwordField;
    // Message label.
    @FXML private Label warningLabel;

    /**
     * Creates an instance of credentials.
     */
    public Login() {
        this.credentials = new Credentials();
    }

    /**
     * Handles login attempts, gets login info and compares using the Credentials class.
     *
     * @param event Trigger event, login button or enter press.
     */
    @FXML private void handleLoginBtn(ActionEvent event) throws Exception {
        // Gets password and username from login screen.
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();

        // Checks if the username is accepted, if it is, changes scene.
        if (this.credentials.doCheckCredentials(username, password)) {
            doChangeScene(event);
        } else {
            this.warningLabel.setText(sceneTitle);
        }
    }


    private void doChangeScene(ActionEvent event) throws Exception{
        // Gets the stage from the event.
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        try {
            // Gets the new root from FXML file.
            Parent root = FXMLLoader.load(getClass().getResource("../../Scenes/ControlSys.fxml"));

            // Sets the new stage.
            stage.setTitle("Autonomous car 3000");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }
}

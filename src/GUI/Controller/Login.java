package GUI.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;


public class Login {
    // List of username and passwords.
    private HashMap<String, String> credentials;

    /**
     * Creates a new HashMap, and fills it.
     */
    public Login() {
        this.credentials = new HashMap();
        doFillCredentials(this.credentials);
    }

    /**
     * Compares entered pw and username with HashMap.
     * @return True if username and password is correct.
     */
    private boolean doCheckPassword(String username, String password) {
        // Setts true if password and username is correct.
        boolean isCorrect = false;

        // Gets password with username.
        String password2 = this.credentials.get(username);

        // Checks password vs stored value.
        if (null != password2 && password2 == password) {
            isCorrect = true;
        }

        // Checks password and username vs hashmap.
        return isCorrect;
    }

    /**
     *
     * @param primaryStage
     * @throws Exception Cant find fxml file.
     */
    private void doChangeScene(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/ControllSys.fxml"));
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }

    /**
     * Fills the hashmap with passwords and usernames.
     */
    private void doFillCredentials(HashMap credentials) {
        credentials.put("Vebjørn", "admin");
        credentials.put("Ole", "admin");
        credentials.put("Sivert", "admin");
        credentials.put("Eirik", "admin");
    }
}

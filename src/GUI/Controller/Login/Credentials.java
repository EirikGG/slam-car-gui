package GUI.Controller.Login;

import java.util.HashMap;

/**
 * Manages login credentials.
 *
 * @author Eirik G. Gustafsson
 * @version 20.09.2018
 */

public class Credentials {
    // List of username and passwords.
    private HashMap<String, String> credentials;

    /**
     * Creates a new map and fills it.
     */
    public Credentials() {
        this.credentials = new HashMap<>();
        doFillCredentials();
    }

    /**
     * Returns true if the password and username entered as a param matches stored password and username.
     *
     * @param username Username as a string.
     * @param password Password as a string.
     * @return True if username and password is in map.
     */
    public boolean doCheckCredentials(String username, String password) {
        boolean isMatch = false;

        String storedPassword = getPassword(username);

        // Compares passwords.
        if (storedPassword != null && password.equals(storedPassword)) {
            isMatch = true;
        }

        return isMatch;
    }

    /**
     * Returns password associated with the key.
     *
     * @return Returns value to which the key is mapped, null if this map contains no mapping for the key.
     */
    private String getPassword(String username) {
        return this.credentials.get(username);
    }

    /**
     * Fills the hashmap with passwords and usernames.
     */
    private void doFillCredentials() {
        this.credentials.put("Vebjørn", "admin");
        this.credentials.put("Ole", "admin");
        this.credentials.put("Sivert", "admin");
        this.credentials.put("Eirik", "admin");
    }
}

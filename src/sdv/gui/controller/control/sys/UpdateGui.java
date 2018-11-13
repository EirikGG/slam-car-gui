package sdv.gui.controller.control.sys;

import javafx.scene.control.Label;
import sdv.coupling.CommIn;

/**
 * Class takes labels and updates their status according to what the backend is doing.
 * @author Eirik G. Gustafsson
 * @version 13.11.2018.
 */
public class UpdateGui extends Thread{
    // Comunication interface.
    private CommIn commIn;
    // Connected label.
    private Label connectedLabel;

    /**
     * Populates the fields.
     * @param connectedLabel
     */
    public UpdateGui(CommIn commIn, Label connectedLabel) {
        this.commIn = commIn;
        this.connectedLabel = connectedLabel;
    }

    @Override
    public void run() {

    }
}

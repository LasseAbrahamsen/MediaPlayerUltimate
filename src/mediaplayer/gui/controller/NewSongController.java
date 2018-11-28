/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer.gui.controller;

import java.awt.Component;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author a
 */
public class NewSongController implements Initializable {

    @FXML
    private Label testLabel;
    @FXML
    private TextField textfieldTitle;
    @FXML
    private TextField textfieldArtist;
    @FXML
    private TextField textfieldFile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        testLabel.setText("You're Not Playing a Song :)");
    }
    
    @FXML
    private void openSong(ActionEvent event) {
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        Component aComponent = null;
        File file = fc.getSelectedFile();
        //In response to a button click:
        int returnVal = fc.showOpenDialog(aComponent);
        textfieldFile.setText("Implement this");
    }
    
}

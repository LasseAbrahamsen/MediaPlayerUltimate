/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author a
 */
public class mainController implements Initializable {

    @FXML private Label nowPlayingLabel;
    @FXML
    private Button button;
    @FXML
    private Button btn1;
    
    @FXML
    private void openNewSong(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("gui/view/newSong.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/view/newSong.fxml"));
        //Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    nowPlayingLabel.setText("You're Not Playing a Song :)");
    }
    
}

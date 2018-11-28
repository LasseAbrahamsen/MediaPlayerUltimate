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
//import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mediaplayer.dal.SongDAO;

/**
 *
 * @author a
 */
public class mainController implements Initializable {
    
    //DBConnect test = new DBConnect();
    @FXML private Label nowPlayingLabel;
    @FXML private ListView<?> playlistView;
    @FXML private ListView<?> songView;
    @FXML private TextField TextFieldFilter;
    
    @FXML
    private void openNewSong(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/mediaplayer/gui/view/newSong.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/view/newSong.fxml"));
        //Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
        //((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    private void openNewPlaylist(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/mediaplayer/gui/view/newPlaylist.fxml"));
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
    private void handleButtonAction(ActionEvent event) throws IOException {
        //test.getItemsFromList();
        nowPlayingLabel.setText("You're Not Playing a Song :)");
    }
    
    @FXML
    private void testCreateSong(ActionEvent event) throws IOException {
        SongDAO test = new SongDAO();
        test.createSong("Megan", "Megan", "Pop", 2018, 10.01);
    }
    
    @FXML
    private void exitAction(ActionEvent event) throws InterruptedException {
        System.exit(0);
    }
}

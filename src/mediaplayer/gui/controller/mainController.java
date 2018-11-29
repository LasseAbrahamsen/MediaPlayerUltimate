/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer.gui.controller;

import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import mediaplayer.be.Song;
import mediaplayer.dal.SongDAO;
import mediaplayer.gui.model.Model;


/**
 *
 * @author a
 */
public class mainController implements Initializable {
    
    //DBConnect test = new DBConnect();
    @FXML private Label nowPlayingLabel;
    @FXML private ListView<?> listviewPlaylist;
    @FXML private ListView<Song> listviewSongs;
    @FXML private TableView<?> tableViewSongs;
    @FXML private TextField TextFieldFilter;
    @FXML private Button button;
    @FXML private MediaView MediaView;
    
    private Media media;
    private MediaPlayer mediaPlayer;
    Model model = new Model();
    
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
        listviewSongs.setItems(model.getSongs());
        //use tableviewtableViewSongs.set;
    }    

    private void handleButtonAction(ActionEvent event) {
        //test.getItemsFromList();
        nowPlayingLabel.setText("You're Not Playing a Song :)");
    }
    
    @FXML
    private void testCreateSong(ActionEvent event) throws IOException {
        SongDAO test = new SongDAO();
        test.createSong("something", "Lasse", "Pop", 2018, 13.38);
    }
    
    @FXML
    public void testPlaySong(ActionEvent event)  {
        String path = new File("src/life.mp3").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        MediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }
    
    
    @FXML
    public void exitAction(ActionEvent event) throws InterruptedException {
        System.exit(0);
    }
    
    @FXML
    public void loadSongList(ActionEvent event) {
        model.loadAllSongs();
        
    }
    //Song clickedSong = listviewSongs.getSelectionModel().getSelectedItem();
}

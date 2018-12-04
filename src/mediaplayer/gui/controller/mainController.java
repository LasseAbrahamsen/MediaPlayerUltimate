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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import mediaplayer.be.Playlist;
import mediaplayer.be.Song;
import mediaplayer.bll.Manager;
import mediaplayer.dal.SongDAO;
import mediaplayer.dal.PlaylistDAO;
import mediaplayer.gui.model.ModelPlaylist;
import mediaplayer.gui.model.ModelSong;


/**
 *
 * @author a
 */
public class mainController implements Initializable {
    
    private ObservableList<Song> observableListSong;
    private ObservableList<Playlist> observableListPlaylist;
    
    @FXML private Label nowPlayingLabel;
    @FXML private TableView<Song> tableViewSongs;
    @FXML private TableColumn<Song, String> titleCol;
    @FXML private TableColumn<Song, String> artistCol;
    @FXML private TableColumn<Song, String> genreCol;
    @FXML private TableColumn<Song, Integer> yearCol;
    @FXML private TableColumn<Song, Double> lengthCol;
    @FXML private TableView<Playlist> playlistTable;
    @FXML private TableColumn<Playlist, String> playlistNameCol;
    @FXML private TableColumn<Playlist, Integer> playlistAmountCol;
    @FXML private TableColumn<Playlist, Double> playlistTimeCol;
    @FXML private TableView<Song> SongsOnPlaylistTable;
    @FXML private TableColumn<Song, Integer> songsOnPlaylistID;
    @FXML private TableColumn<Song, String> songsOnPlaylistName;
    @FXML private TextField textFieldFilter;
    @FXML private MediaView MediaView;
    
    private Media media;
    private MediaPlayer mediaPlayer;
    ModelSong songModel = new ModelSong();
    ModelPlaylist playlistModel = new ModelPlaylist();
    
    @FXML
    private void openNewSong(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/mediaplayer/gui/view/newSong.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void openNewPlaylist(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/mediaplayer/gui/view/newPlaylist.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListSong = songModel.getSongs();
        observableListPlaylist = playlistModel.getPlaylists();
        
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        
        tableViewSongs.setItems(observableListSong);
        songModel.loadAllSongs();
        
        playlistNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        playlistTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        playlistTable.setItems(observableListPlaylist);
        
        songsOnPlaylistID.setCellValueFactory(new PropertyValueFactory<>("id"));
        songsOnPlaylistName.setCellValueFactory(new PropertyValueFactory<>("title"));
        SongsOnPlaylistTable.setItems(observableListSong);
    }    
    
    @FXML
    private void testCreateSong(ActionEvent event) throws IOException {
        SongDAO test = new SongDAO();
        test.createSong("something", "Lasse", "Pop", 2018, 13.38);
    }
    
    @FXML
    public void testPlaySong(ActionEvent event)  {
        String path = new File("src/metallica.mp3").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        MediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }
    
    @FXML
    public void exit(ActionEvent event) throws InterruptedException {
        System.exit(0);
    }
    
    @FXML
    public void loadSongList(ActionEvent event) {
        songModel.loadAllSongs();
        playlistModel.loadAllPlaylists();
        songModel.fillSongsOnPlaylist(1);
    }
    
    @FXML
    public void deleteSelectedSong(ActionEvent event) {
        Song clickedSong = tableViewSongs.getSelectionModel().getSelectedItem();
        songModel.deleteSong(clickedSong);
    }
    
    @FXML
    public void deleteSelectedPlaylist(ActionEvent event) {
        Playlist clickedPlaylist = playlistTable.getSelectionModel().getSelectedItem();
        playlistModel.deletePlaylist(clickedPlaylist);
    }
    
    @FXML
    public void searchSong(ActionEvent event) {
        Manager testManager = new Manager();
        testManager.searchinFilter(observableListSong, textFieldFilter.getText());
    }
    
    @FXML
    public void update(ActionEvent event) {
        Song clickedSong = tableViewSongs.getSelectionModel().getSelectedItem();
        songModel.updateSong(clickedSong);
    }
    
}

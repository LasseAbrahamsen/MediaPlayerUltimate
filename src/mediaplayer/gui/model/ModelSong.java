package mediaplayer.gui.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediaplayer.be.Song;
import mediaplayer.bll.Facade;

/**
 *
 * @author a
 */
public class ModelSong {

    Facade logicFacade = new Facade();
    private ObservableList<Song> songs = FXCollections.observableArrayList();

    public ObservableList<Song> getSongs() {
        return songs;
    }
    
    public void deleteSong(Song s)
    {
        songs.remove(s);
        logicFacade.deleteSong(s); // Remove from DB
    }
    
    public void loadAllSongs()
    {
        songs.clear();
        songs.addAll(logicFacade.getAllSongs());
    }
    
    public List<Song> fillSongsOnPlaylist(int id) {
        return logicFacade.fillSongsOnPlaylist(id);
    }
    
    public Song updateSong(Song song, String title, String artist, String genre, int year, double length, String location) {
        return logicFacade.updateSong(song, title, artist, genre, year, length, location);
    }
    
    public Song createSong(String title, String artist, String genre, int year, double length, String location) {
        return logicFacade.createSong(title, artist, genre, year, length, location);
    }
}

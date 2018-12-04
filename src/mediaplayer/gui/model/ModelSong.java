package mediaplayer.gui.model;

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
}

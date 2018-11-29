package mediaplayer.gui.model;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediaplayer.be.Song;
import mediaplayer.dal.SongDAO;

/**
 *
 * @author a
 */
public class Model {

    SongDAO sdao = new SongDAO();
    private ObservableList<Song> songs = FXCollections.observableArrayList();

    public ObservableList<Song> getSongs() {
        return songs;
    }
    /*
    public void deletePerson(Song s)
    {
        persons.remove(s);
        sdao.deletePerson(s); // Remove from DB
    }*/
    
    public void loadAllSongs()
    {
        songs.clear();
        songs.addAll(sdao.getAllSongs());
    }
}

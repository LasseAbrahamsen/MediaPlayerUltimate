package mediaplayer.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediaplayer.be.Playlist;
import mediaplayer.bll.Facade;

/**
 *
 * @author a
 */
public class ModelPlaylist {
    
    Facade logicFacade = new Facade();
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }
    
    public void deletePlaylist(Playlist p)
    {
        playlists.remove(p);
        logicFacade.deletePlaylist(p); // Remove from DB
    }
    
    public void loadAllPlaylists()
    {
        playlists.clear();
        playlists.addAll(logicFacade.getAllPlaylists());
    }
    
    public Playlist createPlaylist(String name) {
        return logicFacade.createPlaylist(name);
    }
    
}

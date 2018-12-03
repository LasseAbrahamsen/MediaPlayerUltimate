package mediaplayer.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediaplayer.be.Playlist;
import mediaplayer.dal.PlaylistDAO;

/**
 *
 * @author a
 */
public class ModelPlaylist {
    
    PlaylistDAO pdao = new PlaylistDAO();
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }
    
    public void deletePlaylist(Playlist p)
    {
        playlists.remove(p);
        pdao.deletePlaylist(p); // Remove from DB
    }
    
    public void loadAllPlaylists()
    {
        playlists.clear();
        playlists.addAll(pdao.getAllPlaylists());
    }
    
}

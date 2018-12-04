package mediaplayer.bll;

import java.util.List;
import mediaplayer.be.Playlist;
import mediaplayer.be.Song;
import mediaplayer.dal.PlaylistDAO;
import mediaplayer.dal.SongDAO;

public class Facade {
    
    SongDAO sdao = new SongDAO();
    PlaylistDAO pdao = new PlaylistDAO();
    
    public void deleteSong(Song s) {
        sdao.deleteSong(s);
    }
    
    public List<Song> getAllSongs() {
        return sdao.getAllSongs();
    }
    
    public void deletePlaylist(Playlist p) {
        pdao.deletePlaylist(p);
    }
    
    public List<Playlist> getAllPlaylists() {
        return pdao.getAllPlaylists();
    }
    
    
    
    
}

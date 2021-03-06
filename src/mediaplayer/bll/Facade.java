package mediaplayer.bll;

import java.util.List;
import mediaplayer.be.Playlist;
import mediaplayer.be.Song;
import mediaplayer.dal.PlaylistDAO;
import mediaplayer.dal.PlaylistSongDAO;
import mediaplayer.dal.SongDAO;

public class Facade {
    
    SongDAO sdao = new SongDAO();
    PlaylistDAO pdao = new PlaylistDAO();
    PlaylistSongDAO psdao = new PlaylistSongDAO();
    
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
    
    public Playlist createPlaylist(String name) {
        return pdao.createPlaylist(name);
    }
    
    public List<Song> fillSongsOnPlaylist(int id) {
        return psdao.fillSongsOnPlaylist(id);
    }
    
    public Song updateSong(Song song, String title, String artist, String genre, int year, double length, String location) {
        return sdao.updateSong(song, title, artist, genre, year, length, location);
    }
    
    public Song createSong(String title, String artist, String genre, int year, double length, String location) {
        return sdao.createSong(title, artist, genre, year, length, location);
    }
    
}

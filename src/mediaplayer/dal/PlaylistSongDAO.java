package mediaplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mediaplayer.be.Playlist;
import mediaplayer.be.Song;

/**
 *
 * @author a
 */
public class PlaylistSongDAO {
    
    SQLServerDataSource ds;
    
    public PlaylistSongDAO() {
        this.ds = new SQLServerDataSource();
        DBConnect connectionInfo = new DBConnect(); 
        List<String> loginInfo;
        try {
            loginInfo = connectionInfo.getDatabaseInfo();
            ds.setDatabaseName(loginInfo.get(0));
            ds.setUser(loginInfo.get(1));
            ds.setPassword(loginInfo.get(2));
            ds.setPortNumber(Integer.parseInt(loginInfo.get(3)));
            ds.setServerName(loginInfo.get(4));     
        } catch (IOException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //It fills the Playlist Songs table.
    public List<Song> fillSongsOnPlaylist(int id) {
        List<Song> songsList = new ArrayList();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT * FROM MusicTableV2 INNER JOIN playlistSongsTable ON MusicTableV2.id=playlistSongsTable.PSid ORDER by locationinlistID desc";
            PreparedStatement preparedStmt = con.prepareStatement(sqlStatement);
            ResultSet rs = preparedStmt.executeQuery();
            while(rs.next()) {
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                int year = rs.getInt("year");
                double length = rs.getDouble("length");
                int idd = rs.getInt("id");
                String location = rs.getString("location");
                Song s = new Song(title, artist, genre, year, length, idd, location);
                songsList.add(s);
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songsList;
    }
    
    //Removes a song from playlist songs table.
    public void deletePlaylistSong(Song songToDelete) {
        try (Connection con = ds.getConnection()) {
            String query = "DELETE from playlistSongsTable WHERE SongID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, songToDelete.getId());
            pstmt.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    //Puts a song from the song list into songs in playlist table.
    public Song addToPlaylist(Playlist playlist, Song song) {
        String sql = "INSERT INTO playlistSongsTable(PSid,songname,locationlistID) VALUES (?,?,?)";
        int Id = -1;
        try (Connection con = ds.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            Id = getNewestSongInPlaylist(playlist.getID()) + 1;
            pstmt.setInt(1, playlist.getID());
            pstmt.setInt(2, song.getId());
            pstmt.setInt(3, Id);
            pstmt.execute();
            song.setLocationinlist(Id);
            return song;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return null;

        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    //returns last ID in the songs in playlist list.
    private int getNewestSongInPlaylist(int id) {
        int lastID = -1;
        try (Connection con = ds.getConnection()) {
            String query = "SELECT TOP(1) * FROM playlistSongsTable WHERE PSid=? ORDER by locationinlistID desc";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, id);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                lastID = rs.getInt("locationInListID");
            }
            System.out.println(lastID);
            return lastID;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return lastID;
        } catch (SQLException ex) {
            System.out.println(ex);
            return lastID;
        }
    }
    
    //removes song from selected playlist
    public void deleteFromPlaylist(Playlist play) {
        try (Connection con = ds.getConnection()) {
            String query = "DELETE from playlistSongsTable WHERE songname=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, play.getID());
            preparedStmt.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    //Should be able to move songs up and down the songs in playlist list.
    public void editSongPosition(Playlist selectedItem, Song selected, Song move) {
        try (Connection con = ds.getConnection()) {
            String query = "UPDATE playlistSongsTable set locationinlistID=? WHERE PSid=? AND songname=? AND locationinlistID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, move.getLocationinlist());
            preparedStmt.setInt(2, selectedItem.getID());
            preparedStmt.setInt(3, selected.getId());
            preparedStmt.setInt(4, selected.getLocationinlist());
            preparedStmt.setInt(1, selected.getLocationinlist());
            preparedStmt.setInt(2, selectedItem.getID());
            preparedStmt.setInt(3, move.getId());
            preparedStmt.setInt(4, move.getLocationinlist());
            preparedStmt.execute();
            int getLocation = selected.getLocationinlist();
            selected.setLocationinlist(move.getLocationinlist());
            move.setLocationinlist(getLocation);
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void removeSongFromPlaylist(Playlist selectedItem, Song selectedSong) {
        try (Connection con = ds.getConnection()) {
            String query = "DELETE from playlistSongsTable WHERE PSid=? AND songname=? AND locationinlistID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, selectedItem.getID());
            preparedStmt.setInt(2, selectedSong.getId());
            preparedStmt.setInt(3, selectedSong.getLocationinlist());
            preparedStmt.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}

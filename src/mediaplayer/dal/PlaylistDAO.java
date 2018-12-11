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
public class PlaylistDAO {
    
    PlaylistSongDAO playlistSongDAO = new PlaylistSongDAO(); 
    SQLServerDataSource ds;
    
    public PlaylistDAO() {
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
    
    public Playlist createPlaylist(String name) {
        Playlist p = null;
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO PlaylistTable (name) VALUES(?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.execute();
            p = new Playlist(name, 0, 0, getLastIDPlaylist());
            return p;
        } catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    //gets the last ID in the list for playlist creation.
    public int getLastIDPlaylist() {
        int lastID = -1;
        try (Connection con = ds.getConnection()){
            PreparedStatement pstmt = con.prepareStatement("SELECT TOP(1) * FROM PlaylistTable ORDER by PlaylistID desc");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                lastID = rs.getInt("id");
            }
            return lastID;
        }
        catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lastID;
        }
        catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lastID;
        }
    }
    
    public void updatePlaylist(Playlist p) {
        try (Connection con = ds.getConnection()){
           String sql = "UPDATE PLAYLIST SET name=? WHERE PlaylistID=?";
           PreparedStatement stmt = con.prepareStatement(sql);
           stmt.setString(1, p.getName());
           stmt.execute();
        }
        catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletePlaylist(Playlist p) {
        try (Connection con = ds.getConnection()) {
            String sql = "DELETE FROM playlistTable WHERE PlaylistID=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, p.getID());
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Loads all playlists
    public List<Playlist> getAllPlaylists(){
        List<Playlist> playlists = new ArrayList();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT * FROM PlaylistTable";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("PlaylistID");
                List<Song> allSongs = playlistSongDAO.fillSongsOnPlaylist(id);
                Playlist p = new Playlist(name, allSongs.size(), countTime(allSongs), id);
                p.setSongList(allSongs);
                playlists.add(p);
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return playlists;
    }
    
    //This method is used to return the length of all songs in the playlist so it can be displayed in the GUI.
    private int countTime(List<Song> allSongs) {
        int time = 0;
        for (Song allSong : allSongs) {
            time += allSong.getLength();
        }
        return time;
    }
    
}

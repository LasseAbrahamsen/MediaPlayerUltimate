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

/**
 *
 * @author a
 */
public class PlaylistDAO {
    
    SQLServerDataSource ds;
    
    public PlaylistDAO() {
        this.ds = new SQLServerDataSource();
        DBConnect connectionInfo = new DBConnect(); 
        List<String> infoList; 
        try {
            infoList = connectionInfo.getDatabaseInfo();
            ds.setDatabaseName(infoList.get(0));
            ds.setUser(infoList.get(1));
            ds.setPassword(infoList.get(2));
            ds.setPortNumber(Integer.parseInt(infoList.get(3)));
            ds.setServerName(infoList.get(4));
            
        } catch (IOException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Playlist createPlaylist(String name, int amount, double time) {
        Playlist p = null;
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO MusicTableV2(name, amount, time) VALUES(?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            //stmt.setInt(1, s.getId());
            stmt.setString(1, name);
            stmt.setInt(2, amount);
            stmt.setDouble(3, time);
            stmt.execute();
            p = new Playlist(name, amount, time, getLastIDPlaylist());
            return p;
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public int getLastIDPlaylist() {
        int lastID = -1;
        try (Connection con = ds.getConnection()){
            PreparedStatement pstmt = con.prepareStatement("SELECT TOP(1) * FROM PLAYLIST ORDER by id desc");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                lastID = rs.getInt("id");
            }
            return lastID;
        }
        catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lastID;
        }
        catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lastID;
        }
    }
    
    public void updatePlaylist(Playlist p) {
        try (Connection con = ds.getConnection()){
           String sql = "UPDATE PLAYLIST SET name=?, amount=?, time=? "
                   + "WHERE id=?";
           PreparedStatement stmt = con.prepareStatement(sql);
           stmt.setString(1, p.getName());
           stmt.setInt(2, p.getAmount());
           stmt.setDouble(3, p.getTime());
           stmt.execute();
        }
        catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletePlaylist(Playlist p) {
        try (Connection con = ds.getConnection()) {
            String sql = "DELETE FROM MusicTableV2 WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, p.getId());
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Song> getAllSongs(){
        List<Song> songs = new ArrayList();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT * FROM MusicTableV2";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()) {
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                int year = rs.getInt("year");
                double length = rs.getDouble("length");
                int id = rs.getInt("id");
                Song s = new Song(title, artist, genre, year, length, id);
                songs.add(s);
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songs;
    }
    
}

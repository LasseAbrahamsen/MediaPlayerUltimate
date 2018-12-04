package mediaplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javafx.collections.FXCollections;
import mediaplayer.be.Song;

/**
 *
 * @author a
 */
public class SongDAO {
    
    SQLServerDataSource ds;
    
    public SongDAO() {
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
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Song createSong(String title, String artist, String genre, int year, double length) {
        Song s = null;
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO MusicTableV2(title, artist, genre, year, length) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, artist);
            stmt.setString(3, genre);
            stmt.setInt(4, year);
            stmt.setDouble(5, length);
            stmt.execute();
            s = new Song(title, artist, genre, year, length, getLastID());
            return s;
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    public int getLastID() {
        int lastID = -1;
        try (Connection con = ds.getConnection()){
            String sql = "SELECT TOP(1) * FROM MusicTableV2 ORDER by id desc";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            ResultSet rs = preparedStmt.executeQuery();
            while(rs.next()) {
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
    
    public void updateSong(Song s)
    {
        try (Connection con = ds.getConnection()) {
           String sql = "UPDATE MusicTableV2 SET title=?, artist=?, genre=?, year=?, length=? "
                   + "WHERE id=?";
           PreparedStatement stmt = con.prepareStatement(sql);
           stmt.setString(1, s.getTitle());
           stmt.setString(2, s.getArtist());
           stmt.setString(3, s.getGenre());
           stmt.setInt(4, s.getYear());
           stmt.setDouble(5, s.getLength());
           stmt.setInt(6, s.getId());
           stmt.execute();
        }
        catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteSong(Song s) {
        try (Connection con = ds.getConnection()) {
            String sql = "DELETE FROM MusicTableV2 WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, s.getId());
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Song> getAllSongs() {
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

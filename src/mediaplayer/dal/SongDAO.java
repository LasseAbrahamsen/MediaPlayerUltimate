package mediaplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
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
    
    //The method creates a song with variables title, artist, genre, year of creation, length and location on the disc.
    public Song createSong(String title, String artist, String genre, int year, double length, String location) {
        Song s = null;
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO MusicTableV2(title, artist, genre, year, length, location) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, artist);
            stmt.setString(3, genre);
            stmt.setInt(4, year);
            stmt.setDouble(5, length);
            stmt.setString(6, location);
            stmt.execute();
            s = new Song(title, artist, genre, year, length, getLastID(), s.getLocation());
            return s;
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    //The method is for song creation. It gives us the last ID in the song list.
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
    
    //Updating the song, if the user wants to edit a song that already exists in the database.
    public Song updateSong(Song song, String title, String artist, String genre, int year, double length, String location) {
        try (Connection con = ds.getConnection()) {
            String query = "UPDATE MusicTableV2 set title=?, artist=?, genre=?, year=?, length=? WHERE id=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, title);
            preparedStmt.setString(2, artist);
            preparedStmt.setString(3, genre);
            preparedStmt.setInt(4, year);
            preparedStmt.setDouble(5, length);
            preparedStmt.setInt(6, song.getId());
            preparedStmt.executeUpdate();
            Song s = new Song(title, artist, genre, year, length, song.getId(), location);
            return s;
        }
        catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    //Deletes a song from the database.
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
    
    //returns a list of all the songs, used for the song list.
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
                String location = rs.getString("location");
                Song s = new Song(title, artist, genre, year, length, id, location);
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

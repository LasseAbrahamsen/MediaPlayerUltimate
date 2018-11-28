/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public SongDAO() throws IOException {
    this.ds = new SQLServerDataSource();
        DBConnect connectionInfo = new DBConnect();
        List<String> infoList = connectionInfo.getDatabaseInfo();
        /*
        ds.setDatabaseName(infoList.get(0));
        ds.setUser(infoList.get(1));
        ds.setPassword(infoList.get(2));
        ds.setPortNumber(Integer.parseInt(infoList.get(3)));
        ds.setServerName(infoList.get(4));*/
        ds.setDatabaseName(infoList.get(0));
        ds.setUser(infoList.get(1));
        ds.setPassword(infoList.get(2));
        ds.setPortNumber(Integer.parseInt(infoList.get(3)));
        ds.setServerName(infoList.get(4));
    }
    
        public Song createSong(String title, String artist, String genre, int year, double length) {
        Song s = null;
        try (Connection con = ds.getConnection()) {
            String sql = "INSERT INTO MusicTable(title, artist, genre, year, length) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, artist);
            stmt.setString(3, genre);
            stmt.setInt(4, year);
            stmt.setDouble(5, length);
            stmt.execute();
            s = new Song(title, artist, genre, year, length);
            return s;
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    
}

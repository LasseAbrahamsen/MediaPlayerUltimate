/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    // brain is dead :D
    public List<Song> fillSongsOnPlaylist() {
        List<Song> songs = new ArrayList();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT * FROM MusicTableV2 INNER JOIN playlistSongsTable ON MusicTableV2.id=playlistSongsTable.id";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while(rs.next()) {
                
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songs;
    }
    
    
    
}

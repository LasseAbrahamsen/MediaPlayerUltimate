package mediaplayer.be;

import java.util.List;

/**
 *
 * @author a
 */
public class Playlist {
    
    private List<Song> songList;
    private String name;
    private int amount;
    private double time;
    private int id;

    public Playlist(String name, int amount, double time, int id) {
        this.name = name;
        this.amount = amount;
        this.time = time;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Playlist{" + "name=" + name + '}';
    }

    public int getID() {
        return id;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}

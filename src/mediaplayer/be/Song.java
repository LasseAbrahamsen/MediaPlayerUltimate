package mediaplayer.be;

/**
 *
 * @author a
 */
public class Song {
    private String title;
    private String artist;
    private String genre;
    private int year;
    private double length;
    private int id;
   
    public Song(String title, String artist, String genre, int year, double length, int id) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        this.length = length;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Song{" + "title=" + title + ", artist=" + artist + ", genre=" + genre + ", year=" + year + ", length=" + length + ", id=" + id + '}';
    }
    
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
    
    
    
}

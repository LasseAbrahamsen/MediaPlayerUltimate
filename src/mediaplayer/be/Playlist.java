package mediaplayer.be;

/**
 *
 * @author a
 */
public class Playlist {
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

    public int getID() {
        return id;
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

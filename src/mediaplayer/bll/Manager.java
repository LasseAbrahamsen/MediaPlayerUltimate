package mediaplayer.bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediaplayer.be.Song;

/**
 *
 * @author a
 */
public class Manager {
    
    private ObservableList<Song> songFilter = FXCollections.observableArrayList();
 
    public ObservableList<Song> searchinFilter(ObservableList<Song> items, String textInput) {
        songFilter.clear();
        for (Song item : items) {
            if (item.getTitle().toLowerCase().startsWith(textInput.toLowerCase())) {
                songFilter.add(item);
            }
        }
        return songFilter;
    }
}

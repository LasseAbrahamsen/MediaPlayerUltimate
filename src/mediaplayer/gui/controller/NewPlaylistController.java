package mediaplayer.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mediaplayer.be.Playlist;
import mediaplayer.dal.PlaylistDAO;
import mediaplayer.gui.model.ModelPlaylist;
import mediaplayer.gui.model.ModelSong;

/**
 * FXML Controller class
 *
 * @author a
 */
public class NewPlaylistController implements Initializable {
    
    ModelSong songModel = new ModelSong();
    ModelPlaylist playlistModel = new ModelPlaylist();

    @FXML
    private TextField textfieldplaylistName;
    
    private boolean isEditing = false;
    private Playlist editingList;
    NewPlaylistController controller1;
    
    @FXML private Label playlistLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    private void createPlaylist(ActionEvent event) throws IOException {
        playlistModel.createPlaylist(textfieldplaylistName.getText());
    }
    
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) textfieldplaylistName.getScene().getWindow();
        stage.close();
    }
    
    public void setPlaylistInfo(Playlist selectedItem) {
        isEditing = true;
        editingList = selectedItem;
        textfieldplaylistName.setText(selectedItem.getName());
    }
    
    //?
    public void setController(NewPlaylistController controller1) {
        this.controller1 = controller1;
        if (isEditing) {
            playlistLabel.setText("Editing Playlist");
        } else {
            playlistLabel.setText("Create Playlist");
        }
    }

    
}

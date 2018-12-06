package mediaplayer.gui.controller;

import java.io.File;
import static java.lang.Math.toIntExact;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import mediaplayer.be.Song;
import mediaplayer.gui.model.ModelSong;

/**
 * FXML Controller class
 *
 * @author a
 */
public class NewSongController implements Initializable {

    @FXML private Label testLabel;
    @FXML private TextField textfieldTitle;
    @FXML private TextField textfieldArtist;
    @FXML private TextField textfieldFile;
    
    private MediaPlayer mediaPlayer;
    private Media media;
    private Song songToAdd;
    private boolean isEditing;
    NewPlaylistController controller1;
    
    ModelSong songModel = new ModelSong();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void saveTheSong(ActionEvent event) {
        int length = toIntExact(Math.round(mediaPlayer.getMedia().getDuration().toSeconds())); 
        //int yearCreated = media.getMetadata(year);
        String name = textfieldTitle.getText();
        if (name != null && name.length() > 0 && name.length() < 50 && length > 0) {
            if (!isEditing) { 
                                    //title, artist, genre, year, length
                songModel.createSong(name, textfieldArtist.getText(), name, length, length);
                        //.createSong(name, textfieldArtist.getText(), genre, year, length);
                testLabel.setText("Created song");
            } else { 
                songModel.updateSong(songToAdd, name, textfieldArtist.getText(), name, length, length);
                testLabel.setText("Updated song");
            }
        } else {
            testLabel.setText("Doesn't work");
        }

        //controller1.refreshSongList(isEditing); // Refreshes the list in main window to reflect changes
    }

    @FXML
    private void pickaFile (ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop"));
        chooser.setDialogTitle("Select song ");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 and Wav files", "mp3", ".wav"); 
        chooser.setFileFilter(filter); 
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
            textfieldFile.setText(chooser.getSelectedFile().getAbsolutePath());
            mediaPlayer = new MediaPlayer(new Media(new File(chooser.getSelectedFile().getAbsolutePath()).toURI().toString())); // Sets up the media object in order to get time of the song
        }
    }

    public void setSongInfo (Song s) {
        isEditing = true;
        songToAdd = s;
        textfieldTitle.setText(s.getTitle());
        textfieldArtist.setText(s.getArtist());
        mediaPlayer = new MediaPlayer(new Media(new File(s.getArtist()).toURI().toString()));
    }
    
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) testLabel.getScene().getWindow();
        stage.close();
    }
    
    public void setController(NewPlaylistController controller1) {
        this.controller1 = controller1;
        if (isEditing) {
            testLabel.setText("Editing song");
        } else {
            testLabel.setText("Create song");
        }
    }
}

package mediaplayer.gui.controller;

import java.io.File;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void saveTheSong(ActionEvent event) {
        //implement this
    }

    @FXML
    private void chooseURL (ActionEvent event) {
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
    
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) testLabel.getScene().getWindow();
        stage.close();
    }
}

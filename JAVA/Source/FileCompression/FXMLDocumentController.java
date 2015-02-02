/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaFileCompress;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * 
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField compressInputField;
    @FXML
    private TextField decompressInputField;
    @FXML
    private TextField compressOutputField;
    @FXML
    private TextField decompressOutputField;
    @FXML
    private Button compressInputBrowse;
    @FXML
    private Button decompressInputBrowse;
    @FXML
    private Button compressOutputBrowse;
    @FXML
    private Button decompressOutputBrowse;
    @FXML
    private Button compressButton;
    @FXML
    private Button decompressButton;
    
    private File compressInputFile;
    private File compressOutputFile;
    private File decompressInputFile;
    private File decompressOutputFile;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        compressInputField.setEditable(false);
        compressInputField.setCursor(Cursor.DEFAULT);
        decompressInputField.setEditable(false);
        decompressInputField.setCursor(Cursor.DEFAULT);
        compressOutputField.setEditable(false);
        compressOutputField.setCursor(Cursor.DEFAULT);
        decompressOutputField.setEditable(false);
        decompressOutputField.setCursor(Cursor.DEFAULT);
        //---------COMPRESS GUI--------------------------------------
        compressButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                compressInputFile = new File(compressInputField.getText());
                compressOutputFile = new File(compressOutputField.getText());
                if((compressInputFile.exists()) && (compressOutputFile.exists())) {
                    compressButton.setText("Compressing...");
                    if(JavaFileCompress.compress(compressInputFile, compressOutputFile)){
                        compressButton.setText("Complete");
                    }
                }
            }
        });
        compressInputBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser inputBrowseWindow = new FileChooser();
                inputBrowseWindow.setTitle("Choose Input File");
                inputBrowseWindow.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"),
                                                                new FileChooser.ExtensionFilter("JAVA", "*.java"),
                                                                new FileChooser.ExtensionFilter("CPP", "*.cpp"),
                                                                new FileChooser.ExtensionFilter("C", "*.c"),
                                                                new FileChooser.ExtensionFilter("C#", "*.cs"),
                                                                new FileChooser.ExtensionFilter("HTML", "*.html"),
                                                                new FileChooser.ExtensionFilter("CSS", "*.css"),
                                                                new FileChooser.ExtensionFilter("JS", "*.js"));
                compressInputFile = inputBrowseWindow.showOpenDialog(JavaFileCompress.mainStage);
                if(compressInputFile.exists()){
                    compressInputField.setText(compressInputFile.getAbsolutePath());
                }
            }
        });
        compressOutputBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser outputBrowseWindow = new FileChooser();
                outputBrowseWindow.setTitle("Choose Output Location");
                outputBrowseWindow.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BIN", "*.bin"));
                compressOutputFile = outputBrowseWindow.showSaveDialog(JavaFileCompress.mainStage);
                if(!compressOutputFile.exists())
                {
                    try{
                        Files.createFile(compressOutputFile.toPath());
                    }catch(Exception ex){ex.printStackTrace(System.err);}
                }
                compressOutputField.setText(compressOutputFile.getAbsolutePath());
            }
        });
        //-------------END COMPRESS GUI----------------------------------------------
        //----------------DECOMPRESS GUI---------------------------------------------
        decompressButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                decompressInputFile = new File(decompressInputField.getText());
                decompressOutputFile = new File(decompressOutputField.getText());
                decompressButton.setText("Decompressing...");
                if(JavaFileCompress.decompress(decompressInputFile, decompressOutputFile)){
                    decompressButton.setText("Complete!");
                }
                
            }
        });
        decompressInputBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser inputBrowseWindow = new FileChooser();
                inputBrowseWindow.setTitle("Choose Input File");
                inputBrowseWindow.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BIN", "*.bin"));
                decompressInputFile = inputBrowseWindow.showOpenDialog(JavaFileCompress.mainStage);
                decompressInputField.setText(decompressInputFile.getAbsolutePath());
                
            }
        });
        decompressOutputBrowse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser outputBrowseWindow = new FileChooser();
                outputBrowseWindow.setTitle("Choose Output Location");
                outputBrowseWindow.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"),
                                                                new FileChooser.ExtensionFilter("JAVA", "*.java"),
                                                                new FileChooser.ExtensionFilter("CPP", "*.cpp"),
                                                                new FileChooser.ExtensionFilter("C", "*.c"),
                                                                new FileChooser.ExtensionFilter("C#", "*.cs"),
                                                                new FileChooser.ExtensionFilter("HTML", "*.html"),
                                                                new FileChooser.ExtensionFilter("CSS", "*.css"),
                                                                new FileChooser.ExtensionFilter("JS", "*.js"));
                decompressOutputFile = outputBrowseWindow.showSaveDialog(JavaFileCompress.mainStage);
                decompressOutputField.setText(decompressOutputFile.getAbsolutePath());
            }
        });
    }       
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.stage.Stage;
import unnatkrushipranali.UnnatKrushiPranali;

/**
 * FXML Controller class
 *
 * @author harshbitss
 */
public class LanguageSelectionController implements Initializable {
    @FXML
    private ComboBox<String> languageCombo;

    UnnatKrushiPranali ukp;
    private Stage dialogStage; 
    private boolean okClicked = false;

    /**
     * Initializes the controller class.
     */
    public LanguageSelectionController() throws SQLException, ClassNotFoundException, IOException, Exception {
        ukp=new UnnatKrushiPranali();
    }

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       languageCombo.getItems().clear();
       languageCombo.getItems().add("English-International");
       languageCombo.getItems().add("French");
       languageCombo.getItems().add("Hindi");
       languageCombo.getItems().add("Gujarati");
       languageCombo.getItems().add("Spanish");
       languageCombo.getItems().add("Italian");
       
        
    }    

    @FXML
    private void applyLanguage(ActionEvent event) throws IOException {
        
        int selectedIndex=languageCombo.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0)
        {
            
            String selectedLang = languageCombo.getSelectionModel().selectedItemProperty().getValue().toString(); 
            System.out.println("You Have Selected "+selectedLang+" Language");
            
               
            Dialogs.showInformationDialog(ukp.getPrimaryStage(),"You Have Selected "+selectedLang+" Language",
                    "Unnat Krushi Pranali : Language Selection",
                    "Confirmation of Selection");
            if(selectedLang.equalsIgnoreCase("hindi")){
                
                ukp.handleHindiLanguageSelection();
            }
            
            dialogStage.close();
        }
        else{
             Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please select a language from the list",
					"No Language is selected from a List", "No Selection");
        }
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        
        Dialogs.showInformationDialog(ukp.getPrimaryStage(), "By Selecting Appropriate language whole GUI will chnage to selected Language Support.",
                    "Unnat Krushi Pranali : Language Selection",
                    "About the Feature");
    }
 public void setDialogStage(Stage dialogStage) 
    {
		this.dialogStage = dialogStage;
    }
    
     public boolean isOkClicked()
    {
		return okClicked;
    }
}

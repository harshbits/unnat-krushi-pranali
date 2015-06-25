/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unnatkrushipranali.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author harshbitss
 */
public class AddSensorNodeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    
    private Stage dialogStage; 
    private boolean okClicked = false;
   
    
    
    
    @FXML
    public void handleCancel(ActionEvent event)
    {
        dialogStage.close();
        
    }

    public void setDialogStage(Stage dialogStage) 
    {
		this.dialogStage = dialogStage;
    }
    
     public boolean isOkClicked()
    {
		return okClicked;
    }
    
     
     
      
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}

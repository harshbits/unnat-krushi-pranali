/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unnatkrushipranali.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import unnatkrushipranali.MainWindowController;
import unnatkrushipranali.UnnatKrushiPranali;
import unnatkrushipranali.model.FieldTbl;

/**
 * FXML Controller class
 *
 * @author harshbitss
 */
public class NewFieldWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
     private Stage dialogStage;
    private Stage fileDialog;
    private boolean okClicked = false;
    private Desktop desktop=Desktop.getDesktop();
    
    MainWindowController mwc;
    UnnatKrushiPranali ukp;
  
    FieldTbl field;
     EntityManager em;
    EntityManagerFactory emf=Persistence.createEntityManagerFactory("UnnatKrushiPranaliPU");
    
   int id;
    @FXML
    private TextField fieldName;
    @FXML
    private Font x1;
    @FXML
    private TextField mappedArea;
    @FXML
    private TextField legalArea;
    @FXML
    private TextField tillableArea;
    @FXML
    private ComboBox<String> farmRegionCombo;
    @FXML
    private Label farmName;

    public NewFieldWindowController() {
        field=new FieldTbl();
    }
    
    
    
    @FXML
    public void handleAdd(ActionEvent event)
    {
        if(isInputValid()){
            if(field.getId()!=null){
                update(field.getId());  
                okClicked=true;
                dialogStage.close();                    
                System.out.println("Data Updated");
            }
        }
    }
    
    
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
    
          private void addPhotoEvent(ActionEvent event)
     {
         final FileChooser fileChooser;
         fileChooser=new FileChooser();
         configureFileChooser(fileChooser);
         File file=fileChooser.showOpenDialog(fileDialog);
         if(file!=null)
         {
             openFile(file);
         }
         
     }
     
      private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                NewFarmerWindowController.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
      
      private static void configureFileChooser(
        final FileChooser fileChooser)
      {      
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            
     }
 
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        farmRegionCombo.getItems().addAll("Northern","Southern","Eastern","Western","Center","North-Eastern","North-Western","South-Eastern","South-Western");
    }
    
    
    
    public void setField(FieldTbl field) {
            this.field=field;
             if(field.getId()!=null){               
                fieldName.setText(field.getFieldDescription());
                mappedArea.setText(Double.toString(field.getMappedArea()));
                legalArea.setText(Double.toString(field.getLegalArea()));
                tillableArea.setText(Double.toString(field.getTillableArea()));
                farmName.setText(field.getFarmId().getFarmDescription().toString());
                
                id=field.getId();              
             }         

    }
    
    
    /**
     * Update the fields of the Farm Field
     * @param id 
     */
    private void update(int id) {
        System.out.println(id);
        em=emf.createEntityManager();
        em.getTransaction().begin();
        FieldTbl farmr=(FieldTbl)em.find(FieldTbl.class, id);
        if(farmr!=null){
            farmr.setFieldDescription(fieldName.getText());
            farmr.setMappedArea(Double.parseDouble(mappedArea.getText()));
            farmr.setLegalArea(Double.parseDouble(legalArea.getText()));
            farmr.setTillableArea(Double.parseDouble(tillableArea.getText()));
            if(farmRegionCombo.getSelectionModel().getSelectedIndex()>0){
                farmr.setFarmRegion(farmRegionCombo.getSelectionModel().getSelectedItem().toString());
            }            
            em.persist(farmr);
            em.getTransaction().commit();
        }
        
    }
    
    
    /**
     * Validation of Input Field
     * 
     * @return 
     */
    public boolean isInputValid(){
        String errorMessage = "";
        
        if (fieldName.getText() == null || fieldName.getText().length() == 0) {
			errorMessage += "No valid Field Description!\n"; 
		}
        if (mappedArea.getText() == null || mappedArea.getText().length() == 0){
                        errorMessage += "No Valid Mapped Area !\n";
            }
        if (legalArea.getText() == null || legalArea.getText().length() == 0){
                        errorMessage += "No Valid legal Area !\n";
            }else{
            if(Double.parseDouble(legalArea.getText()) > Double.parseDouble( mappedArea.getText())){
                    errorMessage += "No Valid Legal Area, is should be less than Mapped Area!\n";
            }
        }
        
        if (tillableArea.getText() == null || tillableArea.getText().length() == 0){
                        errorMessage += "No Valid legal Area !\n";
            }else{
            if(Double.parseDouble(tillableArea.getText()) > Double.parseDouble( legalArea.getText())){
                    errorMessage += "No Valid Tillable Area, is should be less than Legal Area!\n";
            }
        }
        
    
        if (errorMessage.length() == 0) {
			return true;
        } else 
        {
			// Show the error message
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
	}
        
        
    }
}

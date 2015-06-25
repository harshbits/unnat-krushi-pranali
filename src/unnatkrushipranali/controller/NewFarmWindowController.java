/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.controller;

import java.awt.Desktop;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import unnatkrushipranali.UnnatKrushiPranali;
import unnatkrushipranali.model.FarmTbl;
import unnatkrushipranali.model.FarmerTbl;

/**
 * FXML Controller class
 *
 * @author harshbitss
 */
public class NewFarmWindowController implements Initializable {
    @FXML
    private Font x1;

    
    private Stage dialogStage; 
    private Stage fileDialog;
    private boolean okClicked = false;
    public FarmTbl farm;
    private final Desktop desktop=Desktop.getDesktop();
    UnnatKrushiPranali ukp;
    
    EntityManager em;
    EntityManagerFactory emf=Persistence.createEntityManagerFactory("UnnatKrushiPranaliPU");
    @FXML
    private TextField farmDescription;
    @FXML
    private TextField mappedArea;
    @FXML
    private TextField legalArea;
    @FXML
    private TextField tillableArea;        
    @FXML
    private TextField kmapLocation;
    
    int farmId;
    @FXML
    private Label farmerName;

    public NewFarmWindowController() {
        farm=new FarmTbl();               
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */           
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     //   farmerComboData();
    }    

    public FarmTbl handleAddData(int id){
        Query q=em.createQuery("SELECT f FROM FarmTbl f WHERE f.id= :farmID");
        q.setParameter(":farmId", id);
        FarmTbl fmt=(FarmTbl) q.getSingleResult();
        return fmt;
        
        
    }
    @FXML
    private void handleAdd(ActionEvent event) {

        if(farm.getId()!=null){
              try{
              
              if(isInputValid()){
                   
                    update(farmId);
                    okClicked=true;
                    dialogStage.close();
                    
                    System.out.println("Data Updated");
              }
          
          }catch(NoResultException ex){
          }}
          else{
            if(isInputValid()){
            insertData();           
            okClicked=true;
            dialogStage.close();
            System.out.println("Data Added");
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

    public void setFarm(FarmTbl farm) {
         this.farm=farm;
             if(farm.getId()!=null){               
                farmDescription.setText(farm.getFarmDescription());
                mappedArea.setText(farm.getMappedArea().toString());
                legalArea.setText(farm.getLegalArea().toString());
                tillableArea.setText(farm.getTillableArea().toString());
                farmerName.setText(farm.getFarmerId().getFirstName());                
                farmId=farm.getId();                 
             }                 
         em=emf.createEntityManager();   
         em.getTransaction().begin();
         FarmerTbl fmt=(FarmerTbl) em.createQuery("SELECT f FROM FarmerTbl f").getSingleResult();
         farmerName.setText(fmt.getFirstName());
         em.getTransaction().commit();           
    }
  

    private void update(Integer id) {
        FarmTbl farmr=(FarmTbl)em.find(FarmTbl.class, id);
        if(farmr!=null){
            em=emf.createEntityManager();
            em.getTransaction().begin();
            FarmerTbl fmt=(FarmerTbl) em.createQuery("SELECT f FROM FarmerTbl f").getSingleResult();
            farmr.setFarmDescription(farmDescription.getText());
            farmr.setMappedArea(Double.parseDouble(mappedArea.getText()));
            farmr.setLegalArea(Double.parseDouble(legalArea.getText()));
            farmr.setTillableArea(Double.parseDouble(tillableArea.getText()));
            farmr.setFarmerId(fmt);   
            em.persist(farmr);
            em.getTransaction().commit();
        }
        
    }

    private void insertData() {
        em=emf.createEntityManager();
        em.getTransaction().begin();
        FarmerTbl fmt=(FarmerTbl) em.createQuery("SELECT f FROM FarmerTbl f").getSingleResult();
        em.getTransaction().commit();
        farm.setId(Integer.SIZE); 
        farm.setFarmDescription(farmDescription.getText());
        farm.setMappedArea(Double.parseDouble(mappedArea.getText()));
        farm.setLegalArea(Double.parseDouble(legalArea.getText()));
        farm.setTillableArea(Double.parseDouble(tillableArea.getText()));
        farm.setFarmerId(fmt);       
    }
    
    /**
     * Input Validation
     * 
     * 
     * @return 
     */
    public boolean isInputValid(){
        String errorMessage = "";
        
        if (farmDescription.getText() == null || farmDescription.getText().length() == 0) {
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

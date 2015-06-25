/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import unnatkrushipranali.model.Cropdata;
import unnatkrushipranali.model.FieldTbl;

/**
 * FXML Controller class
 *
 * @author harshbitss
 */
public class AssignCropWindowController implements Initializable {
    
    private Stage dialogStage; 
    private boolean okClicked = false;
    @FXML
    private ComboBox<String> selectCropCombo;
    @FXML
    private Label selectedField;
    Cropdata crop;
    
    FieldTbl ft;
    EntityManagerFactory emf =Persistence.createEntityManagerFactory("UnnatKrushiPranaliPU");
    EntityManager em;

    /**
     * Initializes the controller class.
     *
     */
    public AssignCropWindowController(){
        
       
    }
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        em=emf.createEntityManager();
        em.getTransaction().begin();
        Query q=em.createQuery("SELECT c FROM Cropdata c");
        List ls=q.getResultList();        
        Iterator it=ls.iterator();
        while(it.hasNext()){
           Cropdata cd= (Cropdata) it.next();
           cd.getId();
           String crpName=cd.getId()+":"+cd.getCropName();        
           System.out.println(cd.getCropName());           
           selectCropCombo.getItems().setAll(crpName);
        }        
        em.getTransaction().commit();
        System.out.println("Successfully added all crop data");
        
    }    

    @FXML
    private void handleAdd(ActionEvent event) {
        
        em=emf.createEntityManager();
        em.getTransaction().begin();
        
        String selection=selectCropCombo.getSelectionModel().getSelectedItem().toString();
        String [] sId = selection.split(":");
        System.out.println("error above :"+sId[0]);        
        int id=Integer.parseInt(sId[0]);
        System.out.println("id is :"+id);
        Cropdata crd = em.find(Cropdata.class, id);
        if(crd !=null){
           crd.setFieldId(ft);
           System.out.println(ft);                
           okClicked=true;
           dialogStage.close();
           em.getTransaction().commit();
           System.out.println("Data updated");
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


    public void setCropData(FieldTbl ft) {
        this.ft=ft;
        if(ft.getId()!=null){           
            selectedField.setText(ft.getFieldDescription());            
        }
    }    
}

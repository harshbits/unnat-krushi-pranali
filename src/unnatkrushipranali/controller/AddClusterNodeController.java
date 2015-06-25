/*
 * To change this template, choose Tools | Templates
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import unnatkrushipranali.model.ClusterNode;

/**
 * FXML Controller class
 *
 * @author harshbitss
 */
public class AddClusterNodeController implements Initializable {

    /**
     * Initializes the controller class.
     * 
     * @aurhor Harsh Bhavsar
     */
    
    private Stage dialogStage; 
    private boolean okClicked = false;
    private ClusterNode clusterNode;
    @FXML
    private TextField clusterId;
    EntityManagerFactory emf =Persistence.createEntityManagerFactory("UnnatKrushiPranaliPU");
    EntityManager em;

    public AddClusterNodeController() throws SQLException, ClassNotFoundException, IOException, Exception {
        
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
    
     public void setClusterNode(ClusterNode clusterNode){
         this.clusterNode=clusterNode;         
         clusterId.setText(clusterNode.getClusterId());
                
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAdd(ActionEvent event) throws IOException {
        em=emf.createEntityManager();
        em.getTransaction().begin();
        clusterNode.setId(Integer.SIZE);
        clusterNode.setClusterId(clusterId.getText());
        okClicked=true;
        dialogStage.close();
        em.getTransaction().commit();
    }
}

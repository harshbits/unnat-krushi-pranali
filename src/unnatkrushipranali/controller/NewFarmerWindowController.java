/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import unnatkrushipranali.UnnatKrushiPranali;
import unnatkrushipranali.model.FarmerTbl;
import unnatkrushipranali.util.CalendarUtil;

/**
 * FXML Controller class
 *
 * @author harshbitss
 */
public class NewFarmerWindowController implements Initializable {
    @FXML
    private Font x1;        
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField phoneNoField;
    @FXML
    private TextField street1Field;
    @FXML
    private TextField street2Field;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField postalField;
    @FXML
    private TextField countryCode;
    @FXML
    private Button addPhotoButton;
    @FXML
    private Pane photoView;

    /**
     * Initializes the controller class.
     */
    private Stage dialogStage; 
    private Stage fileDialog;
    private boolean okClicked = false;
    private FarmerTbl farmer;
    private final Desktop desktop=Desktop.getDesktop();
    UnnatKrushiPranali ukp;
    
    EntityManager em;
    EntityManagerFactory emf=Persistence.createEntityManagerFactory("UnnatKrushiPranaliPU");

    public NewFarmerWindowController() {
        farmer=new FarmerTbl();               
    }
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    @FXML
    private void addPhotoEvent(ActionEvent event) throws IOException {
        final FileChooser fileChooser;
         fileChooser=new FileChooser();
         configureFileChooser(fileChooser);
         File file=fileChooser.showOpenDialog(fileDialog);
         if(file!=null)
         {
             openFile(file);
         }
        
    }
    
     private void openFile(File file) throws IOException {
         desktop.open(file);
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

    
     
      public void update(int id) throws ParseException{
          FarmerTbl frmr=(FarmerTbl)em.find(FarmerTbl.class, id);
          
          if(frmr!=null){
           // frmr.setId(Integer.SIZE);        
            System.out.println(frmr.getFirstName());
            frmr.setFirstName(firstNameField.getText());
            frmr.setMiddleName(middleNameField.getText());
            frmr.setLastName(lastNameField.getText()); 
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date bday=format.parse(birthdayField.getText());
            frmr.setBirthDay(bday);
            frmr.setPhone(phoneNoField.getText());
            frmr.setStreet1(street1Field.getText());
            frmr.setStreet2(street2Field.getText());        
            frmr.setCity(cityField.getText());
            frmr.setState(stateField.getText());
            frmr.setPin(Integer.parseInt(postalField.getText()));
            frmr.setCountry(countryCode.getText());
            
            em.persist(frmr);
            em.getTransaction().commit();
          }         
      }
      
    public void insertData() throws ParseException{
            farmer.setId(Integer.SIZE);        
            farmer.setFirstName(firstNameField.getText());
            farmer.setMiddleName(middleNameField.getText());
            farmer.setLastName(lastNameField.getText()); 
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date bday=format.parse(birthdayField.getText());
            farmer.setBirthDay(bday);
            farmer.setPhone(phoneNoField.getText());
            farmer.setStreet1(street1Field.getText());
            farmer.setStreet2(street2Field.getText());        
            farmer.setCity(cityField.getText());
            farmer.setState(stateField.getText());
            farmer.setPin(Integer.parseInt(postalField.getText()));
            farmer.setCountry(countryCode.getText());
          
      }
    @FXML
    private void handleAdd(ActionEvent event) throws ParseException {
        
          em=emf.createEntityManager();
          em.getTransaction().begin();
          
          CriteriaBuilder qb = em.getCriteriaBuilder();
          CriteriaQuery<Long> cq = qb.createQuery(Long.class);
          cq.select(qb.count(cq.from(FarmerTbl.class)));
          long count=em.createQuery(cq).getSingleResult();
          try{
          FarmerTbl fmt=(FarmerTbl) em.createQuery("SELECT f FROM FarmerTbl f").getSingleResult();
          
          if(count==1){
              if(isInputValid()){
                   
                    update(fmt.getId());
                    okClicked=true;
                    dialogStage.close();
                    
                    System.out.println("Data Updated");
              }
          
          }}catch(NoResultException ex){

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
     
     /**
	 * Sets the person to be edited in the dialog.
	 * 
     * @param farmer
	 */
	public void setFarmer(FarmerTbl farmer) {
            this.farmer=farmer;
            em=emf.createEntityManager();
            CriteriaBuilder qb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = qb.createQuery(Long.class);
            cq.select(qb.count(cq.from(FarmerTbl.class)));        
            long count=em.createQuery(cq).getSingleResult();
            if(count==1){                           
                firstNameField.setText(farmer.getFirstName());
                middleNameField.setText(farmer.getMiddleName());
                lastNameField.setText(farmer.getLastName());
                String bday = CalendarUtil.format(farmer.getBirthDay());
                birthdayField.setText(bday);
                birthdayField.setPromptText("DD-MM-YYYY (for eg. '21-10-1992')");
                phoneNoField.setText(farmer.getPhone());
                street1Field.setText(farmer.getStreet1());
                street2Field.setText(farmer.getStreet2());
                cityField.setText(farmer.getCity());           
                stateField.setText(farmer.getState());
                postalField.setText(farmer.getPin().toString());
                countryCode.setText(farmer.getCountry());
            }
             birthdayField.setPromptText("DD-MM-YYYY (for eg. '21-10-1992')");
        }
        
        
        private boolean isInputValid() {
		String errorMessage = "";

		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "No valid first name!\n"; 
		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "No valid last name!\n"; 
		}
		if (street1Field.getText() == null || street1Field.getText().length() == 0) {
			errorMessage += "No valid street!\n"; 
		}
                if (phoneNoField.getText() == null || phoneNoField.getText().length() == 0) {
			errorMessage += "No valid mobile no!\n"; 
		}else{
                    try {
				//Integer.parseInt(phoneNoField.getText());
                                if(phoneNoField.getText().length()!=10){
                                    errorMessage += "It should be of length '10'!\n";                                     
                                }                               
			} catch (NumberFormatException e) {
				errorMessage += "No valid mobile no!\n";  
			}                    
                }
		
		if (postalField.getText() == null || postalField.getText().length() == 0) {
			errorMessage += "No valid postal code!\n"; 
		} else {
			// try to parse the postal code into an int
			try {
				//Integer.parseInt(postalField.getText());
                                if(postalField.getText().length()!=6){
                                    errorMessage += "It should be of length '6'!\n";                                     
                                } 
			} catch (NumberFormatException e) {
				errorMessage += "No valid postal code (must be an integer)!\n"; 
			}
		}
		
		if (cityField.getText() == null || cityField.getText().length() == 0) {
			errorMessage += "No valid city!\n"; 
		}
		
		if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
			errorMessage += "No valid birthday!\n";
		} 
                else {
			if (!CalendarUtil.validString(birthdayField.getText())) {
				errorMessage += "No valid birthday. Use the format DD-MM-YYYY !\n";
			}
		}
		
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
		}
	}
     
}

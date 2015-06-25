/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unnatkrushipranali;


import unnatkrushipranali.controller.NewFarmerWindowController;
import unnatkrushipranali.controller.NewFieldWindowController;
import unnatkrushipranali.controller.AddClusterNodeController;
import unnatkrushipranali.controller.AddSensorNodeController;
import unnatkrushipranali.controller.LanguageSelectionController;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import unnatkrushipranali.controller.AssignCropWindowController;
import unnatkrushipranali.controller.NewFarmWindowController;
import unnatkrushipranali.model.ClusterNode;
import unnatkrushipranali.model.Cropdata;
import unnatkrushipranali.model.FarmTbl;
import unnatkrushipranali.model.FarmerTbl;
import unnatkrushipranali.model.FieldTbl;
import unnatkrushipranali.util.FileUtil;

/**
 *
 * @author harshbitss
 */

public class UnnatKrushiPranali extends Application 
{    
    public Stage stage;
    private AnchorPane root; 
    private AnchorPane anchorPane;
    FXMLLoader loader;
    public ObservableList<ClusterNode> clusterData =FXCollections.observableArrayList();
    public ObservableList<FarmerTbl> farmerData=FXCollections.observableArrayList();
    public ObservableList<FarmTbl> farmData=FXCollections.observableArrayList();
    EntityManager em;
    EntityManagerFactory emf=Persistence.createEntityManagerFactory("UnnatKrushiPranaliPU");
    //=new MainWindowController();
    MenuItem hm;
    MenuItem enm;
    MenuBar mb=new MenuBar();
    
    
   
    private ObservableList<Cropdata> cropData = FXCollections.observableArrayList();
    
     private ResourceBundle enBundle = ResourceBundle.getBundle("bundles.bundle", Locale.forLanguageTag("en"));
     private ResourceBundle hiBundle = ResourceBundle.getBundle("bundles.bundle", Locale.forLanguageTag("hi"));   
    
    public UnnatKrushiPranali() throws Exception {       
        em=emf.createEntityManager();
        em.getTransaction().begin();
        Query query=em.createQuery("SELECT c FROM ClusterNode c");        
        List lst=query.getResultList();
        Iterator it=lst.iterator();
        while(it.hasNext())
        {            
            ClusterNode tree=(ClusterNode) it.next();        
            clusterData.add(new ClusterNode(tree.getId(), tree.getClusterId()));
        }
        
        em.getTransaction().commit();        
        em.close();  
  }
    
    @Override
    public void init() throws Exception {
        super.init();
    }
       
    @Override
    public void start(Stage stage) 
    {
        try{            
            //anchorPane=FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"), enBundle);
            loader=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/MainWindow.fxml"),enBundle);
            root=(AnchorPane) loader.load();
            //Scene scene = new Scene(root);            
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        final MainWindowController main=loader.getController();
        mb=main.menuBar;
        Menu menu = new Menu("Language");
        hm=new MenuItem("Hindi");
        hm.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                try {
                    loader=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/MainWindow.fxml"),hiBundle);
                    root=(AnchorPane) loader.load();
                    //anchorPane=FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"), hiBundle);
                } catch (IOException ex) {
                   System.out.println(ex.getMessage());
                }
                
            }
        });
        
        enm=new MenuItem("English");
        enm.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                try {
                    loader=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/MainWindow.fxml"),enBundle);
                    root=(AnchorPane) loader.load();
                    //anchorPane=FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"), enBundle);
                    
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        
         menu.getItems().add(hm);
         menu.getItems().add(enm);
         mb.getMenus().add(menu);
              

        //Make scene occupy full screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        //Scene scene = new Scene(anchorPane, screenBounds.getWidth(), screenBounds.getHeight());
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());        
        //  Scene scene=new Scene(anchorPane);
        this.stage=stage;
        this.stage.setTitle("Unnat Krushi Pranali Main Window");
        this.stage.getIcons().add(new Image("file:resources/images/ukpLogo.png"));
        this.stage.setScene(scene);
        this.stage.show();
        
        File file = getCropFilePath();
		if (file != null) {
			loadCropDataFromFile(file);
		}
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    @Override public void handle(WindowEvent t) {
        System.out.println("CLOSING");
        main.exit();
    }
});
              
    }
         
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //new UnnatKrushiPranali();
        launch(args);
    }
    
     @Override
    public void stop() throws Exception {
        super.stop();
    }
    
    // initialization of the new farmer add window
    public boolean showFarmerWindowOpen(FarmerTbl farmer) throws IOException
    {        
        try
        {
            FXMLLoader loaderFarmer=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/newview/newFarmerWindow.fxml"));
            AnchorPane newButtonPage=(AnchorPane) loaderFarmer.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Farmer Profile");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            NewFarmerWindowController controller=loaderFarmer.getController();
            controller.setDialogStage(dialogStage);
            controller.setFarmer(farmer);
            System.out.println(farmer);
            
            dialogStage.showAndWait();
            return controller.isOkClicked();
              
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
                
    }
    
    /**
     *Initialization of the new Field add window   
     * 
     * @param farm
     * @return 
     */
    public boolean showFarmWindowOpen(FarmTbl farm)
    {
        try
        {
            FXMLLoader loaderField=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/newview/newFarmWindow.fxml"));
            AnchorPane newButtonPage=(AnchorPane) loaderField.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Farm Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            NewFarmWindowController controller=loaderField.getController();
            controller.setFarm(farm);
            System.out.println(farm);            
            controller.setDialogStage(dialogStage);
            
            
            dialogStage.showAndWait();
            //dialogStage.show();
            return controller.isOkClicked();
              
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean showFieldWindowOpen(FieldTbl field)
    {
        try
        {
            FXMLLoader loaderField=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/newview/newFieldWindow.fxml"));
            AnchorPane newButtonPage=(AnchorPane) loaderField.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Field Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            NewFieldWindowController controller=loaderField.getController();
            controller.setField(field);
            controller.setDialogStage(dialogStage);
            //dialogStage.showAndWait();
            dialogStage.show();
            return controller.isOkClicked();
              
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
        
    public boolean showAddSensorOpen()
    {        
        try
        {
            FXMLLoader loader=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/newview/addSensorNode.fxml"));
            AnchorPane newButtonPage=(AnchorPane) loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Add Sensor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            AddSensorNodeController controller=loader.getController();
            controller.setDialogStage(dialogStage);
            
            dialogStage.showAndWait();
            return controller.isOkClicked();
              
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
        
    public boolean showAddClusterOpen(ClusterNode clusterNode)
    {
        try
        {
            FXMLLoader loader=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/newview/addClusterNode.fxml"));
            AnchorPane newButtonPage=(AnchorPane) loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Add Cluster Node");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            AddClusterNodeController controller=loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setClusterNode(clusterNode);
            System.out.println(clusterNode);
         //   System.out.println(clusterNode.getClusterId());
            
            
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
              
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        
    }

    
    public ObservableList<ClusterNode> getClusterData() {
        return clusterData;
    }
    
    public ObservableList<FarmerTbl> getFarmerData() {
        return farmerData;
    }
    
    public ObservableList<FarmTbl> getFarmData() {
        return farmData;
    }
    
    public Stage getPrimaryStage() {
		return stage;
	}
    
   
    
    /**
	 * Loads crop data from the specified file. The current crop data will
	 * be replaced.
	 * 
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	public void loadCropDataFromFile(File file) {
		XStream xstream = new XStream();
		xstream.alias("cropData", Cropdata.class);
		
		try {
			String xml = FileUtil.readFile(file);
                        
			ArrayList<Cropdata> cropDataList = (ArrayList<Cropdata>) xstream.fromXML(xml);
			
			cropData.clear();
			cropData.addAll(cropDataList);
                        
                        System.out.println(cropData);
			
			setCropdataFilePath(file);
		} catch (IOException e) { // catches ANY exception
			Dialogs.showErrorDialog(stage,
					"Could not load data from file:\n" + file.getPath(),
					"Could not load data", "Error", e);
		}
	}
        
        public File getCropdataFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(UnnatKrushiPranali.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
        
        public void setCropdataFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(UnnatKrushiPranali.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			
			// Update the stage title
			stage.setTitle("UnnatKrushiPranali - " + file.getName());
		} else {
			prefs.remove("filePath");
			
			// Update the stage title
			stage.setTitle("UnnatKrushiPranali");
		}
	}

    File getProjectFilePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void saveProjectDataToFile(File projectFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean showLanguageSelection() {
        
         try
        {
            FXMLLoader loader=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/newview/languageSelection.fxml"));
            AnchorPane newButtonPage=(AnchorPane) loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Select Local Language");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            LanguageSelectionController controller=loader.getController();
            controller.setDialogStage(dialogStage);
            
            dialogStage.showAndWait();
            return controller.isOkClicked();
              
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void handleEnglishLanguageSelection() throws IOException{
     //   start(stage);
        anchorPane=FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"), hiBundle);
        //stage.setScene(anchorPane);
       //loader=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/MainWindow.fxml"),hiBundle);
       //root=(AnchorPane) loader.load();
//       Scene scene = new Scene(root);
//       stage.setScene(scene);
//       stage.show();
    }
    public void handleHindiLanguageSelection() throws IOException{
        
        stage.setTitle("Unnat Krushi Pranali Main Window");
        stage.getIcons().add(new Image("file:resources/images/ukpLogo.png"));  

        try{            
            anchorPane=FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"), hiBundle);
            Scene scene=new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }       
        
    }

    public void saveCropDataToFile(File file) {
        
        XStream xstream = new XStream();
		xstream.alias("crop", Cropdata.class);

		// Convert ObservableList to a normal ArrayList
		ArrayList<Cropdata> cropList = new ArrayList<>(cropData);
		
		String xml = xstream.toXML(cropList);
		try {
			FileUtil.saveFile(xml, file);
			
			setCropFilePath(file);
		} catch (IOException e) { // catches ANY exception
			Dialogs.showErrorDialog(stage,
					"Could not save data to file:\n" + file.getPath(),
					"Could not save data", "Error", e);
		}
    }

    private void setCropFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(UnnatKrushiPranali.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			
			// Update the stage title
			// stage.setTitle("Crop Name - " + file.getName());
		} else {
			prefs.remove("filePath");
			
			// Update the stage title
			// stage.setTitle("CropName");
		}
            
    }

    File getCropFilePath() {
        
        Preferences prefs = Preferences.userNodeForPackage(UnnatKrushiPranali.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
        
    }

    boolean showAboutCropWindowOpen(Cropdata crop) {
        try
        {
            FXMLLoader loaderFarmer=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/newview/assignCropWindow.fxml"));
            AnchorPane newButtonPage=(AnchorPane) loaderFarmer.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Farmer Profile");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            AssignCropWindowController controller=loaderFarmer.getController();
            controller.setDialogStage(dialogStage);            
            controller.setCropData(null);
            
            dialogStage.showAndWait();
            return controller.isOkClicked();
              
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    boolean showAssignCropWindowOpen(FieldTbl ft) {
        try
        {
            FXMLLoader loaderFarmer=new FXMLLoader(UnnatKrushiPranali.class.getResource("view/newview/assignCropWindow.fxml"));
            AnchorPane newButtonPage=(AnchorPane) loaderFarmer.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Farmer Profile");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            AssignCropWindowController controller=loaderFarmer.getController();
            controller.setDialogStage(dialogStage);
            controller.setCropData(ft);
            //System.out.println(farmer);
            
            dialogStage.showAndWait();
            return controller.isOkClicked();
              
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        
    }

   
    
    
}

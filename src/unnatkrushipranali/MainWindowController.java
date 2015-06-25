/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unnatkrushipranali;

import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
import com.sun.javafx.scene.control.skin.SkinBase;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import unnatkrushipranali.model.ClusterNode;
import unnatkrushipranali.model.ClusterNodeReading;
import unnatkrushipranali.model.ClusterNodeService;
import unnatkrushipranali.model.Cropdata;
import unnatkrushipranali.model.CropdataService;
import unnatkrushipranali.model.EntityProxy;
import unnatkrushipranali.model.EntityProxyType;
import unnatkrushipranali.model.FarmService;
import unnatkrushipranali.model.FarmTbl;
import unnatkrushipranali.model.FarmerService;
import unnatkrushipranali.model.FarmerTbl;
import unnatkrushipranali.model.FieldService;
import unnatkrushipranali.model.FieldTbl;
import unnatkrushipranali.model.SensorData;
import unnatkrushipranali.model.SensorList;
import unnatkrushipranali.model.SensorListService;
import unnatkrushipranali.model.SensorNode;
import unnatkrushipranali.model.SensorNodeData;
import unnatkrushipranali.model.SensorNodeService;
import unnatkrushipranali.model.SolenoidValve;
import unnatkrushipranali.model.SolenoidValveService;
import unnatkrushipranali.model.WeatherStatus;

/**
 *
 * @author harshbitss
 */
public final class MainWindowController implements Initializable {
    /**
     * for containing the ports that will be found
     * serial data declaration
     */
    private Enumeration ports = null;
    private CommPortIdentifier portId = null;
    private SerialPort serialPort = null;   
    private BufferedReader input;
    private OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;   
    private HashMap portMap = new HashMap();
    
    
          
    
    private UnnatKrushiPranali ukp;
    private boolean isConnected=false;
    @FXML
    private Label portStatus;        
    @FXML
    private Label temp,moisture;

    @FXML
    private ComboBox<String> portCombo;


       
    private ResourceBundle bundle;
    
    /**
     * bar chart declaration
     * 
     */ 
    @FXML
    private BarChart<String, Double> barChart;       
    @FXML
    private CategoryAxis clusterAxis;
    @FXML
    private NumberAxis dataAxis;    
    XYChart.Series<String, Double> series= new XYChart.Series<>();   
    private ObservableList<ClusterNode> barchartData=FXCollections.observableArrayList();  
    
    /**
     *  Machine TreeView Declaration
     * 
     */
    private ObservableList<ClusterNode>clusterNames = FXCollections.observableArrayList();  
    private ObservableList<String>sensorNodeList=FXCollections.observableArrayList();        
    private ObservableList<String> solenoidValveList;
    
    ClusterNode clusterNode;
    SensorNode sensorNode;
    SolenoidValve solenoidValve;
    
    private ClusterNodeService clusterNodeService;
    private SensorNodeService sensorNodeService;
    private SensorListService sensorListService;
    private SolenoidValveService solenoidValveService;
    
    private Collection<SensorNode> snList;
    private Collection<SensorList> snlList;
    private Collection<SolenoidValve> svList;
    
    private final ContextMenu nodeMenu=new ContextMenu();
    private final ContextMenu sensorMenu=new ContextMenu();
    private final ContextMenu sensorlMenu=new ContextMenu();
    private final ContextMenu valveMenu= new ContextMenu();
    private final ContextMenu wsnMenu=new ContextMenu();
    
    private final Image clusterIcon=new Image(getClass().getResourceAsStream("cluster.png"));
    private final Image sensorNodeIcon=new Image(getClass().getResourceAsStream("sensorNode.png"));
    private final Image valveIcon=new Image(getClass().getResourceAsStream("valve.png"));
    private final Node wsnIcon=new ImageView(new Image(getClass().getResourceAsStream("wsn.png")));
    private final Image sensorIcon=new Image(getClass().getResourceAsStream("sensor.png"));
    
    private final Image deleteIcon=new Image(getClass().getResourceAsStream("delete.png"));
    private final Image on=new Image(getClass().getResourceAsStream("on.png"));
    private final Image off=new Image(getClass().getResourceAsStream("off.png"));
    private final Image editIcon=new Image(getClass().getResourceAsStream("edit.png"));
    private final Image refreshIcon=new Image(getClass().getResourceAsStream("refresh.png"));
    private final Image aboutIcon=new Image(getClass().getResourceAsStream("about-32.png"));
    
   /**
    * Farm TreeView Declaration
    * 
    */
    private ObservableList<FarmTbl>farmNames=FXCollections.observableArrayList();
    private ObservableList<FieldTbl>fieldName=FXCollections.observableArrayList();
    
    FarmerTbl farmer;
    FarmTbl farm;
    FieldTbl field;
    private FarmerService farmerService;
    private FarmService farmService;
    private FieldService fieldService;
    private CropdataService cropdataService;
    
    private Collection<FarmTbl> farmList;
    private Collection<FieldTbl> fieldList;
    
    private final ContextMenu farmerMenu=new ContextMenu();
    private final ContextMenu farmMenu=new ContextMenu();
    private final ContextMenu fieldMenu=new ContextMenu();
    private final ContextMenu cropMenu=new ContextMenu();
    
    private final Node farmerIcon=new ImageView(new Image(getClass().getResourceAsStream("farmer.png")));
    private final Image farmIcon=new Image(getClass().getResourceAsStream("farm.PNG"));
    private final Image fieldIcon=new Image(getClass().getResourceAsStream("field.PNG"));
    private final Image cropIcon=new Image(getClass().getResourceAsStream("wheat-32.png"));
    private final Image machineIcon=new Image(getClass().getResourceAsStream("cnc-machine-32.png"));
    
    private final Image sunImage=new Image(getClass().getResourceAsStream("forecast/Status-weather-clear-icon.png"));
    private final Image cloudImage=new Image(getClass().getResourceAsStream("forecast/Status-weather-clouds-night-icon.png"));
    
    
    EntityManagerFactory emf ;
    EntityManager em;
    
       
    /**
     * 
     * Weather Forecasting Icon
     * 
     */
    
//        Image cloudImage = new Image(getClass().getResourceAsStream("cloud.jpg"));
  //      Image sunImage = new Image(getClass().getResourceAsStream("sun.jpg"));   
    @FXML
    private Font x1;
    @FXML
    private Color x6;
    @FXML
    private Font x5;
    @FXML
    private Font x2;
    @FXML
    private Font x3;
    @FXML
    private Color x4;
    @FXML
    private Label humidity;
    @FXML
    private Tab temperature;
    @FXML
    private TreeView<EntityProxy> farmTreeView;
    private TreeItem<EntityProxy> farmRootItem;
    EntityProxy epf;
    
    @FXML
    private TreeView<EntityProxy> treeView;    
    private TreeItem<EntityProxy> rootItem;
    EntityProxy ep;
    
    
    @FXML
    private TreeView<?> accountTreeView;
    
    
    @FXML
    private Font x7;
    @FXML
    private Color x9;
    @FXML
    private Color x8;
    
    @FXML
    private ListView<String> valveList;
    
    
    @FXML
    private Label refreshStatus;
    @FXML
    private Label cropName;
    @FXML
    private ComboBox<String> periodCombo;
    @FXML
    public MenuItem hindiMenu;
    @FXML
    private AnchorPane weatherStatus;
    
            
    
    
    
    // Timers for auto refresh of the main Windows
     Timer timerCombo;
     Timer timerBarChart;
     Timer timerTemperature;
     Timer timerSerial;
     Timer autoData;
     
    @FXML
    public MenuItem englishMenu;
    @FXML
    private Label cropSeason;
    @FXML
    private Label cropDuration;
    @FXML
    private Label cropAtmosphere;
    @FXML
    private Label cropMoisture;
    @FXML
    private Label cropRegion;
    @FXML
    private Label suggestedFertilizer;
    @FXML
    private Label cropSoilType;
    @FXML
    private ToggleButton automaticTogleButton;
    
    /**
     * Date and time and calendar entity
     * 
     * 
     * @author Harsh Bhavsar          
     */
    
    
//    Calendar cal=Calendar.getInstance();
//    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
//    String date;
//    java.sql.Date readingDt;
//    Date readingDate;
//    int hour;
    
    /**
     * 
     * Serial Reading Database declaration
     * 
     */
   ObservableList<ClusterNode> clusterNodeListing=FXCollections.observableArrayList();
   ObservableList<SensorNode> sensorNodeListing=FXCollections.observableArrayList();
   ObservableList<SensorList> sensorListing=FXCollections.observableArrayList();
   ObservableList<Cropdata> cropData=FXCollections.observableArrayList();
   
   
   
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @FXML
    public MenuBar menuBar;
    @FXML
    private Color x10;
    private boolean cropFileStatus=false;
    @FXML
    private Color x11;
    @FXML
    private ComboBox<String> cropFromDatabase;
    @FXML
    private Button saveCropButton;
    @FXML
    private ImageView today1Image;
    @FXML
    private Label today1Date;
    @FXML
    private Font x12;
    @FXML
    private Label today1Temp;
    @FXML
    private ImageView today2Image;
    @FXML
    private Label today2Date;
    @FXML
    private Label today2Temp;
    @FXML
    private Color x13;
    @FXML
    private ImageView today3Image;
    @FXML
    private Label today3Date;
    @FXML
    private Label today3Temp;
    @FXML
    private Tab intelligentTab;
    @FXML
    private TabPane allTab;
    @FXML
    private Label pressure;
    @FXML
    private Label windSpeed;
    @FXML
    private Label windDirection;
    
    
    public MainWindowController() throws SQLException, ClassNotFoundException, IOException, Exception {
        emf =Persistence.createEntityManagerFactory("UnnatKrushiPranaliPU");
        clusterNodeService=new ClusterNodeService("UnnatKrushiPranaliPU");
        sensorNodeService=new SensorNodeService("UnnatKrushiPranaliPU");
        sensorListService=new SensorListService("UnnatKrushiPranaliPU");
        solenoidValveService=new SolenoidValveService("UnnatKrushiPranaliPU");
        farmerService=new FarmerService("UnnatKrushiPranaliPU");
        farmService=new FarmService("UnnatKrushiPranaliPU");
        fieldService=new FieldService("UnnatKrushiPranaliPU");
        cropdataService=new CropdataService("UnnatKrushiPranaliPU");
        clusterNode=new ClusterNode();
        sensorNode=new SensorNode();
       
        ukp = new UnnatKrushiPranali();
        createWSNContextMenu();
        createClusterNodeContextMenu();
        createSensorListContextMenu();
        createSensorListMenu();
        createSolenoidValveContextMenu();        
        createFarmerContextMenu(); 
        createFarmContextMenu();
        createFieldContextMenu();
        createCropContextMenu();
    }

    
    public void setUnnatKrushiPranali(UnnatKrushiPranali ukp) throws IOException{
        this.ukp=ukp;
        refreshMachineTreeView();
    }
    
    

    @FXML
    private void newFieldWindowOpen(ActionEvent event) throws IOException {
        //ukp.showFieldWindowOpen();
    }


 /**
 * Sensor Node Data Add
 * 
 * @author Harsh Bhavsar
     * @throws java.io.IOException
 */
    
    public void sensorNodeAdd() throws IOException{
        
        SensorNode clrTemp =new SensorNode();
        boolean okClicked= ukp.showAddSensorOpen();
        if(okClicked){
       // ukp.getClusterData().add(clrTemp);
        
        em=emf.createEntityManager();
        em.getTransaction().begin();
        ClusterNode cn=new ClusterNode();
        cn.setClusterId(clrTemp.getClusterId().toString());
        em.persist(cn);
        em.getTransaction().commit();
        refreshMachineTreeView();
        }
    }
     @FXML
    private void addSensorNode(MouseEvent event) throws IOException {
        
        sensorNodeAdd();
    }

    @FXML
    private void newSensorWindowOpen(ActionEvent event) throws IOException {
        sensorNodeAdd();
        
    }
    
    
    @FXML
    private void addSolenoidValve(MouseEvent event) {
    }

   
    
    
    public void insertDataInitialize(){
        em=emf.createEntityManager();
        em.getTransaction().begin();
        
        Query query=em.createQuery("SELECT c FROM ClusterNode c");        
        List lst=query.getResultList();
        clusterNodeListing.addAll(lst);
        
        Query query1=em.createQuery("SELECT s FROM SensorNode s");
        List lst1=query1.getResultList();
        sensorNodeListing.addAll(lst1);
        
        Query query2=em.createQuery("SELECT s FROM SensorList s");
        List lst2=query2.getResultList();
        sensorListing.addAll(lst2);
        
        em.getTransaction().commit();
    }

    @FXML
    private void addFarmerDetails(MouseEvent event) throws IOException {
        farmerWindowOpen();
    }

    @FXML
    private void addFarmDetails(MouseEvent event) throws IOException {
        farmDataAdd();
    }

    @FXML
    private void addFieldDetails(MouseEvent event) {
    }

    
  
    

    
    

  
    
    
 /**
 * Serial Input and output/......!!!!!!!!!!!!!    
 * 
 * @author Harsh Bhavsar  
 */        
  public class SerialReader implements SerialPortEventListener {                  
        private BufferedReader in;    
        public SerialReader(BufferedReader in) {            
            this.in = in;
        }
        @Override
        public void serialEvent(SerialPortEvent oEvent) {
        

            if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                try {                                         
                    Calendar cal=Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
                    String date=dateFormat.format(cal.getTime());        
                    Date readingDate=dateFormat.parse(date);                    
                    int hour=cal.get(Calendar.HOUR_OF_DAY);
                    final String inputLine = in.readLine().toString();              
                    System.out.println(inputLine);                                                          
                    System.out.println("in side serial");
                    final String[] r = inputLine.split(":");
                    switch (r[0]) {
                        
                        case "WTHR":
                            Double temp=Double.parseDouble(r[1]);
                            Double hum=Double.parseDouble(r[2]);
                            Double wnds=Double.parseDouble(r[3]);
                            Double wndd=Double.parseDouble(r[4]);
                            Double psr=Double.parseDouble(r[5]);
                            em=emf.createEntityManager();
                            em.getTransaction().begin();
                            WeatherStatus ws=new WeatherStatus();
                            ws.setTemperature(temp);
                            ws.setHumidity(hum);
                            ws.setWindSpeed(wnds);
                            ws.setWindDirection("NW");
                            ws.setPressure(psr);
                            ws.setSunLight(6.0);
                            ws.setReadingTime(cal.getTime());
                            ws.setReadingDate(readingDate);
                            em.persist(ws);
                            em.getTransaction().commit();
                            System.out.println("Weather Data entered");                                                    
                            break;
                            
                        case "SM":                            
                            Double Reading=Double.parseDouble(r[4]);  
                            if(Reading >10){
                                Reading =0.0;
                            }
                            else{
                                Reading=(Reading*100)/4.5;
                            }
                            if(Reading <100){                                                            
                            for(ClusterNode cluster: clusterNodeListing){
                                if(cluster.getClusterId().equals(r[1])){
                                    for(SensorNode sensorNode: cluster.getSensorNodeCollection()){
                                        if(sensorNode.getSensorId().equals(r[2])){
                                            for(SensorList sensor: sensorNode.getSensorListCollection()){
                                                if(sensor.getName().equals(r[3])){
                                                    em=emf.createEntityManager();
                                                    em.getTransaction().begin();
                                                    SensorData sd=new SensorData();                                                              
                                                    sd.setSensorsId(sensor);
                                                    sd.setReading(Reading);
                                                    sd.setReadingHour(hour);
                                                    sd.setReadingTime(cal.getTime());
                                                    sd.setReadingDate(readingDate);                                                               
                                                    em.persist(sd);
                                                    em.getTransaction().commit();
                                                    System.out.println("Soil Moisture Data entered :: "+Reading);
                                                    break;
                                                }
                                            }
                                        }                                                    
                                    }
                                }
                            }   
                           }
                            break;
                    }                                                                  
                } catch (        IOException | ParseException e) {
                    System.err.println(e.toString());
                } 
            }
        }
    }      
     
    /**
     *   Connection Of Serial Port for Input
     * 
     * 
     * @param event
     * @throws gnu.io.PortInUseException
     * @throws gnu.io.UnsupportedCommOperationException
     * @throws java.io.IOException
     * @throws java.util.TooManyListenersException
     * @throws gnu.io.NoSuchPortException
     */ 
    @FXML
    public void connectPort(ActionEvent event) throws PortInUseException, UnsupportedCommOperationException, IOException, TooManyListenersException, NoSuchPortException
    {        
        int selectedIndex=portCombo.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0)
        {
            isConnected=true;
            String newPort = portCombo.getSelectionModel().selectedItemProperty().getValue().toString();   
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(newPort);
            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println("Error: Port is currently in use");
            } else 
            {
                CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
                portStatus.setText(newPort + "Connected");
                if (commPort instanceof SerialPort) {
                    serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));                                   
                    output=serialPort.getOutputStream();                    
                    serialPort.addEventListener(new SerialReader(input));
                    serialPort.notifyOnDataAvailable(true);
                    connectButton.setDisable(true);
                } else {
                    System.out.println("Not a serial port...");
                    Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please select a proper COM port from the list",
					"Incorrect COM port selected", "Port Error");
                }
                System.out.println("start");
            }
        }
        else
        {
            Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please select a port from the list",
					"No Port Connection Selected", "No Selection");    
        }
    }

     /**
      *  Disconnect code of the Serial Port
      *     
      * @param event
      * @throws java.io.IOException
      */
    @FXML
    public void disconnectPort(ActionEvent event) throws IOException {
        if(isConnected)
        {
            serialPort.removeEventListener();
            serialPort.close();
            input.close();
            output.close();
            portStatus.setStyle(" -fx-color : RED ");
            portStatus.setText("Disconnected");

            System.out.println("Disconnected");
            isConnected=false;
            connectButton.setDisable(false);
        }
        else{            
            Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Connect the port First",
					"No Port Connected to the Wireless Sensor Network", "No Connection");
        }
    }
    
    
    
    
    
    
    /**
     * Crop Data save and open 
     * 
     * @author Harsh Bhavsar
     */

    public void cropDataAdd(){
        
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
         cropFromDatabase.getItems().setAll(crpName);
        }
        
        em.getTransaction().commit();
        System.out.println("Successfully added all crop data");
    }
    @FXML
    private void cropDataLoad(ActionEvent event) {
        
        em=emf.createEntityManager();
        em.getTransaction().begin();
        String selection=cropFromDatabase.getSelectionModel().getSelectedItem().toString();
        System.out.println(selection);        
        String [] sId = selection.split(":");
        System.out.println("error above :"+sId[0]);
        
        int id=Integer.parseInt(sId[0]);
        System.out.println("id is :"+id);
        Cropdata crd = em.find(Cropdata.class, id);
        if(crd !=null){
            cropName.setText(crd.getCropName());
            cropDuration.setText(crd.getCropDuration());
            cropAtmosphere.setText(crd.getCropAtmosphere());
            cropMoisture.setText(crd.getCropMoisture());
            cropRegion.setText(crd.getCropRegion());
            cropSeason.setText(crd.getCropSeason());
            cropSoilType.setText(crd.getCropSoilType());
            suggestedFertilizer.setText(crd.getSuggestedFertilizer());

            em.getTransaction().commit();

            System.out.println("Successfuuly loaded file");

            Dialogs.showInformationDialog(ukp.getPrimaryStage(),
                                            "Crop Data has beeen Loaded Successfully",
                                            "Crop Data Selection", "Crop Data ");
            
            saveCropButton.setDisable(true);
            
        }
        else{
            System.out.println("no file selected");
        }
        
    }
    @FXML
    private void saveCropData(ActionEvent event) {
        
        if(cropFileStatus){
            em=emf.createEntityManager();
            em.getTransaction().begin();
            Cropdata cd=new Cropdata();
            cd.setCropName(cropName.getText());
            cd.setCropAtmosphere(cropAtmosphere.getText());
            cd.setCropDuration(cropDuration.getText());
            cd.setCropMoisture(cropMoisture.getText());
            cd.setCropRegion(cropRegion.getText());
            cd.setCropSeason(cropSeason.getText());
            cd.setCropSoilType(cropSoilType.getText());
            cd.setSuggestedFertilizer(suggestedFertilizer.getText());
            
            em.persist(cd);
            em.getTransaction().commit();
            System.out.println("Successfully Saved to your database");
            
            Dialogs.showInformationDialog(ukp.getPrimaryStage(), "You have successfully saved data to database",
                    "Saving of Data to the Database",
                    "Saving Status");  
            
        }
        else{
            
            Dialogs.showErrorDialog(ukp.getPrimaryStage(),
					"Please select appropriate file format for crop data",
					"Not file selected or loaded properly", "Crop Data Error");
        }
    }
    @FXML
    private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);                
                File file = fileChooser.showOpenDialog(ukp.getPrimaryStage());

		if (file != null) {
                    try {
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(file);
                        doc.getDocumentElement().normalize();
                        if(doc.getDocumentElement().getNodeName().equals("cropData"))
                        {
                        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                        NodeList nList = doc.getElementsByTagName("cropData");
                        System.out.println("-----------------------");
                        for (int cropCount = 0; cropCount < nList.getLength(); cropCount++) {
                            Element eElement =(Element) nList.item(cropCount);
                            cropName.setText(getTagValue("cropName", eElement));
                            System.out.println("Crop Name : " + getTagValue("cropName", eElement));
                            cropSeason.setText(getTagValue("cropSeason", eElement));
                            System.out.println("Crop Season : " + getTagValue("cropSeason", eElement));
                            cropDuration.setText(getTagValue("cropDuration", eElement));
                            System.out.println("Crop Duration : " + getTagValue("cropDuration", eElement));
                            cropAtmosphere.setText(getTagValue("cropAtmosphere", eElement));
                            System.out.println("Crop Atmosphere : " + getTagValue("cropAtmosphere", eElement));
                            cropMoisture.setText(getTagValue("cropMoisture", eElement));
                            System.out.println("Crop Moisture : " + getTagValue("cropMoisture", eElement));
                            suggestedFertilizer.setText(getTagValue("suggestFertilizer", eElement));
                            System.out.println("Suggested Fertilizer : " + getTagValue("suggestFertilizer", eElement));
                            cropSoilType.setText(getTagValue("cropSoilType", eElement));
                            System.out.println("Related Soil type : " + getTagValue("cropSoilType", eElement));
                            cropRegion.setText(getTagValue("cropRegion", eElement));
                            System.out.println("Recommended Region : " + getTagValue("cropRegion", eElement));
                            
                            Dialogs.showInformationDialog(ukp.getPrimaryStage(),
					"Crop Data has beeen Loaded Successfully",
					"Crop Data Selection", "Crop Data ");
                            
                            cropFileStatus=true;
                            saveCropButton.setDisable(false);
                            }
                        }else{
                            Dialogs.showErrorDialog(ukp.getPrimaryStage(),
					"Please select appropriate file format for crop data",
					"Invalid File format Error", "Crop Data Error");
                                        cropName.setText("");
                                        cropMoisture.setText("");
                                        cropAtmosphere.setText("");
                                        cropDuration.setText("");
                                        cropSeason.setText("");
                                        suggestedFertilizer.setText("");
                                        cropSoilType.setText("");
                                        cropRegion.setText("");
                                        cropFileStatus=false;
                                
                         }
                          } catch (ParserConfigurationException | SAXException | IOException ex) {
                            Dialogs.showErrorDialog(ukp.getPrimaryStage(),
					"There is problem while loading data. Reload the File",
					"Data Fetching Error", "Crop Data");
                                        cropName.setText("");
                                        cropMoisture.setText("");
                                        cropAtmosphere.setText("");
                                        cropDuration.setText("");
                                        cropSeason.setText("");
                                        suggestedFertilizer.setText("");
                                        cropSoilType.setText("");
                                        cropRegion.setText("");
                                        cropFileStatus=false;
                    }
                }
        }
    /**
     * 
     * Save Crop Data to Local PC
     * @param event 
     */
    @FXML
    private void saveToPc(ActionEvent event) {
        
        File personFile = ukp.getCropFilePath();
		if (personFile != null) {
			ukp.saveCropDataToFile(personFile);
		} else {
                                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                                        "XML files (*.xml)", "*.xml");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(ukp.getPrimaryStage());

                if (file != null) {
                        // Make sure it has the correct extension
                        if (!file.getPath().endsWith(".xml")) {
                                file = new File(file.getPath() + ".xml");
                        }
                        ukp.saveCropDataToFile(file);
                }
	}

    }

    private static String getTagValue(String sTag, Element eElement) {
            NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
            org.w3c.dom.Node nValue = (org.w3c.dom.Node) nlList.item(0);
            return nValue.getNodeValue();
        }

    
    
    
    
   
    @FXML
    private void openProjectFile(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showOpenDialog(ukp.getPrimaryStage());
		
		if (file != null) {
			//ukp.loadCropDataFromFile(file);
                       
		}
        
        
    }
    @FXML
    private void saveProjectFile(ActionEvent event) {
        
        File projectFile = ukp.getProjectFilePath();
		if (projectFile != null) {
			ukp.saveProjectDataToFile(projectFile);
		} else {
			saveAsProjectFile(event);
		}
    }
    @FXML
    private void saveAsProjectFile(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(ukp.getPrimaryStage());
        
        if (file != null) {
        	// Make sure it has the correct extension
        	if (!file.getPath().endsWith(".xml")) {
        		file = new File(file.getPath() + ".xml");
        	}
        	ukp.saveProjectDataToFile(file);
        }
    }
    @FXML
    private void newProjectFile(ActionEvent event) {
                
    }

    @FXML
    private void languageSelectionWindowOpen(ActionEvent event) {
        ukp.showLanguageSelection();
        
    }

    @FXML
    private void handleEnglishLanguage(ActionEvent event) throws IOException {
        ukp.handleEnglishLanguageSelection();
    }

    
    
    

     /**
      * Initialization of Main Application
      * 
      * @param url
      * @param rb 
      */   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {        
        try {
            dateInitialize();
            sumCluster();
            insertDataInitialize();
            portComboRefresh();
            autoDataUpdated();
            refreshMachineTreeView();
            refreshFarmTreeView();
            getBarChartData();
            cropDataAdd();
            weatherStatus();
            clusterAxis.setLabel("Name of Clusters");
            dataAxis.setLabel("Readings");
            
            solenoidValveList= FXCollections.observableArrayList(solenoidValveService.findAllbyName());          
            valveList.setItems(solenoidValveList);
           
           }
           catch (IOException | ParseException ex) {
            ex.getMessage();
            }        
        } 
        
    
    
    
    
    
     /**
      * Bar Chart Data
      * refresh the tree view automatically after the adding or deleting the resource.
      * 
      * @author harshbitss
      */ 
     public void getBarChartData(){     
            barchartData.clear();
            series.getData().clear();
            barChart.getData().clear();
            em = emf.createEntityManager();         
            em.getTransaction().begin();
            Query query1=em.createQuery("SELECT c FROM ClusterNode c");
            List lst1=query1.getResultList();          
            Iterator it1=lst1.iterator();            
            while(it1.hasNext())
            {
 
                
                ClusterNode cln=(ClusterNode) it1.next();               
                 final XYChart.Data<String, Double> data = new XYChart.Data(cln.getClusterId(), cln.getFinalReading());
                   data.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) 
                    {
                        setNodeStyle(data);            
                    } 
        }
      });                                                                        
                series.getData().add(data);
                barchartData.add(new ClusterNode(cln.getClusterId(),cln.getFinalReading()));               
                System.out.println(it1+"::"+cln.getFinalReading());                
            }
            em.getTransaction().commit();
            series.setName("Cluster Node reading");
            barChart.getData().addAll(series);
     }  
     public void refreshBarChartData(){
           
         Calendar cal=Calendar.getInstance();        
            //refreshStatus
            getBarChartData();
            Dialogs.showInformationDialog(ukp.getPrimaryStage(), "The Barchart has been refreshed",
                    "The refreshed data has been updated in the bar chart",
                    "Confirmed Refresh");
            DateFormat dateForm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");          	    
            refreshStatus.setText("Last Updated at "+dateForm.format(cal.getTime()));
     }
     private void setNodeStyle(XYChart.Data<String, Double> data) {
        Node node = data.getNode();
        if (data.getYValue().doubleValue() > 70) {
                node.setStyle("-fx-bar-fill: green;");
        } else if (data.getYValue().doubleValue() > 50) {
                node.setStyle("-fx-bar-fill: navy;");
        }
        else if (data.getYValue().doubleValue() <=30){
                node.setStyle("-fx-bar-fill: firebrick");                    
        }
        
  }
     @FXML
     private void refreshBarChart(ActionEvent event)throws Exception
    {
       refreshBarChartData();
    }
    
       /**
     * Period Combo box selection on click refresh data
     * 
     * @author Harsh Bhavsar
     */
     @FXML
     private void periodSelectionChart(ActionEvent event) {
        
        periodComboMethod();                
    }
     public void periodComboMethod(){
        Calendar cal=Calendar.getInstance();
        DateFormat dateForm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        String selectedPeriod=periodCombo.getSelectionModel().getSelectedItem().toString();
        switch (selectedPeriod) {
            
            case "Current":
                
                getBarChartData();
                System.out.println("You have selected last 6 hours status");
                Dialogs.showErrorDialog(ukp.getPrimaryStage(), "The Barchart has been refreshed",
                    "You have already loaded current status in the bar chart",
                    "Confirmed Refresh");                                         	    
                refreshStatus.setText("Last Updated at "+dateForm.format(cal.getTime()));                
                break;
                    
                
            case "last 6 hours":
                
                   barchartData.clear();
                   series.getData().clear();
                   barChart.getData().clear();
                   em = emf.createEntityManager();         
                   em.getTransaction().begin();
                   
                   Query query1=em.createQuery("SELECT c FROM ClusterNode c");
                   List lst1=query1.getResultList();          
                   Iterator it1=lst1.iterator();            
                   while(it1.hasNext())
                   {
                       ClusterNode cln=(ClusterNode) it1.next();               
                        final XYChart.Data<String, Double> data = new XYChart.Data(cln.getClusterId(), cln.getLast6Hours());
                          data.nodeProperty().addListener(new ChangeListener<Node>() {
                           @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                           if (node != null) 
                           {
                               setNodeStyle(data);            
                           } 
                        }
                      });                                                                        
                       series.getData().add(data);
                       barchartData.add(new ClusterNode(cln.getClusterId(),cln.getLast6Hours()));               
                       System.out.println(it1+"::"+cln.getLast6Hours());                
                   }
                   em.getTransaction().commit();
                   series.setName("Cluster Node reading");
                   barChart.getData().addAll(series);
                
                System.out.println("You have selected last 6 hours status");
                Dialogs.showInformationDialog(ukp.getPrimaryStage(), "The Barchart has been refreshed",
                    "You have selected last 6 hours status in the bar chart",
                    "Confirmed Refresh");                                         	    
                refreshStatus.setText("Last Updated at "+dateForm.format(cal.getTime()));                
                break;
                
            case "last 12 hours":
                
                   barchartData.clear();
                   series.getData().clear();
                   barChart.getData().clear();
                   em = emf.createEntityManager();         
                   em.getTransaction().begin();
                   
                   Query query2=em.createQuery("SELECT c FROM ClusterNode c");
                   List lst2=query2.getResultList();          
                   Iterator it2=lst2.iterator();            
                   while(it2.hasNext())
                   {
                       ClusterNode cln=(ClusterNode) it2.next();               
                        final XYChart.Data<String, Double> data = new XYChart.Data(cln.getClusterId(), cln.getLast12Hours());
                          data.nodeProperty().addListener(new ChangeListener<Node>() {
                           @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                           if (node != null) 
                           {
                               setNodeStyle(data);            
                           } 
                        }
                      });                                                                        
                       series.getData().add(data);
                       barchartData.add(new ClusterNode(cln.getClusterId(),cln.getLast12Hours()));               
                       System.out.println(it2+"::"+cln.getLast12Hours());                
                   }
                   em.getTransaction().commit();
                   series.setName("Cluster Node reading");
                   barChart.getData().addAll(series);
                                
                System.out.println("You have selected last 12 hours status");
                Dialogs.showInformationDialog(ukp.getPrimaryStage(), "The Barchart has been refreshed",
                    "You have selected last 12 hours status in the bar chart",
                    "Confirmed Refresh");                                
                refreshStatus.setText("Last Updated at "+dateForm.format(cal.getTime()));
               
                break;
                
            case "today":
                
                 barchartData.clear();
                   series.getData().clear();
                   barChart.getData().clear();
                   em = emf.createEntityManager();         
                   em.getTransaction().begin();
                   
                   Query query3=em.createQuery("SELECT c FROM ClusterNode c");
                   List lst3=query3.getResultList();          
                   Iterator it3=lst3.iterator();            
                   while(it3.hasNext())
                   {
                       ClusterNode cln=(ClusterNode) it3.next();               
                        final XYChart.Data<String, Double> data = new XYChart.Data(cln.getClusterId(), cln.getToday());
                          data.nodeProperty().addListener(new ChangeListener<Node>() {
                           @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                           if (node != null) 
                           {
                               setNodeStyle(data);            
                           } 
                        }
                      });                                                                        
                       series.getData().add(data);
                       barchartData.add(new ClusterNode(cln.getClusterId(),cln.getToday()));               
                       System.out.println(it3+"::"+cln.getToday());                
                   }
                   em.getTransaction().commit();
                   series.setName("Cluster Node reading");
                   barChart.getData().addAll(series);
                
                System.out.println("You have selected today's status");
                Dialogs.showInformationDialog(ukp.getPrimaryStage(), "The Barchart has been refreshed",
                    "You have selected today's status in the bar chart",
                    "Confirmed Refresh");                                
                refreshStatus.setText("Last Updated at "+dateForm.format(cal.getTime()));
        
                break;
                
            case "this week":
                                
                 barchartData.clear();
                   series.getData().clear();
                   barChart.getData().clear();
                   em = emf.createEntityManager();         
                   em.getTransaction().begin();
                   
                   Query query4=em.createQuery("SELECT c FROM ClusterNode c");
                   List lst4=query4.getResultList();          
                   Iterator it4=lst4.iterator();            
                   while(it4.hasNext())
                   {
                       ClusterNode cln=(ClusterNode) it4.next();               
                        final XYChart.Data<String, Double> data = new XYChart.Data(cln.getClusterId(), cln.getLastWeek());
                          data.nodeProperty().addListener(new ChangeListener<Node>() {
                           @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                           if (node != null) 
                           {
                               setNodeStyle(data);            
                           } 
                        }
                      });                                                                        
                       series.getData().add(data);
                       barchartData.add(new ClusterNode(cln.getClusterId(),cln.getLastWeek()));               
                       System.out.println(it4+"::"+cln.getLastWeek());                
                   }
                   em.getTransaction().commit();
                   series.setName("Cluster Node reading");
                   barChart.getData().addAll(series);
                   
                System.out.println("You have selected this week's status");
                Dialogs.showInformationDialog(ukp.getPrimaryStage(), "The Barchart has been refreshed",
                    "You have selected this week's status in the bar chart",
                    "Confirmed Refresh");                                
                refreshStatus.setText("Last Updated at "+dateForm.format(cal.getTime()));

                break;            
                
        }
        
    }
     
     
     
  
    
    
    
    
     /**
      *  Tree View for Machines
      *
      * @author Harsh Bhavsar
      * @throws java.io.IOException
      */
     public void refreshMachineTreeView() throws IOException
    {
       
        ep=new EntityProxy("WSN");         
        rootItem=new TreeItem<>(ep);
        rootItem.setExpanded(true);
        em=emf.createEntityManager();
        em.getTransaction().begin();
        Query query=em.createQuery("SELECT c FROM ClusterNode c");        
        List lst=query.getResultList();
        clusterNames.addAll(lst);
        Iterator it=lst.iterator();
        while(it.hasNext())
        {            
                ClusterNode tree=(ClusterNode) it.next();
                TreeItem<EntityProxy> item = new TreeItem<>(new EntityProxy(tree.getId(), EntityProxyType.CLUSTERNODE, tree.getClusterId()), new ImageView(clusterIcon));
                item.setExpanded(false);
                snList=tree.getSensorNodeCollection();
                for(SensorNode cn :snList)
                {                    
                    TreeItem<EntityProxy> item1 = new TreeItem<>(new EntityProxy(cn.getId(), EntityProxyType.SENSORNODE, cn.getSensorId()),new ImageView(sensorNodeIcon));
                    snlList=cn.getSensorListCollection();
                    for(SensorList sl :snlList){
                        TreeItem<EntityProxy> item2 = new TreeItem<>(new EntityProxy(sl.getId(), EntityProxyType.SENSORLIST, sl.getName()));
                        item1.getChildren().add(item2);
                    }
                    item.getChildren().add(item1);   
                }
                svList=tree.getSolenoidValveCollection();
                for (SolenoidValve sv : svList){
                    TreeItem<EntityProxy> item2 = new TreeItem<>(new EntityProxy(sv.getId(), EntityProxyType.SOLENOIDVALVE, sv.getValveId()), new ImageView(valveIcon));
                    item.getChildren().add(item2);
                }

                rootItem.getChildren().add(item);
        }
                  
        em.getTransaction().commit();        
        //em.close();      
        rootItem.setGraphic(wsnIcon);
        treeView.setRoot(rootItem);
        treeView.setEditable(true);               
        treeView.setCellFactory(TextFieldTreeCellImpl.forClusterNodeTreeView(nodeMenu,sensorMenu, sensorlMenu,valveMenu,wsnMenu, clusterNodeService, sensorNodeService, sensorListService, solenoidValveService));
             
        treeView.setOnEditCommit(
				new EventHandler<TreeView.EditEvent<EntityProxy>>() {
			@Override
			public void handle(TreeView.EditEvent<EntityProxy> item) {
				EntityProxy node = item.getNewValue();
				switch (node.getType()) {
					case CLUSTERNODE:  
                                            try{
                                                boolean match = false;
						ClusterNode cn = clusterNodeService.find( node.getId());
                                                for(ClusterNode sn1: clusterNames){
                                                    String snMatch1=sn1.getClusterId();
                                                    String clMatch=node.getLabel();
                                                    if(clMatch.contains(snMatch1)){
                                                       match=true;
                                                       break;
                                                    }
                                                }
                                                if(!match){                                                                                                                                                    
						cn.setClusterId(node.getLabel());
						clusterNodeService.merge(cn);
						break;
                                                }else{
                                                    
                                                     Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Give another name for the Cluster Node",
					"The name you are trying is already assigned.","Naming Error");
                                                  
                                                }}catch(RollbackException ex){
                                                        Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Give another name for the Solenoid Valve",
					"The name you are trying is already assigned.","Naming Error");
                                                        
                                                        
                                                    
                                                }
                                               
					case SENSORNODE:
                                            try{
                                                boolean match1 = false;
						SensorNode sn = sensorNodeService.find(node.getId());
                                                for(SensorNode sn1: snList){
                                                    String snMatch1=sn1.getSensorId();
                                                    String clMatch=node.getLabel();
                                                    if(clMatch.contains(snMatch1)){
                                                       match1=true;
                                                       break;
                                                    }
                                                }
                                                if(!match1){ 
						sn.setSensorId(node.getLabel());
						sensorNodeService.merge(sn);
						break;
                                                 }
                                                else{
                                                   Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Give another name for the Sensor Node",
					"The name you are trying is already assigned.","Naming Error");
                                                
                                                }}catch(RollbackException ex){
                                                        Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Give another name for the Solenoid Valve",
					"The name you are trying is already assigned.","Naming Error");
                                                        
                                                        
                                                    
                                                }
                                        case SENSORLIST:
                                                SensorList sl=sensorListService.find(node.getId());
                                                sl.setName(node.getLabel());
                                                sensorListService.merge(sl);
                                                break;
                                        case SOLENOIDVALVE:
                                            try{
                                                boolean match2 = false;
                                                SolenoidValve sv=solenoidValveService.find(node.getId());
                                                for(SolenoidValve sv1: svList){
                                                    String snMatch1=sv1.getValveId();
                                                    String clMatch=node.getLabel();
                                                    if(clMatch.contains(snMatch1)){
                                                       match2=true;
                                                       break;
                                                    }
                                                }
                                                if(!match2){ 
                                                sv.setValveId(node.getLabel());
                                                solenoidValveService.merge(sv);
                                                break;
                                                }else{
                                                    Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Give another name for the Solenoid Valve",
					"The name you are trying is already assigned.","Naming Error");
                                                }}catch(RollbackException ex){
                                                        Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Give another name for the Solenoid Valve",
					"The name you are trying is already assigned.","Naming Error");
                                                        
                                                        
                                                    
                                                }
				}
			}
		});
    }
     public void createWSNContextMenu(){
         
         MenuItem refresh=new MenuItem("Refresh Tree View",new ImageView(refreshIcon));
         wsnMenu.getItems().add(refresh);
         refresh.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                 try {
                     refreshMachineTreeView();
                 } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                 }
             }
         });
         
     }
     public void createClusterNodeContextMenu() {	
		MenuItem addNodeItem = new MenuItem("Add Sensor Node",new ImageView(sensorNodeIcon));
		nodeMenu.getItems().add(addNodeItem);                              
		addNodeItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
                            
                                try{
				SensorNode child = new SensorNode();
				child.setSensorId("New Node");                                
				TreeItem<EntityProxy> selected = treeView.getSelectionModel().getSelectedItem();
				ClusterNode parent = clusterNodeService.find( selected.getValue().getId());
                                child.setClusterId(parent);
                                
                                sensorNodeService.persist(child);
				clusterNodeService.addChild(parent, child);
				TreeItem<EntityProxy> newNode = new TreeItem<>(new EntityProxy(child.getId(), EntityProxyType.SENSORNODE, child.getSensorId()),new ImageView(sensorNodeIcon));
				selected.getChildren().add(newNode);
                                }
                                catch(RollbackException ex){
                                    ex.getMessage();
                                    Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Give the name for the Sensor Node where name is 'NEW NODE'.",
					"The node you have added is still remains UN NAMED.","Naming Error");
                                    
                                }
			}
		});

		MenuItem deleteDepartmentItem = new MenuItem("Delete Node",new ImageView(deleteIcon));
		nodeMenu.getItems().add(deleteDepartmentItem);
		deleteDepartmentItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				TreeItem<EntityProxy> selectedItem = treeView.getSelectionModel().getSelectedItem();
				if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
				}

				ClusterNode selectedCluster = clusterNodeService.find(selectedItem.getValue().getId());
				TreeItem<EntityProxy> parentCluster = (treeView.getRoot());
                                
                                
				//Remove the selected department from it's parent in both the
				//tree and the persisted version
				TreeItem<EntityProxy> parentItem = selectedItem.getParent();
				parentItem.getChildren().remove(selectedItem);
				parentCluster.getChildren().remove(selectedCluster);
				clusterNodeService.merge(selectedCluster);
				clusterNodeService.remove(selectedCluster);
			}
		});  
                
                MenuItem addValveItem = new MenuItem("Add Solenoid Valve",new ImageView(valveIcon));
		nodeMenu.getItems().add(addValveItem);                              
		addValveItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
                            try{
				SolenoidValve child = new SolenoidValve();
				child.setValveId("New Valve");                                
				TreeItem<EntityProxy> selected = treeView.getSelectionModel().getSelectedItem();
				ClusterNode parent = clusterNodeService.find( selected.getValue().getId());
                                child.setClusterId(parent);
                                
                                solenoidValveService.persist(child);
				clusterNodeService.addChild(parent, child);
				TreeItem<EntityProxy> newDepartment = new TreeItem<>(new EntityProxy(child.getId(), EntityProxyType.SENSORNODE, child.getValveId()), new ImageView(valveIcon));
				selected.getChildren().add(newDepartment);
                            }
                            catch(RollbackException ex){
                                    ex.getMessage();
                                    Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Give the name for the Sensor valve where name is 'NEW VALVE'.",
					"The valve you have added is still remains UN NAMED.","Naming Error");
                                    
                                }
                            
			}
		});
	}
     public void createSensorListContextMenu(){
        
        MenuItem addSensor=new MenuItem("Add Sensor",new ImageView(sensorIcon));
        
        sensorMenu.getItems().add(addSensor);
        addSensor.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent t) {
                        
				SensorList child = new SensorList();				                            
                                child.setName("New Sensor");                               
				TreeItem<EntityProxy> selected = treeView.getSelectionModel().getSelectedItem();
				SensorNode parent = sensorNodeService.find( selected.getValue().getId());
                                child.setNodeId(parent);                                
                                sensorListService.persist(child);
				sensorNodeService.addChild(parent, child);
				TreeItem<EntityProxy> newSensor = new TreeItem<>(new EntityProxy(child.getId(), EntityProxyType.SENSORLIST, child.getName()));
				selected.getChildren().add(newSensor);                        
                    }
                });
    
    
    MenuItem deleteDepartmentItem = new MenuItem("Delete Node",new ImageView(deleteIcon));
		sensorMenu.getItems().add(deleteDepartmentItem);
		deleteDepartmentItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				TreeItem<EntityProxy> selectedItem = treeView.getSelectionModel().getSelectedItem();
				if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
				}

				SensorNode selectedCluster = sensorNodeService.find(selectedItem.getValue().getId());
				TreeItem<EntityProxy> parentCluster = (treeView.getRoot());
                                
                                
				//Remove the selected department from it's parent in both the
				//tree and the persisted version
				TreeItem<EntityProxy> parentItem = selectedItem.getParent();
				parentItem.getChildren().remove(selectedItem);
				parentCluster.getChildren().remove(selectedCluster);
				sensorNodeService.merge(selectedCluster);
				sensorNodeService.remove(selectedCluster);
			}
		});  
    
    }
     public void createSensorListMenu(){        
        MenuItem deleteDepartmentItem = new MenuItem("Delete Sensor",new ImageView(deleteIcon));
		sensorlMenu.getItems().add(deleteDepartmentItem);
		deleteDepartmentItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				TreeItem<EntityProxy> selectedItem = treeView.getSelectionModel().getSelectedItem();
				if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
				}

				SensorList selectedCluster = sensorListService.find(selectedItem.getValue().getId());
				TreeItem<EntityProxy> parentCluster = (treeView.getRoot());
                                                                
				//Remove the selected department from it's parent in both the
				//tree and the persisted version
				TreeItem<EntityProxy> parentItem = selectedItem.getParent();
				parentItem.getChildren().remove(selectedItem);
				parentCluster.getChildren().remove(selectedCluster);
				sensorListService.merge(selectedCluster);
				sensorListService.remove(selectedCluster);
			}
		});  
    }     
     public void createSolenoidValveContextMenu(){
         
         MenuItem onValve =new MenuItem("On Valve", new ImageView(on));
         valveMenu.getItems().add(onValve);
         onValve.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                
                 if(isConnected){
                      TreeItem<EntityProxy> selectedItem = treeView.getSelectionModel().getSelectedItem();
				if (selectedItem.getParent() == null) {
                                    return;
                                }
                                    String text="a";
                                     text=text+"\r";
                                 //   int txt=1;
                                try {
                                       // boolean st;//=false;
                                        //output.write(text);
                                      //  output.write(text);
                                    output.write(text.getBytes());
                                        System.out.println("successful");
                                        
                                        //st=true;
                                        
                                    } catch (IOException ex) {
                                            ex.getMessage();
                                    }
                 }
        else{            
            Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Connect the port First",
					"No Port Connected to the Wireless Sensor Network", "No Connection");
        }
                                
                                
				}                                                             
         });
         
         MenuItem offValve =new MenuItem("Off Valve", new ImageView(off));
         valveMenu.getItems().add(offValve);
         offValve.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                 
                    
                    if(isConnected){
                        TreeItem<EntityProxy> selectedItem = treeView.getSelectionModel().getSelectedItem();
                    
				if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
				}            
                                String text="b";
                                     text=text+"\r";
                                 //   int txt=1;
                                try {
                                       // boolean st;//=false;
                                        //output.write(text);
                                      //  output.write(text);
                                    output.write(text.getBytes());
                                        System.out.println("successful");
                                        
                                        //st=true;

                                    } catch (IOException ex) {
                                            ex.printStackTrace();
                                    }
                                }
                    else{            
                            Dialogs.showWarningDialog( ukp.getPrimaryStage(),
                                                        "Please Connect the port First",
                                                        "No Port Connected to the Wireless Sensor Network", "No Connection");
                        }
            }              
         });
         
         MenuItem deleteValve =new MenuItem("Delete Valve",new ImageView(deleteIcon));
         valveMenu.getItems().add(deleteValve);
         deleteValve.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent t) {
                 TreeItem<EntityProxy> selectedItem = treeView.getSelectionModel().getSelectedItem();
				if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
				}                                
                                SolenoidValve selectedValve= solenoidValveService.find(selectedItem.getValue().getId());                                
                                TreeItem<EntityProxy> parentCluster = (treeView.getRoot());
                                TreeItem<EntityProxy> parentItem = selectedItem.getParent();
				parentItem.getChildren().remove(selectedItem);
				parentCluster.getChildren().remove(selectedValve);
				solenoidValveService.merge(selectedValve);
				solenoidValveService.remove(selectedValve);

             }
         });
     }
     
     
     
     
     
     
     
     /**
      * Tree View for Farmers
      * 
      * @author Harsh Bhavsar
      */
     
     public void refreshFarmTreeView(){
        epf=new EntityProxy("Farmer");
         
        farmRootItem=new TreeItem<>(epf);
        farmRootItem.setExpanded(true);
        em=emf.createEntityManager();
        em.getTransaction().begin();
         
        Query query=em.createQuery("SELECT f FROM FarmTbl f");        
        List lst=query.getResultList();
        farmNames.addAll(lst);
        Iterator it=lst.iterator();
        while(it.hasNext())
        {
            FarmTbl ft=(FarmTbl) it.next();
            TreeItem<EntityProxy> item = new TreeItem<>(new EntityProxy(ft.getId(), EntityProxyType.FARM, ft.getFarmDescription()), new ImageView(farmIcon));
            item.setExpanded(false);
            fieldList=ft.getFieldTblCollection();
            for(FieldTbl flt: fieldList){
                
                 TreeItem<EntityProxy> item1 = new TreeItem<>(new EntityProxy(flt.getId(), EntityProxyType.FIELD, flt.getFieldDescription()),new ImageView(fieldIcon));
                 item.getChildren().add(item1);  
                 
                 for(Cropdata cd:flt.getCropdataCollection()){
                     
                     TreeItem<EntityProxy> item2 = new TreeItem<>(new EntityProxy(cd.getId(), EntityProxyType.CROP, cd.getCropName()),new ImageView(cropIcon));
                     item1.getChildren().add(item2);                      
                 }
            }
            farmRootItem.getChildren().add(item);            
        }
        
        em.getTransaction().commit();        
        //em.close();      
        farmRootItem.setGraphic(farmerIcon);
        farmTreeView.setRoot(farmRootItem);
        farmTreeView.setEditable(true);    
        farmTreeView.setCellFactory(TextFieldTreeCellImplFarm.forFarmTreeView(farmerMenu, farmMenu, fieldMenu, cropMenu, farmerService, farmService, fieldService));
        farmTreeView.setOnEditCommit(
                new EventHandler<TreeView.EditEvent<EntityProxy>>() {

             @Override
             public void handle(TreeView.EditEvent<EntityProxy> item) {
                 EntityProxy node = item.getNewValue();
				switch (node.getType()) {
                                        case FARM:
                                            FarmTbl fmt = farmService.find( node.getId());
                                            fmt.setFarmDescription(node.getLabel());
                                            farmService.merge(fmt);
                                            break;
                                            
                                        case FIELD:
                                            FieldTbl fl = fieldService.find( node.getId());
                                            fl.setFieldDescription(node.getLabel());
                                            fieldService.merge(fl);
                                            break;
                                }
                 
             }
         });
     }
     
     public void createFarmerContextMenu(){
         MenuItem edit=new MenuItem("Edit Farmer Details", new ImageView(editIcon));
         farmerMenu.getItems().add(edit);
         edit.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                 try {
                     em=emf.createEntityManager();
                     em.getTransaction().begin();
                     CriteriaBuilder qb = em.getCriteriaBuilder();
                     CriteriaQuery<Long> cq = qb.createQuery(Long.class);
                     cq.select(qb.count(cq.from(FarmerTbl.class)));
                     long count=em.createQuery(cq).getSingleResult();
                     try{
                     FarmerTbl fmt=(FarmerTbl) em.createQuery("SELECT f FROM FarmerTbl f").getSingleResult();
                     if(count==1){
                        FarmerTbl farmert=em.find(FarmerTbl.class, fmt.getId());
                        System.out.println(farmert.getFirstName());
                        ukp.showFarmerWindowOpen(farmert);
                     }}
                      catch(NoResultException ex){
                     Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Assing Farmer using the MenuBar on the top with: Add Resources",
					"You Have not Assigned Farmer \n"
                                                + "You have to first assign Farmer Details"
                                            , "Not Farmer Assigned");                       
                    } 
                 } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                 }
             }
         });
         
          MenuItem delete=new MenuItem("Delete Farmer Details",new ImageView(deleteIcon));
          farmerMenu.getItems().add(delete);
          delete.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                 Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Deletion of farmer details will delete all farm and field under the current farmer",
					"You are going to delete farmer \n"
                                                + "If you want to change farmer, you may still update farmer info"
                                            , "Confirm Delete Farmer");                       
                 em=emf.createEntityManager();
                 em.getTransaction().begin();
                 CriteriaBuilder qb = em.getCriteriaBuilder();
                 CriteriaQuery<Long> cq = qb.createQuery(Long.class);
                 cq.select(qb.count(cq.from(FarmerTbl.class)));
                 long count=em.createQuery(cq).getSingleResult();
                 try{
                 FarmerTbl fmt=(FarmerTbl) em.createQuery("SELECT f FROM FarmerTbl f").getSingleResult();
                 if(count==1){
                     FarmerTbl farmert=em.find(FarmerTbl.class, fmt.getId());
                     System.out.println(farmert.getFirstName());
                     em.remove(farmert);
                     em.getTransaction().commit();
                 }
                 }
                 catch(NoResultException ex){
                     Dialogs.showWarningDialog( ukp.getPrimaryStage(),
                             "Please Assing Farmer using the MenuBar on the top with: Add Resources",
                             "You Have not Assigned Farmer \n"
                                     + "You have to first assign Farmer Details"
                             , "Not Farmer Assigned");
                 }
                 
             }
         });
          
          MenuItem addFarm=new MenuItem("Add Farm", new ImageView(farmIcon));
          farmerMenu.getItems().add(addFarm);
          addFarm.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                
                             farmDataAdd();
             }
         });
     }
     public void createFarmContextMenu(){

         MenuItem edit=new MenuItem("Edit Farm Details",new ImageView(editIcon));
         farmMenu.getItems().add(edit);
         edit.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                 
                 TreeItem<EntityProxy> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
                 if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
                    }
                 
                        FarmTbl selectedFarm=farmService.find(selectedItem.getValue().getId());
                        ukp.showFarmWindowOpen(selectedFarm);
                        refreshFarmTreeView();
             }
         });
         
         MenuItem delete=new MenuItem("Delete Farm",new ImageView(deleteIcon));
         farmMenu.getItems().add(delete);
         delete.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                 
                 TreeItem<EntityProxy> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
				if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
				}
                                FarmTbl selectedFarm = farmService.find(selectedItem.getValue().getId());
				TreeItem<EntityProxy> parentFarm = (farmTreeView.getRoot());
                                
                                
				//Remove the selected department from it's parent in both the
				//tree and the persisted version
				TreeItem<EntityProxy> parentItem = selectedItem.getParent();
				parentItem.getChildren().remove(selectedItem);
				parentFarm.getChildren().remove(selectedFarm);
				farmService.merge(selectedFarm);
				farmService.remove(selectedFarm);  
          
             }
         });
         
         MenuItem addField=new MenuItem("Add Field", new ImageView(fieldIcon));
         farmMenu.getItems().add(addField);
         addField.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                 
                                FieldTbl child = new FieldTbl();				                            
                                child.setFieldDescription("New Field");                               
				TreeItem<EntityProxy> selected = farmTreeView.getSelectionModel().getSelectedItem();
				FarmTbl parent = farmService.find( selected.getValue().getId());
                                child.setFarmId(parent);                                
                                fieldService.persist(child);
				farmService.addChild(parent, child);
				TreeItem<EntityProxy> newField = new TreeItem<>(new EntityProxy(child.getId(), EntityProxyType.FIELD, child.getFieldDescription()),new ImageView(fieldIcon));
				selected.getChildren().add(newField);                  
                                //refreshFarmTreeView();
                                
             }
         });
     }
     public void createFieldContextMenu(){
         
         MenuItem edit=new MenuItem("Edit Field Details",new ImageView(editIcon));
         fieldMenu.getItems().add(edit);
         edit.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
                 
                   TreeItem<EntityProxy> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
                    if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
                    }
                 
                        FieldTbl selectedField=fieldService.find(selectedItem.getValue().getId());
                        ukp.showFieldWindowOpen(selectedField);
                        refreshFarmTreeView();
                 
             }
         });
         
         MenuItem delField=new MenuItem("Delete Field", new ImageView(deleteIcon));
         fieldMenu.getItems().add(delField);
         delField.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {
          
                 TreeItem<EntityProxy> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
				if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
				}
                                FieldTbl selectedField = fieldService.find(selectedItem.getValue().getId());
				TreeItem<EntityProxy> parentField = (farmTreeView.getRoot());
                                
                                
				//Remove the selected department from it's parent in both the
				//tree and the persisted version
				TreeItem<EntityProxy> parentItem = selectedItem.getParent();
				parentItem.getChildren().remove(selectedItem);
				parentField.getChildren().remove(selectedField);
				fieldService.merge(selectedField);
				fieldService.remove(selectedField);
             }
         });
         
         
         MenuItem assignCrop=new MenuItem("Assign Crop",new ImageView(cropIcon));
         fieldMenu.getItems().add(assignCrop);
         assignCrop.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent t) {
                  em=emf.createEntityManager();
                  em.getTransaction().begin();
                  CriteriaBuilder qb = em.getCriteriaBuilder();
                  CriteriaQuery<Long> cq = qb.createQuery(Long.class);
                  cq.select(qb.count(cq.from(Cropdata.class)));        
                  long count=em.createQuery(cq).getSingleResult();
                  em.getTransaction().commit();
                  if(count>0){
                      System.out.println("Crop data assign menu selected");
                      TreeItem<EntityProxy> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
                        if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
                        }
                        selectedItem.getValue().toString();
                        System.out.println("selected "+selectedItem.getValue().toString());                  
                        FieldTbl ft=fieldService.find(selectedItem.getValue().getId());                       
                        boolean okClicked=ukp.showAssignCropWindowOpen(ft);
                        
                        if(okClicked){
                            refreshFarmTreeView();
                        }
                      
                  }else{
                      
                      Dialogs.showErrorDialog(ukp.getPrimaryStage(),
					"Please save any crop file data into your database first, then try again \n"
                                                + "(With the use of Intelligent Assistance Tab) ",
					"Not crop file Found in your database", "Crop Data Error");
                      
                  }
              }
          });
         
         MenuItem assignMachine=new MenuItem("Assign Machine",new ImageView(machineIcon));
         fieldMenu.getItems().add(assignMachine);
         assignMachine.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent t) {

                 
             }
         });
     }
     public void createCropContextMenu(){
         
         MenuItem aboutCrop=new MenuItem("About Crop",new ImageView(aboutIcon));
         cropMenu.getItems().add(aboutCrop);
         aboutCrop.setOnAction(new EventHandler<ActionEvent>() {             
              @Override
              public void handle(ActionEvent t) {   
                  TreeItem<EntityProxy> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
                        if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
                        }
                        em=emf.createEntityManager();
                        em.getTransaction().begin();
                        int id=selectedItem.getValue().getId();
                   Cropdata crd = em.find(Cropdata.class, id);
                    if(crd !=null){
                        cropName.setText(crd.getCropName());
                        cropDuration.setText(crd.getCropDuration());
                        cropAtmosphere.setText(crd.getCropAtmosphere());
                        cropMoisture.setText(crd.getCropMoisture());
                        cropRegion.setText(crd.getCropRegion());
                        cropSeason.setText(crd.getCropSeason());
                        cropSoilType.setText(crd.getCropSoilType());
                        suggestedFertilizer.setText(crd.getSuggestedFertilizer());

                        em.getTransaction().commit();

                        System.out.println("Successfuuly loaded file");

                    }
                  SkinBase skin = (SkinBase) allTab.getSkin();
                  TabPaneBehavior tabPaneBehavior = (TabPaneBehavior) skin.getBehavior();                  
                  tabPaneBehavior.selectTab(intelligentTab);    

                  Dialogs.showInformationDialog(ukp.getPrimaryStage(),
                                                        "Crop Data has beeen Loaded Successfully",
                                                        "Crop Data Selection", "Crop Data ");

                        saveCropButton.setDisable(true);
              }
          });
         
         MenuItem deleteCrop=new MenuItem("Delete Crop",new ImageView(deleteIcon));
         cropMenu.getItems().add(deleteCrop);
         deleteCrop.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent t) {
                  
                  TreeItem<EntityProxy> selectedItem = farmTreeView.getSelectionModel().getSelectedItem();
                        if (selectedItem.getParent() == null) {
					return; //Can't delete the root node.
                        }
//                        
                        TreeItem<EntityProxy> parentItem = selectedItem.getParent();
//                        FieldTbl ft=fieldService.find(parentItem.getValue().getId());
//                        
//                        for(Cropdata cd:ft.getCropdataCollection()){                            
                            em=emf.createEntityManager();
                            em.getTransaction().begin();
                          //  Query q=em.createQuery("DELETE c. FROM Cropdata c");
                            Cropdata cdr=cropdataService.find(selectedItem.getValue().getId());                           
                            //em.remove(cdr.getFieldId());                            
                            //cd.getFieldId();     
                            cdr.setFieldId(new FieldTbl(0));
                            em.getTransaction().commit();
                            parentItem.getChildren().remove(selectedItem);
                            refreshFarmTreeView();                            
                        //}                 
              }
          });
         
     }
     
     
     
     
     
     

    /**
    *  Add Cluster Node Menu 
    * 
    * @author Harsh Bhavsar  
    */
    @FXML
    private void newClusterWindowOpen(ActionEvent event) throws IOException {             
        clusterNodeAdd();
    }    
    @FXML
    private void addClusterNode(MouseEvent event) throws IOException {
       clusterNodeAdd();
    }
    public void clusterNodeAdd() throws IOException{
        ClusterNode clrTemp =new ClusterNode();
        boolean okClicked= ukp.showAddClusterOpen(clrTemp);
        if(okClicked){
        ukp.getClusterData().add(clrTemp);
        em=emf.createEntityManager();
        em.getTransaction().begin();
        ClusterNode cn=new ClusterNode();
        cn.setClusterId(clrTemp.getClusterId().toString());
        em.persist(cn);     
        em.getTransaction().commit();
        refreshMachineTreeView();
        }
    }
    
    
    
    
    
    
    
    /**
    *  Add Farmer Data Menu 
    * 
    * @author Harsh Bhavsar  
     * @throws java.io.IOException  
    */
    public void farmerWindowOpen() throws IOException{
        em=emf.createEntityManager();
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(FarmerTbl.class)));        
        long count=em.createQuery(cq).getSingleResult();
        if(count!=1){
            farmerDataAdd();
        }
        else{
            Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Right Click on the Farm Tree View to Edit Details",
					"You Have Already Assigned Farmer \n"
                                                + "You Can Edit Farmer Details"
                                            , "Already Assigned");                       
        }
    }
    @FXML
    private void newFarmerWindowOpen(ActionEvent event) throws IOException {
                 
       farmerWindowOpen();       
    }    
    public void farmerDataAdd() throws IOException{
        FarmerTbl farmerTbl=new FarmerTbl();
        boolean okClicked=ukp.showFarmerWindowOpen(farmerTbl);
        if(okClicked){
            ukp.getFarmerData().add(farmerTbl);
            em=emf.createEntityManager();
            em.getTransaction().begin();
            FarmerTbl farmr=new FarmerTbl();
            farmr.setFirstName(farmerTbl.getFirstName().toString());
            farmr.setMiddleName(farmerTbl.getMiddleName().toString());
            farmr.setLastName(farmerTbl.getLastName().toString());
            farmr.setBirthDay(farmerTbl.getBirthDay());
            farmr.setPhone(farmerTbl.getPhone());            
            farmr.setStreet1(farmerTbl.getStreet1().toString());
            farmr.setStreet2(farmerTbl.getStreet2().toString());
            farmr.setCity(farmerTbl.getCity().toString());
            farmr.setState(farmerTbl.getState().toString());
            farmr.setPin(farmerTbl.getPin());
            farmr.setCountry(farmerTbl.getCountry().toString());
            
            em.persist(farmr);
            em.getTransaction().commit();
            
            System.out.println("Farmer Data Added");
            
        }
        
    }
    
    
    
    
    
    /**
    *  Add Farm Data Menu 
    * 
    * @author Harsh Bhavsar  
    */
    @FXML
    private void addNewFarm(ActionEvent event) {
        
        farmDataAdd();
    }    
    public void farmDataAdd(){
        FarmTbl farmTbl=new FarmTbl();
        boolean okClicked=ukp.showFarmWindowOpen(farmTbl);
        if(okClicked){
            ukp.getFarmData().add(farmTbl);
            em=emf.createEntityManager();
            em.getTransaction().begin();
            FarmTbl farmd=new FarmTbl();
            
            farmd.setFarmDescription(farmTbl.getFarmDescription().toString());
            farmd.setMappedArea(farmTbl.getMappedArea());
            farmd.setLegalArea(farmTbl.getLegalArea());
            farmd.setTillableArea(farmTbl.getTillableArea());            
            farmd.setFarmerId(farmTbl.getFarmerId());
            
            em.persist(farmd);
            em.getTransaction().commit();
            
            System.out.println("Farm Data Added");
            refreshFarmTreeView();
        }
    }
    
   
    

     
    

 /**
 * Auto Update of the reading into the database.....!!!!!
 * 
 * @author Harsh Bhavsar  
 */

    private void autoDataUpdated() {
        autoData=new Timer();
            TimerTask tt=new TimerTask() {

            @Override
            public void run() {                       
                try {
                    addSensorNodeData();
                    System.out.println("sensor node updated");
                    addClusterNodeData();
                    System.out.println("cluster node updated");                
                    addFinalClusterData();
                    System.out.println("FInal Data Added");
                    automaticClearData();
                    System.out.println("Clear Data");
                 } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
       autoData.schedule(tt, 1000, 1000*120);        
    }
    public void addSensorNodeData() throws ParseException{       
       
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        String date=dateFormat.format(cal.getTime());        
        Date readingDate=dateFormat.parse(date);        
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        for(SensorNode sensorNd: sensorNodeListing)
        {
            System.out.println("inside listing");
            for(SensorList sl:sensorNd.getSensorListCollection()){
                System.out.println("inside sensor listing");      
                
                em=emf.createEntityManager();
                em.getTransaction().begin();
                Query q = em.createQuery("SELECT s FROM SensorData s WHERE s.readingDate = :readingDate AND s.readingHour = :readingHour AND s.sensorsId =:sensorsId");
                q.setParameter("readingDate", readingDate);
                q.setParameter("readingHour", hour);        
                q.setParameter("sensorsId", sl);         
                List ls=q.getResultList();        
                Iterator it=ls.iterator();
                double reading=0;
                int count=0;
                while(it.hasNext()){
                    SensorData sd=(SensorData) it.next();                            
                    reading=reading+sd.getReading();
                    count++;  
                    System.out.println("inside count");
                }                                                                                             
                if(count>0){                                      
                    SensorNodeData snd=new SensorNodeData();                
                    snd.setReading((reading/count));
                    snd.setSensorId(sensorNd);                
                    snd.setReadingHour(hour);
                    snd.setReadingTime(cal.getTime());
                    snd.setData(readingDate);
                    em.persist(snd); 
                    em.getTransaction().commit();     
                }                
            }           
        }        
    }    
    public void addClusterNodeData() throws ParseException{
            
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        String date=dateFormat.format(cal.getTime());        
        Date readingDate=dateFormat.parse(date);       
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        
        for(ClusterNode cluster: clusterNodeListing)
        {
            System.out.println("inside cluster");
            for(SensorNode sensorNd:cluster.getSensorNodeCollection()){
                em=emf.createEntityManager();
                em.getTransaction().begin();
                Query q = em.createQuery("SELECT s FROM SensorNodeData s WHERE s.data = :readingDate AND s.readingHour = :readingHour AND s.sensorId =:sensorId");
                q.setParameter("readingDate", readingDate);
                q.setParameter("readingHour", hour);        
                q.setParameter("sensorId", sensorNd);         
                List ls=q.getResultList();        
                Iterator it=ls.iterator();
                double reading=0;
                int count=0;
                while(it.hasNext()){
                    SensorNodeData sd=(SensorNodeData) it.next();                            
                    reading=reading+sd.getReading();
                    count++;  
                    System.out.println("inside count");
                }                                  
                if(count>0){
                    ClusterNodeReading cnr=new ClusterNodeReading();
                    cnr.setClusterId(cluster);
                    cnr.setReading((reading/count));
                    cnr.setReadingHour(hour);
                    cnr.setReadingTime(cal.getTime());
                    cnr.setReadingDate(readingDate);
                    em.persist(cnr);
                    em.getTransaction().commit();
                }                
            }            
        }          
    }    
    public void addFinalClusterData() throws ParseException{
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        String date=dateFormat.format(cal.getTime());        
        Date readingDate=dateFormat.parse(date);        
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        for(ClusterNode cluster:clusterNodeListing){              
                em=emf.createEntityManager();              
                em.getTransaction().begin();
                Query q = em.createQuery("SELECT c FROM ClusterNodeReading c WHERE c.readingDate = :readingDate AND c.readingHour = :readingHour AND c.clusterId =:clusterId");
                q.setParameter("readingDate", readingDate);
                q.setParameter("readingHour", hour);        
                q.setParameter("clusterId", cluster);
                try{
                    List ls=q.getResultList();        
                    Iterator it=ls.iterator();
                    double reading=0;
                    int count=0;
                    while(it.hasNext()){
                        ClusterNodeReading cnr=(ClusterNodeReading) it.next();    
                        
                        reading=reading+cnr.getReading();
                        count++;                        
                    }            
                    if(count>0){
                        ClusterNode clusterN=em.find(ClusterNode.class, cluster.getId());                                        
                        clusterN.setFinalReading(reading/count);
                        em.getTransaction().commit();                        
                    }
                    
                   }
                    catch(NoResultException ex){
                        System.out.println(ex.getMessage());                    
                    }           
        }
    }
    
    
    /**
     * Automatic Clear data
     * 
     * 
     */
    private void automaticClearData() throws ParseException{
    
              clearSensorData();
              clearSensorNodeData();
              clearClusterNodeData();
              
    }    
    public void clearSensorData() throws ParseException{
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        String date=dateFormat.format(cal.getTime());        
        Date readingDate=dateFormat.parse(date);
        java.sql.Date readingDt=new java.sql.Date(readingDate.getTime());
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        System.out.println("Hour is :"+hour);
             
                    em=emf.createEntityManager();
                    em.getTransaction().begin();
                    Query q=em.createQuery("SELECT s FROM SensorData s WHERE s.readingHour< :readingHour OR s.readingDate<> :readingDate");
                    q.setParameter("readingHour", hour);
                    q.setParameter("readingDate", readingDt);
                    List ls=q.getResultList();
                    Iterator it=ls.iterator();
                    while(it.hasNext()){                        
                        SensorData sd=(SensorData) it.next();                      
                            em.remove(sd);                            
                    }
                    em.getTransaction().commit();
    }
    public void clearSensorNodeData()throws ParseException{
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        String date=dateFormat.format(cal.getTime());        
        Date readingDate=dateFormat.parse(date);
        java.sql.Date readingDt=new java.sql.Date(readingDate.getTime());
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        System.out.println("Hour is :"+hour);
             
                    em=emf.createEntityManager();
                    em.getTransaction().begin();
                    Query q=em.createQuery("SELECT s FROM SensorNodeData s WHERE s.readingHour< :readingHour OR s.data<> :readingDate");
                    q.setParameter("readingHour", hour);
                    q.setParameter("readingDate", readingDt);
                    List ls=q.getResultList();
                    Iterator it=ls.iterator();
                    while(it.hasNext()){                        
                        SensorNodeData sd=(SensorNodeData) it.next();                      
                            em.remove(sd);
                    }
                    em.getTransaction().commit();
    }
    public void clearClusterNodeData()throws ParseException{
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        String date=dateFormat.format(cal.getTime());        
        Date readingDate=dateFormat.parse(date);
        java.sql.Date readingDt=new java.sql.Date(readingDate.getTime());
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        System.out.println("Hour is :"+hour);
             
                    em=emf.createEntityManager();
                    em.getTransaction().begin();
                    Query q=em.createQuery("SELECT c FROM ClusterNodeReading c WHERE c.readingHour< :readingHour OR c.readingDate<> :readingDate");
                    q.setParameter("readingHour", hour);
                    q.setParameter("readingDate", readingDt);
                    List ls=q.getResultList();
                    Iterator it=ls.iterator();
                    while(it.hasNext()){                        
                        ClusterNodeReading sd=(ClusterNodeReading) it.next();                      
                            em.remove(sd);
                    }
                    em.getTransaction().commit();        
    }
    
    
    
    
    
    
    /**
     * Handle Automatic Menu
     * 
     * @author Harsh Bhavsar
     */
    boolean autoStatus = false;
    @FXML
    private void handleAutomaticMenu(ActionEvent event) {  
       
       if(isConnected){    
           Timer timer=new Timer();
            if(autoStatus==false){
                System.out.println("Automatic Menu Selected");
                autoStatus=true;
                automaticTogleButton.setText("Manual");
                automaticTogleButton.setSelected(true);
                Dialogs.showInformationDialog(ukp.getPrimaryStage(),
                                            "Now All the controlling functions will be done by software itself \n"
                                                    + "Press same button to restore it to Manual",
                                            "You have selected the Automatic Controlling of Farm", "Automatic Control");

                
                TimerTask tt=new TimerTask() {

                    boolean valveStatus=false;
                    @Override
                    public void run() {
//                            em=emf.createEntityManager();
//                            em.getTransaction().begin();
                            for(ClusterNode cn:clusterNodeListing){           
                                System.out.println("Inside Check");
                                for(SolenoidValve sv:cn.getSolenoidValveCollection()){
                                           
                                    Double SensorReading = cn.getFinalReading();                                    
                                    if(SensorReading <30 && SensorReading>0)
                                    {   
                                        if(!valveStatus)
                                        {               
                                            String valveId=sv.getValveId();
                                            System.out.println("truning on");
                                                String text="a";//+valveId;
                                                text=text+"\r";   
                                                valveStatus=true;
                                            try {
                                                output.write(text.getBytes());
                                                System.out.println("successfully on the valve"); 
                                                }
                                            catch (IOException ex) {
                                                System.out.println("Unable to on the valve"+ex.getMessage());
                                            }                                    
                                        } 
                                        else{
                                            System.out.println("Already On");
                                        }
                                    }                                
                                    else
                                    {
                                        String text="b";
                                        text=text+"\r";
                                        valveStatus=false;
                                        try {
                                            output.write(text.getBytes());
                                             System.out.println("successfully of the valves");
                                            } catch (IOException ex) {
                                                    ex.getMessage();
                                            }
                                    }
                            }
                                }
                    }
                };
                timer.schedule(tt, 1000,1000*10);
            }
            else{
                autoStatus=false;
                timer.purge();
                System.out.println("Manual Menu Seleceted");                
                automaticTogleButton.setText("Automatic");
                automaticTogleButton.setSelected(false);
                Dialogs.showInformationDialog(ukp.getPrimaryStage(),
                                            "Now All the controlling functions will be done manually by user \n"
                                                    + "Press same button to make it again Automatic",
                                            "You have selected the Manual Controlling of Farm", "Manual Control");
            }
       }
        else{
           automaticTogleButton.setSelected(false);
            Dialogs.showWarningDialog( ukp.getPrimaryStage(),
					"Please Connect the port First",
					"No Port Connected to the Wireless Sensor Network", "No Connection");
            
        }

    }
    

    
    
    
 /**
 * Miscellaneous Methods
 * 
 * @author Harsh Bhavsar
     * @throws java.text.ParseException
 */
    
    public void dateInitialize() throws ParseException{
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        String date=dateFormat.format(cal.getTime());        
        Date readingDate=dateFormat.parse(date);
        java.sql.Date readingDt=new java.sql.Date(readingDate.getTime());
        int hour=cal.get(Calendar.HOUR_OF_DAY);
             
        System.out.println("Today's date is :"+readingDt);
        System.out.println("Current hour is :"+hour);                
    }
    
    @FXML
    private void handleExit(ActionEvent event) {
        exit();   
    }
    public void exit(){
        timerCombo.purge();
      //  timerBarChart.purge();
        //timerSerial.purge();
       // timerTemperature.purge();
        autoData.purge();
        System.exit(0);
    }
    @FXML
    private void aboutTheApp(ActionEvent event) {
        
        Dialogs.showInformationDialog(ukp.getPrimaryStage(), "Developer : Harsh Bhavsar \n An Advanced and Intelligent Farming System for Indian Farmer",
                    "Unnat Krushi Pranali : An Advanced Farming System",
                    "About the Application");        
    }
    /**
     * Auto port combo refresh to 
     * 
     * @author Harsh Bhavsar
     * 
     */
    public void portComboRefresh(){
       timerCombo= new Timer();
		TimerTask tt = new TimerTask(){
                    int count=0;
                        @Override
			public void run(){
                            if(count<1){                                                                                    
                                portCombo.getItems().clear();
                                ports = CommPortIdentifier.getPortIdentifiers();                                      
                                while(ports.hasMoreElements())
                               {                                
                                   CommPortIdentifier curPort=(CommPortIdentifier) ports.nextElement();
                                   if(curPort.getPortType()== CommPortIdentifier.PORT_SERIAL)
                                   {
                                       System.out.println(curPort.getName());
                                       portCombo.getItems().setAll(curPort.getName());                    
                                       count++;
                                       
                                   }
                               }
                                System.out.println("in run");
                            }
                            else{
                            timerCombo.purge();
                        }
                        }
		};
		timerCombo.schedule(tt, 1000, 1000*5);
    }
    
    
    /**
     * Forecasted Weather Tab Menu
     * 
     * 
     * @throws java.text.ParseException
     */    
    public void weatherStatus() throws ParseException{              
        currentStatus();
        forecastedWeather();
    }
    
    public void currentStatus(){
        em=emf.createEntityManager();
        em.getTransaction().begin();
        
        Query q=em.createQuery("SELECT w FROM WeatherStatus w ORDER BY W.id DESC");
        List ls=q.setMaxResults(1).getResultList();
        Iterator it=ls.iterator();
        while(it.hasNext()){  
            
            WeatherStatus ws=(WeatherStatus) it.next();
            temp.setText(String.valueOf(ws.getTemperature())+"° C");
            //moisture.setId(String.valueOf(ws.()));
            humidity.setText(String.valueOf(ws.getHumidity())+" %");
            pressure.setText(String.valueOf(ws.getPressure())+" mb");
            windSpeed.setText(String.valueOf(ws.getWindSpeed())+" kmph");
            windDirection.setText(String.valueOf(ws.getWindDirection()));            
        }
        em.getTransaction().commit();        
    }
    
    public void forecastedWeather() throws ParseException{
         
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
        SimpleDateFormat df=new SimpleDateFormat("dd");
        String date=dateFormat.format(cal.getTime());        
        Date readingDate=dateFormat.parse(date);
        java.sql.Date readingDt=new java.sql.Date(readingDate.getTime());
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tom=cal.getTime();                
        today1Date.setText(df.format(tom));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date dat=cal.getTime();
        today2Date.setText(df.format(dat));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date datt=cal.getTime();
        today3Date.setText(df.format(datt));
        
              
        em=emf.createEntityManager();
        em.getTransaction().begin();
        String next="";
        
        Query q=em.createQuery("SELECT MAX(w.temperature) FROM WeatherStatus w WHERE w.readingDate= :readingDate");
        q.setParameter("readingDate", readingDt);
        Number result = (Number) q.getSingleResult ();
        System.out.println("Max result is :"+result);    
        next=String.valueOf(result);
        next=next+"°/";
        
        Query qmin=em.createQuery("SELECT MIN(w.temperature) FROM WeatherStatus w WHERE w.readingDate= :readingDate");
        qmin.setParameter("readingDate", readingDt);        
        Number result1 = (Number) qmin.getSingleResult ();        
        System.out.println("Min result is :"+result1);
        next=next+String.valueOf(result1)+"°";
        
        em.getTransaction().commit();        
        today1Temp.setText(next);      
        if(hour>20 && hour <6){
            today1Image.setImage(cloudImage);
        }
    }
    public void sumCluster(){
        
//        em=emf.createEntityManager();
//        em.getTransaction().begin();
//
//        Query q=em.createQuery("SELECT SUM(b.salary) FROM BarchartTbl b");
//        Number result = (Number) q.getSingleResult ();   
//        System.out.println("the sum is ::  "+ result);
    }          
    public void timer(){
    Timer timer = new Timer();
		TimerTask tt = new TimerTask(){
                        @Override
			public void run(){
				Calendar cal = Calendar.getInstance(); //this is the method you should use, not the Date(), because it is desperated.
 
				int hour = cal.get(Calendar.HOUR_OF_DAY);//get the hour number of the day, from 0 to 23
 
				if(hour == 14){
					System.out.println("doing the scheduled task");
				}
			}
		};
		timer.schedule(tt, 1000, 1000*5);//	delay the task 1 second, and then run task every five seconds	
    }
    
    
}

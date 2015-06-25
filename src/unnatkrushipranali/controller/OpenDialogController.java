/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unnatkrushipranali.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unnatkrushipranali.UnnatKrushiPranali;

/**
 *
 * @author harshbitss
 */


public class OpenDialogController 
{
    private Stage stage;
    private String title;
    private String path;
    
    
    public boolean showDialog(String title,String path) throws IOException
    {
        
            this.title=title;
            this.path=path;
            
            FXMLLoader loader=new FXMLLoader(UnnatKrushiPranali.class.getResource(path));
            AnchorPane newButtonPage=(AnchorPane) loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene=new Scene(newButtonPage);
            dialogStage.setScene(scene);
            
            
        return false;
    
    }
    
}

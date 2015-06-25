/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unnatkrushipranali;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import unnatkrushipranali.model.ClusterNodeService;
import unnatkrushipranali.model.EntityProxy;
import unnatkrushipranali.model.EntityProxyType;
import unnatkrushipranali.model.SensorListService;
import unnatkrushipranali.model.SensorNodeService;
import unnatkrushipranali.model.SolenoidValveService;


/**
 *
 * @author harshbitss
 * @param <T>
 */
public final class TextFieldTreeCellImpl <T extends EntityProxy> extends TextFieldTreeCell<EntityProxy> {
 
   
    private ContextMenu nodeMenu=new ContextMenu(); 
    private ContextMenu sensorMenu=new ContextMenu();
    private ContextMenu listMenu=new ContextMenu();
    private ContextMenu valveMenu=new ContextMenu();
    private ContextMenu wsnMenu=new ContextMenu();

    public TextFieldTreeCellImpl(ContextMenu nodeMenu,ContextMenu sensorMenu, ContextMenu listMenu, ContextMenu valveMenu,ContextMenu wsnMenu) // throws IOException 
    {
           this.nodeMenu=nodeMenu;
           this.sensorMenu=sensorMenu;
           this.listMenu=listMenu;
           this.valveMenu=valveMenu;
           this.wsnMenu=wsnMenu;
   	}

	public TextFieldTreeCellImpl(ContextMenu nodeMenu,ContextMenu sensorMenu, ContextMenu listMenu,ContextMenu valveMenu, ContextMenu wsnMenu,StringConverter<EntityProxy> sc)
        {
		super(sc);
                this.nodeMenu=nodeMenu;
                this.sensorMenu=sensorMenu;
                this.listMenu=listMenu;
                this.valveMenu=valveMenu;
                this.wsnMenu=wsnMenu;
	}
  
	@Override
	public void updateItem(EntityProxy item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty && !isEditing()) {
			setText(getString());
			setGraphic(getTreeItem().getGraphic());
                        
			if (getItem().getType() == EntityProxyType.CLUSTERNODE) 
                        {
                            setContextMenu(nodeMenu);
			}       
                        else if(getItem().getType() == EntityProxyType.SENSORNODE){
                            setContextMenu(sensorMenu);
                        }
                        else if(getItem().getType() == EntityProxyType.SENSORLIST){
                            setContextMenu(listMenu);
                        }
                        else if(getItem().getType() == EntityProxyType.SOLENOIDVALVE){
                            setContextMenu(valveMenu);
                        }
                        else{
                            setContextMenu(wsnMenu);
                        } 
                            
		}
	}
  
    
    private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
       
    public static Callback<TreeView<EntityProxy>, TreeCell<EntityProxy>> forClusterNodeTreeView(final ContextMenu clusterMenu,final ContextMenu sensornMenu, final ContextMenu sensorlMenu, final ContextMenu valvesMenu,final ContextMenu wsnnMenu, final ClusterNodeService departmentService, final SensorNodeService personService, final SensorListService listService, final SolenoidValveService valveSerive) 
    {
		Callback<TreeView<EntityProxy>, TreeCell<EntityProxy>> callback = new Callback<TreeView<EntityProxy>, TreeCell<EntityProxy>>() {
			@Override
			public TreeCell<EntityProxy> call(TreeView<EntityProxy> p)
                        {
                            
                            
				final TextFieldTreeCellImpl<EntityProxy> treeCell = new TextFieldTreeCellImpl<>(clusterMenu,sensornMenu,sensorlMenu,valvesMenu,wsnnMenu);
				StringConverter<EntityProxy> converter = new StringConverter<EntityProxy>() {
					@Override
					public String toString(EntityProxy t) {
						return t.getLabel();
					}

					@Override
					public EntityProxy fromString(String label) {
						EntityProxy treeNode = treeCell.getTreeView().getSelectionModel().getSelectedItem().getValue();
						treeNode.setLabel(label);
						return treeNode;
					}
				};
				treeCell.setConverter(converter);                               
     return treeCell;
			}
		};

		return callback;
	}
}

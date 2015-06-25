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
import unnatkrushipranali.model.EntityProxy;
import unnatkrushipranali.model.EntityProxyType;
import unnatkrushipranali.model.FarmService;
import unnatkrushipranali.model.FarmerService;
import unnatkrushipranali.model.FieldService;

/**
 *
 * @author harshbitss
 * @param <T>
 */
public final class TextFieldTreeCellImplFarm <T extends EntityProxy> extends TextFieldTreeCell<EntityProxy> {
    
    private ContextMenu farmerMenu=new ContextMenu(); 
    private ContextMenu farmMenu=new ContextMenu();
    private ContextMenu fieldMenu=new ContextMenu();
    private ContextMenu cropMenu=new ContextMenu();
    

     public TextFieldTreeCellImplFarm(ContextMenu farmerMenu,ContextMenu farmMenu, ContextMenu fieldMenu, ContextMenu cropMenu) // throws IOException 
    {
          this.farmerMenu=farmerMenu;
          this.farmMenu=farmMenu;
          this.fieldMenu=fieldMenu;
          this.cropMenu=cropMenu;
          
   	}

	public TextFieldTreeCellImplFarm(ContextMenu farmerMenu,ContextMenu farmMenu, ContextMenu fieldMenu, ContextMenu cropMenu, StringConverter<EntityProxy> sc)
        {
            super(sc);
            this.farmerMenu=farmerMenu;
            this.farmMenu=farmMenu;
            this.fieldMenu=fieldMenu;
            this.cropMenu=cropMenu;
	}
    
    
        
        @Override
	public void updateItem(EntityProxy item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty && !isEditing()) {
			setText(getString());
			setGraphic(getTreeItem().getGraphic());
                        
			if (getItem().getType() == EntityProxyType.FARMER) 
                        {
                            setContextMenu(farmerMenu);
			}       
                        if(getItem().getType() == EntityProxyType.FARM){
                            setContextMenu(farmMenu);
                        }
                        else if(getItem().getType() == EntityProxyType.FIELD){
                            setContextMenu(fieldMenu);
                        }
                        else if(getItem().getType() == EntityProxyType.CROP){
                            setContextMenu(cropMenu);
                        }
                        else{
                            setContextMenu(farmerMenu);
                        }
                        
		}
	}
         private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
         
         
          public static Callback<TreeView<EntityProxy>, TreeCell<EntityProxy>> forFarmTreeView(final ContextMenu farmerMenu,final ContextMenu farmMenu, final ContextMenu fieldMenu, final ContextMenu cropMenu,final FarmerService farmerService, FarmService farmService, FieldService fieldService ) 
    {
		Callback<TreeView<EntityProxy>, TreeCell<EntityProxy>> callback = new Callback<TreeView<EntityProxy>, TreeCell<EntityProxy>>() {
			@Override
			public TreeCell<EntityProxy> call(TreeView<EntityProxy> p)
                        {
                            
                            
				final TextFieldTreeCellImplFarm<EntityProxy> treeCell = new TextFieldTreeCellImplFarm<>(farmerMenu,farmMenu,fieldMenu,cropMenu);
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
    
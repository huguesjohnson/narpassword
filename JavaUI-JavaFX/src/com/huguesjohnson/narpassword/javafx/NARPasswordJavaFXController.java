/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpassword.javafx;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.huguesjohnson.dubbel.fx.ImageUtil;
import com.huguesjohnson.dubbel.util.DateUtil;
import com.huguesjohnson.dubbel.util.StringComparator;
import com.huguesjohnson.narpas.Narpas;
import com.huguesjohnson.narpas.NarpasUtil;
import com.huguesjohnson.narpas.PasswordSetting;
import com.huguesjohnson.narpas.PasswordSettingDateComparator;
import com.huguesjohnson.narpas.PasswordSettingNameComparator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NARPasswordJavaFXController implements Initializable{
	private ResourceBundle bundle;
    private int lastSelectionIndex;
    private boolean isRemoving=false;
    private boolean isAdding=false;
    private boolean isSorting=false;
    private boolean isEditing=false;
    private boolean isFiltered=false;
    private boolean passwordInClipboard=false;
    private long lastClipboardClear=0;
    private long lastPassPhraseClear=0;
	private String savePath;
	public void setSavePath(String savePath){this.savePath=savePath;}
	//sorting
    public enum SortedBy{NAME,DATE};
	private SortedBy sortedBy=SortedBy.NAME;
	private boolean sortedReverse=false;
	//the list of password settings
	ObservableList<PasswordSetting> items=FXCollections.observableArrayList();
	//main window - left pane
    @FXML private ListView<PasswordSetting> passwordList;
    @FXML private Button openButton;
    @FXML private Button saveButton;
    @FXML private Button listAddButton;
    @FXML private Button listRemoveButton;
    @FXML private Button clearPassphraseButton;
    @FXML private Button clearPasswordButton;
    @FXML private Button sortNameButton;
    @FXML private Button sortLastUsedButton;
    //main window - right pane
    @FXML private PasswordField fieldPassPhrase;
    @FXML private TextField fieldPasswordName;
    @FXML private TextField fieldPasswordNotes;
    @FXML private TextField fieldPassword;
    @FXML private TextField fieldPasswordVersion;
    @FXML private TextField fieldLimitSpecialCharacters;
    @FXML private TextField fieldLastUsed;
    @FXML private TextField fieldUUID;
    @FXML private ComboBox<String> fieldPasswordCategory;
    @FXML private ComboBox<String> fieldCategoryFilter;
    @FXML private ComboBox<String> fieldAlgorithmVersion;
    @FXML private Button copyButton;
    @FXML private CheckBox checkLowerCase;
    @FXML private CheckBox checkUpperCase;
    @FXML private CheckBox checkNumbers;
    @FXML private CheckBox checkSpecialCharacters;
    @FXML private CheckBox checkExtendedCharacters;
    @FXML private CheckBox checkDuplicateCharacters;
    @FXML private CheckBox checkLimitSpecialCharacters;
    @FXML private CheckBox checkClearClipboard;
    @FXML private Slider sliderPasswordLength;
    @FXML private Button editPasswordButton;
    @FXML private Button savePasswordButton;
    @FXML private Button addPasswordButton;
    @FXML private Button undoPasswordButton;
    @FXML private TextField fieldClearClipboardSeconds;
    @FXML private CheckBox checkClearPassphrase;
    @FXML private TextField fieldClearPassphraseSeconds;
    
	@Override
	public void initialize(URL url,ResourceBundle bundle){
		this.bundle=bundle;
		//of all the workarounds you are forced to do in JavaFX, this might be the worst
		this.fieldPasswordCategory.setMaxWidth(Double.MAX_VALUE);
		this.fieldCategoryFilter.setMaxWidth(Double.MAX_VALUE);
        //not sure how to do this in mapping in fxml
        this.passwordList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PasswordSetting>(){
            @Override
            public void changed(ObservableValue<? extends PasswordSetting> observable,PasswordSetting oldValue,PasswordSetting newValue){
            	passwordListSelectionChanged(observable,oldValue,newValue);
            }
        });
        //also not sure how to do this in mapping in fxml
        this.sliderPasswordLength.valueProperty().addListener(new ChangeListener<Number>(){
            public void changed(ObservableValue<? extends Number> ov,Number old_val,Number new_val){
                sliderPasswordLength.setTooltip(new Tooltip(new_val.toString()));
                generatePassword();
            }
        });
        //also not sure how to do this in fxml
        this.fieldAlgorithmVersion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldValue,String newValue){
            	onAlgorithmVersionChanged();
            }
          });   
        this.fieldCategoryFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldValue,String newValue){
            	onCategoryFilterChanged();
            }
          });
		//setup password list
        this.passwordList.setItems(this.items);
        this.onAdd(null);
        this.lastSelectionIndex=0;
        this.updateFormFields(this.passwordList.getSelectionModel().getSelectedItem());
        //setup timer - maybe this can also be done in fxml
        Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>(){
        	@Override public void handle(ActionEvent e){onTimer();};
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        //setup category filter
		this.fieldCategoryFilter.getItems().add(NarpasUtil.DEFAULT_NO_CATEGORY);
		this.fieldCategoryFilter.getItems().add(NarpasUtil.DEFAULT_ALL_CATEGORY);
        //disable fields by default
        this.enableDisableFields(false);
        //fix images if they didn't load correctly - this is a CHRONIC problem that I could rant about at great lengths
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.listAddButton,"add.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.openButton,"open.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.saveButton,"save.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.listRemoveButton,"remove.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.clearPassphraseButton,"clear.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.clearPasswordButton,"clear.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.copyButton,"copy.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.editPasswordButton,"edit.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.savePasswordButton,"save.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.addPasswordButton,"add.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.undoPasswordButton,"undo.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.sortLastUsedButton,"sortlastused.png",NARPasswordJavaFXController.class);
        ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.sortNameButton,"sortname.png",NARPasswordJavaFXController.class);
	}
	
	private void setOrAddItem(PasswordSetting setting){
		int index=this.indexOfItemByUUID(setting.getUuid());
		if(index>=0){
			this.items.set(index,setting);
		}else{
			this.items.add(setting);
		}
	}
	
	private int indexOfItemByUUID(String uuid){
		int max=this.items.size();
		for(int index=0;index<max;index++){
			if(uuid.equals(this.items.get(index).getUuid())){
				return(index);
			}
		}
		return(-1);		
	}
	
	private int indexOfItemByName(String name){
		int max=this.items.size();
		for(int index=0;index<max;index++){
			if(name.equals(this.items.get(index).getPasswordName())){
				return(index);
			}
		}
		return(-1);
	}
	
	//event for the open button on the password list
    @FXML
    private void onOpen(ActionEvent event){
    	this.showSaveLoadDialog(NARPasswordJavaFXSaveLoadController.SaveDialogMode.LOAD);
    }

	//event for the save button on the password list
    @FXML
    private void onSave(ActionEvent event){
    	this.showSaveLoadDialog(NARPasswordJavaFXSaveLoadController.SaveDialogMode.SAVE);
    }
    
    public void showSaveLoadDialog(NARPasswordJavaFXSaveLoadController.SaveDialogMode mode){
    	boolean save=(mode==NARPasswordJavaFXSaveLoadController.SaveDialogMode.SAVE);
    	try{
    		Stage dialog=new Stage();
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("SaveLoadDialog.fxml"),this.bundle);
    		Scene scene=new Scene(loader.load());        
    		NARPasswordJavaFXSaveLoadController controller=(NARPasswordJavaFXSaveLoadController)loader.getController();
    		controller.setSavePath(this.savePath);
    		dialog.setScene(scene);
    		dialog.getIcons().add(new Image(NARPasswordJavaFX.class.getResourceAsStream("narpas-icon-16.png")));
    		dialog.setResizable(true);
    		dialog.setScene(scene);
    		dialog.initModality(Modality.APPLICATION_MODAL);
    		if(save){
    			dialog.setTitle(this.bundle.getString("save_title"));
    			controller.setPasswordSettingList(this.items);
    		}else{
    			dialog.setTitle(this.bundle.getString("load_title"));
    		}
    		controller.setMode(mode);
    		dialog.showAndWait();
    		//test if a password list was loaded
    		if(!save&&!controller.getCancel()){
    			List<PasswordSetting> list=controller.getPasswordSettingList();
    			this.items.clear();
    			this.items.setAll(NarpasUtil.prepV2Migrate(list));
    			//add categories
    			List<String> categories=NarpasUtil.getAllCategories(list);
    			this.fieldPasswordCategory.getItems().clear();
    			this.fieldPasswordCategory.getItems().addAll(categories);
    			this.fieldCategoryFilter.getItems().clear();
    			this.fieldCategoryFilter.getItems().addAll(categories);
    			if(!this.fieldCategoryFilter.getItems().contains(NarpasUtil.DEFAULT_NO_CATEGORY)){
    				this.fieldCategoryFilter.getItems().add(NarpasUtil.DEFAULT_NO_CATEGORY);
    			}
    			if(!this.fieldCategoryFilter.getItems().contains(NarpasUtil.DEFAULT_ALL_CATEGORY)){
    				this.fieldCategoryFilter.getItems().add(NarpasUtil.DEFAULT_ALL_CATEGORY);
    			}
    			this.fieldCategoryFilter.getItems().sort(new StringComparator());
    			//select the first item
    			passwordList.getSelectionModel().select(0);
    			passwordList.getFocusModel().focus(0);
    			this.lastSelectionIndex=0;
    			updateFormFields(passwordList.getItems().get(0));
    			this.savePath=controller.getSavePath();
    		}else if(save&&!controller.getCancel()){
    			this.savePath=controller.getSavePath();
    		}
    	}catch(Exception x){
    		this.showErrorAlert(x);
    	}    	
    }

	//event for the add button on the password list
    @FXML
    private void onAdd(ActionEvent event){
    	long l=0;//if this seems like overkill it is
    	String passwordName="[new password "+l+"]";
    	while(this.indexOfItemByName(passwordName)>=0){
    		l++;
        	passwordName="[new password "+l+"]";
    	}
    	PasswordSetting newPassword=new PasswordSetting(passwordName,true,true,true,true,32,"");
    	newPassword.setAlgorithmVersion(2);
    	if(this.isFiltered){
    		newPassword.setCategory(this.fieldCategoryFilter.getValue());
    	}else{
    		newPassword.setCategory(NarpasUtil.DEFAULT_NO_CATEGORY);
    	}
    	this.setOrAddItem(newPassword);
    	this.passwordList.getSelectionModel().select(this.items.size()-1);
    	this.listRemoveButton.setDisable(false);
    	this.onEditPasswordSettings(event);
    }

	//event for the remove button on the password list
    @FXML
    private void onRemove(ActionEvent event){
    	//sanity checks
    	if(this.items.size()<1){
        	this.listRemoveButton.setDisable(true);
    		return;
    	}
    	int currentSelection=this.passwordList.getSelectionModel().getSelectedIndex();
    	if(currentSelection<0){
        	this.listRemoveButton.setDisable(true);
    		return;
    	}
    	this.isRemoving=true;
    	int removeIndex=this.passwordList.getSelectionModel().getSelectedIndex();
    	this.items.remove(removeIndex);
    	this.isRemoving=false;
    	int newSize=this.items.size();
    	if(newSize<1){//test if there are zero items in the list now
    		this.listRemoveButton.setDisable(true);
    		this.lastSelectionIndex=-1;
    	}else if(removeIndex==0){//if the 0th item is removed the UI needs to be forcefully updated
    		this.updateFormFields(this.passwordList.getItems().get(0));
    	}
    }

	//event for the add button on the password list
    @FXML
    private void onSortName(ActionEvent event){
    	this.isSorting=true;
    	boolean reverse=false;
    	if(this.sortedBy==SortedBy.NAME){
    		reverse=!this.sortedReverse;
    	}
    	this.items.sort(new PasswordSettingNameComparator(reverse));
    	this.sortedBy=SortedBy.NAME;
    	this.sortedReverse=reverse;
    	this.isSorting=false;
    	//selected index usually changes after sorting
    	this.lastSelectionIndex=this.passwordList.getSelectionModel().getSelectedIndex();
    }
    
	//event for the add button on the password list
    @FXML
    private void onSortLastUsed(ActionEvent event){
    	this.isSorting=true;
    	boolean reverse=false;
    	if(this.sortedBy==SortedBy.DATE){
    		reverse=!this.sortedReverse;
    	}    	
    	this.items.sort(new PasswordSettingDateComparator(reverse));
    	this.sortedBy=SortedBy.DATE;
    	this.sortedReverse=reverse;
    	this.isSorting=false;
    	//selected index usually changes after sorting
    	this.lastSelectionIndex=this.passwordList.getSelectionModel().getSelectedIndex();
    }
    
    //event for the edit button in the password settings section
    @FXML
    private void onEditPasswordSettings(ActionEvent event){
    	this.enableDisableFields(true);
    	this.checkAddNew();
    }

    //event for the save button in the password settings section
    @FXML
    private void onSavePasswordSettings(ActionEvent event){
    	this.updateSelectedListItem();
    	this.enableDisableFields(false);
    }
    
    //event for the add button in the password settings section
    @FXML
    private void onAddPasswordSettings(ActionEvent event){
    	this.isAdding=true;
    	this.items.add(this.getPasswordSettings());
    	this.passwordList.getSelectionModel().select(this.items.size()-1);
    	this.enableDisableFields(false);
    	this.isAdding=false;
    }
    
    //event for the undo button in the password settings section
    @FXML
    private void onCancelPasswordSettings(ActionEvent event){
    	this.updateFormFields(this.passwordList.getSelectionModel().getSelectedItem());
    	this.enableDisableFields(false);
    }
    
    //event for copy password to clipboard button
    @FXML
    private void onCopy(ActionEvent event){
    	final Clipboard clipboard=Clipboard.getSystemClipboard();
        final ClipboardContent content=new ClipboardContent();
        content.putString(fieldPassword.getText());
        clipboard.setContent(content);
        this.passwordInClipboard=true;
		this.lastClipboardClear=0;//reset the counter
		//update last used value for the selected password
		long ms=System.currentTimeMillis();
    	this.fieldLastUsed.setText(DateUtil.toString(ms,DateUtil.DF_YearMonthDayHourMinuteSecond));
    	int index=this.indexOfItemByUUID(this.fieldUUID.getText());
    	if(index>=0){
    		this.items.get(index).setLastUsed(ms);
    	}
    }
    
    //event for the clear passphrase button
    @FXML
    private void onClearPassphrase(ActionEvent event){
		this.lastPassPhraseClear=0;
		this.fieldPassPhrase.setText("");
		this.generatePassword();
    }

    //event for the clear password from clipboard button
    @FXML
    private void onClearPassword(ActionEvent event){
		if(this.passwordInClipboard){
			this.lastClipboardClear=0;
	        final Clipboard clipboard=Clipboard.getSystemClipboard();
	        final ClipboardContent content=new ClipboardContent();
	        content.putString("");
	        clipboard.setContent(content);
	        this.passwordInClipboard=false;
        }
    }

    //event that fires when a key is pressed in the password version textfield
    @FXML
    private void onPasswordVersionChange(KeyEvent event){
    	this.generatePassword();
    }    
    
    //event that fires when a key is pressed in the passphrase textfield
    @FXML
    private void onPassphraseChange(KeyEvent event){
    	this.generatePassword();
    }

    //event that fires when a key is pressed in the limit special characters textfield
    @FXML
    private void onPasswordLimitSpecialCharactersChange(KeyEvent event){
    	this.generatePassword();
    }
    
    //event that fires when a key is pressed in the password name textfield
    @FXML
    private void onPasswordNameChange(KeyEvent event){
    	this.generatePassword();
    	this.checkAddNew();
    }
    
    //separate event for this checkbox only because it enables/disables another field
    @FXML
    private void onCheckSpecialCharacters(ActionEvent event){
    	int index=this.fieldAlgorithmVersion.getSelectionModel().getSelectedIndex();
    	if(index>0){
    		boolean selected=this.checkSpecialCharacters.isSelected();
    		this.checkLimitSpecialCharacters.setDisable(!selected);
    		if(!selected){
        		this.checkLimitSpecialCharacters.setSelected(false);
    		}
    	}else{
    		this.checkLimitSpecialCharacters.setSelected(false);
    		this.checkLimitSpecialCharacters.setDisable(true);
    	}
    	//default event
    	this.onPasswordSettingsCheckbox(event);
    }
    
    //event that fires when any of the "Use xxxx" checkboxes are checked/unchecked
    @FXML
    private void onPasswordSettingsCheckbox(ActionEvent event){
    	this.generatePassword();
    	this.checkAddNew();
    }
    
    //event that fires when a key is pressed in the password notes textfield
    @FXML
    private void onPasswordNotesChange(KeyEvent event){
    	this.checkAddNew();
    }
    
    //event that fires when a key is pressed in the password category textfield
    @FXML
    private void onPasswordCategoryChange(KeyEvent event){
    	this.checkAddNew();
    }    
    
    //event that fires when the category filter is changed
    @FXML
    private void onCategoryFilterChanged(){
    	String filterCategory=this.fieldCategoryFilter.getSelectionModel().getSelectedItem();
    	if(filterCategory.equals(NarpasUtil.DEFAULT_ALL_CATEGORY)){
    		this.passwordList.setItems(this.items);
    		this.isFiltered=false;
    	}else{
    		FilteredList<PasswordSetting> fl=this.items.filtered(f->filterCategory.equals(f.getCategory()));
    		this.passwordList.setItems(fl);
    		this.isFiltered=true;
    	}
    }     
    
    //event that fires when a key is pressed in the passphrase clear time textfield
    @FXML
    private void onClearPassphraseTimeChange(KeyEvent event){
    	this.lastClipboardClear=0;
        String newText=this.fieldClearPassphraseSeconds.getText();
        String currentStyle=this.fieldClearPassphraseSeconds.getStyle();
        try{
        	int value=Integer.parseInt(newText);
        	if(value<0){
        		throw(new Exception(""));
        	}
        	if(currentStyle.contains(" -fx-background-color: red;")){
        		this.fieldClearPassphraseSeconds.setStyle(currentStyle.replace(" -fx-background-color: red;",""));
        	}        
        }catch(Exception x){
        	this.fieldClearPassphraseSeconds.setStyle(currentStyle+" -fx-background-color: red;");
        }
    }
    
    //event that fires when a key is pressed in the password clear time textfield
    @FXML
    private void onClearPasswordTimeChange(KeyEvent event){
    	this.lastClipboardClear=0;
        String newText=this.fieldClearClipboardSeconds.getText();
        String currentStyle=this.fieldClearClipboardSeconds.getStyle();
        try{
        	int value=Integer.parseInt(newText);
        	if(value<0){
        		throw(new Exception(""));
        	}
        	if(currentStyle.contains(" -fx-background-color: red;")){
        		this.fieldClearClipboardSeconds.setStyle(currentStyle.replace(" -fx-background-color: red;",""));
        	}
        }catch(Exception x){
        	this.fieldClearClipboardSeconds.setStyle(currentStyle+" -fx-background-color: red;");
        }    	
    }
    
    //event that fires when the clear passphrase checkbox is checked/unchecked
    @FXML
    private void onClearPassphraseCheck(ActionEvent event){
    	this.lastPassPhraseClear=0;
    }

    //event that fires when the clear password checkbox is checked/unchecked
    @FXML
    private void onClearPasswordCheck(ActionEvent event){
    	this.lastClipboardClear=0;
    }
    
    //called when the algorithm version combobox changes
    private void onAlgorithmVersionChanged(){
    	boolean disable=true;
    	int index=this.fieldAlgorithmVersion.getSelectionModel().getSelectedIndex();
    	if(index>0){disable=false;}
        this.checkExtendedCharacters.setDisable(disable);
        this.checkLimitSpecialCharacters.setDisable(disable);
        this.checkDuplicateCharacters.setDisable(disable);
    	this.fieldPasswordVersion.setDisable(disable);
    	this.fieldLimitSpecialCharacters.setDisable(disable);
    	if(disable){//v1 algorithm
            this.checkExtendedCharacters.setSelected(false);
            this.checkLimitSpecialCharacters.setSelected(false);
            this.checkDuplicateCharacters.setSelected(false);
            this.fieldPasswordVersion.setText("");
            this.fieldLimitSpecialCharacters.setText("");
        	this.sliderPasswordLength.setBlockIncrement(8d);
        	this.sliderPasswordLength.setMajorTickUnit(8d);
        	this.sliderPasswordLength.setSnapToTicks(true);
            //check if password length has to be downgraded now too
            int passwordLength=(int)this.sliderPasswordLength.getValue();
            if((passwordLength%8)!=0){
        		this.sliderPasswordLength.setValue(Math.round(passwordLength/8.0)*8);
        	}            
    	}else{//v2 algorithm
    		this.sliderPasswordLength.setBlockIncrement(1d);
    		this.sliderPasswordLength.setMajorTickUnit(8d);
    		this.sliderPasswordLength.setSnapToTicks(false);
    	}
    	this.generatePassword();
    }

    //called by the timer
    private void onTimer(){
		if(this.passwordInClipboard&&this.checkClearClipboard.isSelected()){
			try{
				int value=Integer.parseInt(this.fieldClearClipboardSeconds.getText());
				if(this.lastClipboardClear>value){
					this.onClearPassword(null);
				}else{
					this.lastClipboardClear++;
				}
			}catch(Exception x){/* do nothing */}
		}
		if(this.checkClearPassphrase.isSelected()){
			try{
				int value=Integer.parseInt(this.fieldClearPassphraseSeconds.getText());
				if(this.lastPassPhraseClear>value){
					this.onClearPassphrase(null);
				}else{
					this.lastPassPhraseClear++;
				}
			}catch(Exception x){/* do nothing */}
		}    	
    }

    //handle selection changes in the password list
    private void passwordListSelectionChanged(ObservableValue<? extends PasswordSetting> observable,PasswordSetting oldValue,PasswordSetting newValue){
    	/*
    	 * Despite my best efforts, sometimes the listview will lose the selection.
    	 * I have gone to some lengths to prevent this.
    	 * It pretty much always happens if the listview has a single item.
    	 */
    	if(newValue==null){
    		this.passwordList.getSelectionModel().select(0);
    		return;
    	}
    	if(this.isSorting){return;}
    	int currentSelectionIndex=this.passwordList.getSelectionModel().getSelectedIndex();
        if(currentSelectionIndex==this.lastSelectionIndex){
        	return;                	
        }
        //check for unsaved changes
        if((this.lastSelectionIndex>=0)&&(this.lastSelectionIndex<this.items.size())&&((this.isEditing))){
            PasswordSetting psSelected=this.items.get(this.lastSelectionIndex);
            if((psSelected!=null)&&(!this.isAdding)&&(!this.isRemoving)){
                PasswordSetting psNew=this.getPasswordSettings();
                if(!psSelected.compare(psNew)){
                	//password has changed - prompt to save
               		Alert a=new Alert(
               				AlertType.CONFIRMATION,
               				this.bundle.getString("label_savechanges")+psNew.getPasswordName()+"?",
               				ButtonType.NO,
               				ButtonType.YES);
               		a.setTitle(this.bundle.getString("title_save_prompt"));
               		Optional<ButtonType> result=a.showAndWait();
                	//<sarcasm>using Optional.isPresent() is so much better than checking if result!=null</sarcasm>
               		if(result.isPresent()){
               			if(result.get().equals(ButtonType.YES)){
                          	 /* Seriously, please give me a single logical argument why this Optional dance is better than:
                         	  if((result!=null)&&(result.equals(ButtonType.YES)){
                         	    do stuff
                         	  }
                         	  * This is literally more efficient and 100x easier to read
                         	  * This is a totally unnecessary use of Optional to try and look cool in front of the functional programming people
                         	  * */
               				this.items.set(this.lastSelectionIndex,psNew);
               			}
               		}
               	 }
            }
        }
        //update stuff and deal with annoying lose focus behavior
        if(currentSelectionIndex>-1){
        	this.lastSelectionIndex=currentSelectionIndex;
        }else{
        	//this is to deal with the selected index changing when focus is lost
        	if(this.items.size()<0){
        		this.lastSelectionIndex=-1;
            	this.listRemoveButton.setDisable(true);
        	}           	
        }
        //now update the form fields
        this.updateFormFields(newValue);
        this.enableDisableFields(false);
    	this.listRemoveButton.setDisable(false);
    }

    //update all the password setting fields in the right pane
    private void updateFormFields(PasswordSetting setting){
    	if(setting!=null){
	    	//text fields
    		this.fieldPasswordName.setText(setting.getPasswordName());
	    	this.fieldPasswordNotes.setText(setting.getPasswordNotes());
	    	this.fieldPasswordVersion.setText(setting.getPasswordVersion());
	    	this.fieldLastUsed.setText(DateUtil.toString(setting.getLastUsed(),DateUtil.DF_YearMonthDayHourMinuteSecond));
	    	this.fieldUUID.setText(setting.getUuid());
	    	//password category
	    	String category=setting.getCategory();
	    	if((category==null)||(category.length()<1)){category=NarpasUtil.DEFAULT_NO_CATEGORY;}
	    	int index=this.fieldPasswordCategory.getItems().indexOf(category);
	    	if(index<0){//this shouldn't ever happen but probably will
	    		this.fieldPasswordCategory.getItems().add(category);
		    	index=this.fieldPasswordCategory.getItems().indexOf(category);
	    	}
    		this.fieldPasswordCategory.getSelectionModel().select(index);
	    	//checkboxes
	    	this.checkLowerCase.setSelected(setting.isOptionUseLCase());
	        this.checkUpperCase.setSelected(setting.isOptionUseUCase());
	        this.checkNumbers.setSelected(setting.isOptionUseNumbers());
	        this.checkExtendedCharacters.setSelected(setting.isOptionUseExtChars());
	        this.checkDuplicateCharacters.setSelected(setting.isOptionAllowDuplicateCharacters());
	        //special chararcters
	        boolean useSChars=setting.isOptionUseSChars();
	        this.checkSpecialCharacters.setSelected(useSChars);
	        char[] limitSpecialCharacters=setting.getLimitSpecialChars();
	        if((limitSpecialCharacters==null)||(limitSpecialCharacters.length<1)){
	        	this.fieldLimitSpecialCharacters.setText(new String(Narpas.Constants.schars));
	        	this.checkLimitSpecialCharacters.setSelected(false);
	        }else{
	        	this.fieldLimitSpecialCharacters.setText(new String(limitSpecialCharacters));
	        	this.checkLimitSpecialCharacters.setSelected(true);
	        }
	        //algorithm version
	        int algorithmVersion=setting.getAlgorithmVersion();
	        if(algorithmVersion<1){
	        	algorithmVersion=1;
	        }else if(algorithmVersion>Narpas.Constants.CURRENT_ALGORITHM_VERSION){
	        	algorithmVersion=Narpas.Constants.CURRENT_ALGORITHM_VERSION;
	        }
	        this.fieldAlgorithmVersion.getSelectionModel().select(algorithmVersion-1);
	        //password slider
	        this.sliderPasswordLength.setValue(setting.getPasswordLength());
	        //now generate the password
	        this.generatePassword();
	    }
    }
    
    //save PasswordSetting behind the selected item in the password list
    private void updateSelectedListItem(){
    	if(isRemoving){return;}
    	int size=this.items.size();
    	if((this.lastSelectionIndex>-1)&&(size>0)&&(this.lastSelectionIndex<size)){
    		PasswordSetting setting=new PasswordSetting();
    		long ms=System.currentTimeMillis();
    		setting.setLastUsed(ms);
	    	this.fieldLastUsed.setText(DateUtil.toString(ms,DateUtil.DF_YearMonthDayHourMinuteSecond));
	    	//text fields
    		setting.setPasswordName(this.fieldPasswordName.getText());
    		setting.setPasswordNotes(this.fieldPasswordNotes.getText());
    		setting.setPasswordVersion(this.fieldPasswordVersion.getText());
    		setting.setUuid(this.fieldUUID.getText());
	    	//password category
    		String category=this.fieldPasswordCategory.getSelectionModel().getSelectedItem();
    		setting.setCategory(category);
    		if(!this.fieldCategoryFilter.getItems().contains(category)){
    			this.fieldCategoryFilter.getItems().add(category);
    		}
    		if(!this.fieldPasswordCategory.getItems().contains(category)){
    			this.fieldPasswordCategory.getItems().add(category);
    		}
	    	//checkboxes
    		setting.setOptionAllowDuplicateCharacters(this.checkDuplicateCharacters.isSelected());
    		setting.setOptionUseExtChars(this.checkExtendedCharacters.isSelected());
    		setting.setOptionUseLCase(this.checkLowerCase.isSelected());
    		setting.setOptionUseUCase(this.checkUpperCase.isSelected());
    		setting.setOptionUseNumbers(this.checkNumbers.isSelected());
	        //special characters
	        boolean useSChars=this.checkSpecialCharacters.isSelected();
	        setting.setOptionUseSChars(useSChars);
	        if(useSChars){
	        	String limitSpecialChars=this.fieldLimitSpecialCharacters.getText();
	        	if((this.checkLimitSpecialCharacters.isSelected())&&(limitSpecialChars!=null)&&(limitSpecialChars.length()>0)){
	        		setting.setLimitSpecialChars(limitSpecialChars.toCharArray());
	        	}
	        }
	        //algorithm version
	        setting.setAlgorithmVersion(this.fieldAlgorithmVersion.getSelectionModel().getSelectedIndex()+1);
	        //password length
	        setting.setPasswordLength((int)this.sliderPasswordLength.getValue());
	        //now save the updated password
	        this.setOrAddItem(setting);
    	}
    }    
    
    //update the password
    private void generatePassword(){
        String passPhrase=this.fieldPassPhrase.getText();
        PasswordSetting settings=this.getPasswordSettings();
        //this was always a bad idea for password length, fixed in v2
        if(settings.getAlgorithmVersion()<=1){
        	settings.setPasswordLength(settings.getPasswordLength()/8);
        }
        String password="";
        if(Narpas.validate(passPhrase,settings)){
        	try{
                password=Narpas.generatePassword(passPhrase,settings);
                this.copyButton.setDisable(false);
        	}catch(Exception x){
        		this.showErrorAlert(x);
                this.copyButton.setDisable(true);
        	}
        }else{
            this.copyButton.setDisable(true);
        }
        this.fieldPassword.setText(password);
    }
    
    //check if the add new password button should be enabled
    private void checkAddNew(){
    	PasswordSetting setting=this.getPasswordSettings();
    	if(this.passwordList.getSelectionModel().getSelectedItem().equals(setting)){
    		this.addPasswordButton.setDisable(true);
    		this.savePasswordButton.setDisable(false);
    	}else if(this.items.contains(setting)){
    		this.addPasswordButton.setDisable(true);
    		this.savePasswordButton.setDisable(true);
    	}else{
    		this.addPasswordButton.setDisable(false);
    		this.savePasswordButton.setDisable(false);
    	}
    }
    
    //enable or disable password setting fields
    private void enableDisableFields(boolean enabled){
        this.fieldPasswordVersion.setDisable(!enabled);
        this.fieldPasswordName.setDisable(!enabled);
        this.checkLowerCase.setDisable(!enabled);
        this.checkUpperCase.setDisable(!enabled);
        this.checkNumbers.setDisable(!enabled);
        this.checkSpecialCharacters.setDisable(!enabled);
        this.checkLimitSpecialCharacters.setDisable(!enabled);
        this.fieldLimitSpecialCharacters.setDisable(!enabled);
        this.checkDuplicateCharacters.setDisable(!enabled);
        this.sliderPasswordLength.setDisable(!enabled);
        this.fieldPasswordNotes.setDisable(!enabled);
        this.editPasswordButton.setDisable(enabled);
        this.savePasswordButton.setDisable(!enabled);
        this.addPasswordButton.setDisable(!enabled);
        this.undoPasswordButton.setDisable(!enabled);
        this.fieldAlgorithmVersion.setDisable(!enabled);
        this.fieldPasswordCategory.setDisable(!enabled);
        if(enabled){//for new v2 fields
        	this.onAlgorithmVersionChanged();
        }else{
            this.checkExtendedCharacters.setDisable(true);
        	this.fieldPasswordVersion.setDisable(true);
        }
        this.isEditing=enabled;
    }
    
    //get the current password settings
    private PasswordSetting getPasswordSettings(){
    	PasswordSetting settings=new PasswordSetting();
    	int algorithmVersion=this.fieldAlgorithmVersion.getSelectionModel().getSelectedIndex()+1;
    	settings.setAlgorithmVersion(algorithmVersion);
    	settings.setCategory(this.fieldPasswordCategory.getSelectionModel().getSelectedItem());
    	settings.setLastUsed(this.passwordList.getSelectionModel().getSelectedItem().getLastUsed());
    	settings.setOptionUseLCase(this.checkLowerCase.isSelected());
    	settings.setOptionUseNumbers(this.checkNumbers.isSelected());
    	boolean useSChars=this.checkSpecialCharacters.isSelected();
    	settings.setOptionUseSChars(useSChars);
    	settings.setOptionUseUCase(this.checkUpperCase.isSelected());
    	settings.setPasswordLength((int)this.sliderPasswordLength.getValue());
    	settings.setPasswordName(this.fieldPasswordName.getText());
    	settings.setPasswordNotes(this.fieldPasswordNotes.getText());
    	//fields added to v2
    	if(algorithmVersion>1){
        	settings.setPasswordVersion(this.fieldPasswordVersion.getText());
        	settings.setOptionAllowDuplicateCharacters(this.checkDuplicateCharacters.isSelected());
        	settings.setOptionUseExtChars(this.checkExtendedCharacters.isSelected());
    		if(useSChars){
    			if(this.checkLimitSpecialCharacters.isSelected()){
    				String limitSpecialChars=this.fieldLimitSpecialCharacters.getText();
    				if((limitSpecialChars==null)||(limitSpecialChars.length()<1)){
    					settings.setLimitSpecialChars(null);
    				}else{
    					settings.setLimitSpecialChars(limitSpecialChars.toCharArray());
    				}
    			}else{
    				settings.setLimitSpecialChars(null);
    			}
    		}
    	}
    	return(settings);
    }
    
    //show an error alert
    private void showErrorAlert(Exception x){
        Alert alert=new Alert(AlertType.ERROR);
        alert.setTitle(x.getClass().getName());
        alert.setHeaderText(x.getMessage());
        alert.setResizable(true);
        StringBuilder stack=new StringBuilder();
        StackTraceElement[] st=x.getStackTrace();
        int maxLine=10;
        if(st.length<maxLine){maxLine=st.length-1;}
        for(int i=0;i<maxLine;i++){
        	stack.append(st[i].getFileName());
        	stack.append(" - ");
        	stack.append(st[i].getMethodName());
        	stack.append("[");
        	stack.append(st[i].getLineNumber());
        	stack.append("]\n");
        }
        stack.append("[.. ");
        stack.append(maxLine-st.length-1);
        stack.append(" more lines ..]");
        alert.setContentText(stack.toString());
        alert.showAndWait();
    }
    
}
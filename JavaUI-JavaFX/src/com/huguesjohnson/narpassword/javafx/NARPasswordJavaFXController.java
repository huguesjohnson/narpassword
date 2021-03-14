/*
NARPassword for Java - Application to generate a non-random password
Copyright (C) 2011-2021 Hugues Johnson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.huguesjohnson.narpassword.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import com.huguesjohnson.narpas.Narpas;
import com.huguesjohnson.narpas.PasswordSetting;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
	private boolean passwordChanged=false;
    private int lastSelectionIndex;
    private boolean isRemoving=false;
    private boolean passwordInClipboard=false;
    private long lastClipboardClear=0;
    private long lastPassPhraseClear=0;
	private String savePath;
	public void setSavePath(String savePath){this.savePath=savePath;}
    //main window - left pane
    @FXML private ListView<PasswordSetting> passwordList;
    @FXML private Button openButton;
    @FXML private Button saveButton;
    @FXML private Button listAddButton;
    @FXML private Button listRemoveButton;
    @FXML private Button clearPassphraseButton;
    @FXML private Button clearPasswordButton;
    //main window - right pane
    @FXML private PasswordField fieldPassPhrase;
    @FXML private TextField fieldPasswordName;
    @FXML private TextField fieldPasswordNotes;
    @FXML private TextField fieldPassword;
    @FXML private Button copyButton;
    @FXML private CheckBox checkLowerCase;
    @FXML private CheckBox checkUpperCase;
    @FXML private CheckBox checkNumbers;
    @FXML private CheckBox checkSpecialCharacters;
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
		//setup password list
        PasswordSetting defaultPassword=new PasswordSetting("<new password>",true,true,true,true,32,"");
        this.passwordList.setEditable(false);
        ObservableList<PasswordSetting> items=FXCollections.observableArrayList(defaultPassword);
        this.passwordList.setItems(items);
        //not sure how to do this in mapping in fxml
        this.passwordList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PasswordSetting>(){
            @Override
            public void changed(ObservableValue<? extends PasswordSetting> observable,PasswordSetting oldValue,PasswordSetting newValue){
            	passwordListSelectionChanged(observable,oldValue,newValue);
            }
        });
        this.passwordList.getSelectionModel().select(0);
        this.lastSelectionIndex=0;
        //also not sure how to do this in mapping in fxml
        this.sliderPasswordLength.valueProperty().addListener(new ChangeListener<Number>(){
            public void changed(ObservableValue<? extends Number> ov,Number old_val,Number new_val){
            	passwordChanged=true;
                sliderPasswordLength.setTooltip(new Tooltip(new_val.toString()));
                generatePassword();
            }
        });
        this.updateFormFields(this.passwordList.getSelectionModel().getSelectedItem());
        //setup timer - maybe this can also be done in fxml
        Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>(){
        	@Override public void handle(ActionEvent e){onTimer();};
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        //disable fields by default
        this.enableDisableFields(false);
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
    
    private void showSaveLoadDialog(NARPasswordJavaFXSaveLoadController.SaveDialogMode mode){
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
    			controller.setPasswordSettingList(this.passwordList.getItems());
    		}else{
    			dialog.setTitle(this.bundle.getString("load_title"));
    		}
    		controller.setMode(mode);
    		dialog.showAndWait();
    		//test if a password list was loaded
    		if(!save&&!controller.getCancel()){
    			passwordList.getItems().clear();
    			passwordList.getItems().addAll(controller.getPasswordSettingList());
    			passwordList.getSelectionModel().select(0);
    			passwordList.getFocusModel().focus(0);
    			updateFormFields(passwordList.getItems().get(0));
    		}
    	}catch(Exception x){
    		this.showErrorAlert(x);
    	}    	
    }

	//event for the add button on the password list
    @FXML
    private void onAdd(ActionEvent event){
    	long l=0;//if this seems like overkill it is
    	PasswordSetting newPassword=new PasswordSetting("<new password "+l+">",true,true,true,true,32,"");
    	while(this.passwordList.getItems().contains(newPassword)){
    		l++;
        	newPassword=new PasswordSetting("<new password "+l+">",true,true,true,true,32,"");
    	}
    	this.passwordList.getItems().add(newPassword);
    	this.passwordList.getSelectionModel().select(this.passwordList.getItems().size()-1);
    	this.listRemoveButton.setDisable(false);
    }

	//event for the remove button on the password list
    @FXML
    private void onRemove(ActionEvent event){
    	//sanity checks
    	if(this.passwordList.getItems().size()<1){
    		return;
    	}
    	int currentSelection=this.passwordList.getSelectionModel().getSelectedIndex();
    	if(currentSelection<0){
    		return;
    	}
    	this.isRemoving=true;
    	this.passwordList.getItems().remove(this.passwordList.getSelectionModel().getSelectedIndex());
    	this.isRemoving=false;
    	int newSize=this.passwordList.getItems().size();
    	if(newSize<=1){
    		this.listRemoveButton.setDisable(true);
    		this.lastSelectionIndex=-1;
    	}
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
    	this.passwordList.getItems().add(this.getPasswordSettings());
    	this.enableDisableFields(false);
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
    
    //event that fires when a key is pressed in the passphrase textfield
    @FXML
    private void onPassphraseChange(KeyEvent event){
    	this.generatePassword();
    }

    //event that fires when a key is pressed in the password name textfield
    @FXML
    private void onPasswordNameChange(KeyEvent event){
    	this.passwordChanged=true;
    	this.generatePassword();
    	this.checkAddNew();
    }
    
    //event that fires when any of the "Use xxxx" checkboxes are checked/unchecked
    @FXML
    private void onPasswordSettingsCheckbox(ActionEvent event){
    	this.passwordChanged=true;
    	this.generatePassword();
    	this.checkAddNew();
    }
    
    //event that fires when a key is pressed in the password name textfield
    @FXML
    private void onPasswordNotesChange(KeyEvent event){
    	this.passwordChanged=true;
    	this.checkAddNew();
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
        int currentSelectionIndex=this.passwordList.getSelectionModel().getSelectedIndex();
        if(currentSelectionIndex==this.lastSelectionIndex){
        	return;                	
        }
        //TODO - if I want to prompt to save unsaved changes this is the place to do it
        //update stuff and deal with annoying lose focus behavior
        if(currentSelectionIndex>-1){
        	this.lastSelectionIndex=currentSelectionIndex;
        }else{
        	//this is to deal with the selected index changing when focus is lost
        	if(this.passwordList.getItems().size()<0){
        		this.lastSelectionIndex=-1;
        	}           	
        }
        //now update the form fields
        this.updateFormFields(newValue);
        this.enableDisableFields(false);
        this.passwordChanged=false;
    }

    //update all the password setting fields in the right pane
    private void updateFormFields(PasswordSetting setting){
    	if(setting!=null){
	    	this.fieldPasswordName.setText(setting.getPasswordName());
	    	this.fieldPasswordNotes.setText(setting.getPasswordNotes());
	        this.checkLowerCase.setSelected(setting.isOptionUseLCase());
	        this.checkUpperCase.setSelected(setting.isOptionUseUCase());
	        this.checkNumbers.setSelected(setting.isOptionUseNumbers());
	        this.checkSpecialCharacters.setSelected(setting.isOptionUseSChars());
	        this.sliderPasswordLength.setValue(setting.getPasswordLength());
	        this.generatePassword();
	    }
    }
    
    //save PasswordSetting behind the selected item in the password list
    private void updateSelectedListItem(){
    	if(isRemoving){return;}
    	int size=this.passwordList.getItems().size();
    	if((this.lastSelectionIndex>-1)&&(size>0)&&(this.lastSelectionIndex<size)){
			PasswordSetting setting=new PasswordSetting(
					fieldPasswordName.getText(),
					checkLowerCase.isSelected(),
					checkUpperCase.isSelected(),
					checkNumbers.isSelected(),
					checkSpecialCharacters.isSelected(),
					(int)sliderPasswordLength.getValue(),
					fieldPasswordNotes.getText());
			this.passwordList.getItems().set(this.lastSelectionIndex,setting);
		}
    }    
    
    //update the password
    private void generatePassword(){
        String passPhrase=this.fieldPassPhrase.getText();
        String passwordName=this.fieldPasswordName.getText(); 
        boolean useLCase=this.checkLowerCase.isSelected();
        boolean useUCase=this.checkUpperCase.isSelected();
        boolean useNumbers=this.checkNumbers.isSelected();
        boolean useSpecialCharacters=this.checkSpecialCharacters.isSelected();
        int basePasswordLength=(int)this.sliderPasswordLength.getValue()/8;
        String password="";
        if((passPhrase.length()>0)&&(passwordName.length()>0)&&(!passPhrase.equals(passwordName))&&(useLCase||useUCase||useNumbers||useSpecialCharacters)){
            password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
            this.copyButton.setDisable(false);
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
    	}else if(this.passwordList.getItems().contains(setting)){
    		this.addPasswordButton.setDisable(true);
    		this.savePasswordButton.setDisable(true);
    	}else{
    		this.addPasswordButton.setDisable(false);
    		this.savePasswordButton.setDisable(true);
    	}
    }
    
    //enable or disable password setting fields
    private void enableDisableFields(boolean enabled){
        this.fieldPasswordName.setDisable(!enabled);
        this.fieldPasswordNotes.setDisable(!enabled);
        this.checkLowerCase.setDisable(!enabled);
        this.checkUpperCase.setDisable(!enabled);
        this.checkNumbers.setDisable(!enabled);
        this.checkSpecialCharacters.setDisable(!enabled);
        this.sliderPasswordLength.setDisable(!enabled);
        this.editPasswordButton.setDisable(enabled);
        this.savePasswordButton.setDisable(!enabled);
        this.addPasswordButton.setDisable(!enabled);
        this.undoPasswordButton.setDisable(!enabled);
    }
    
    //get the current password settings
    private PasswordSetting getPasswordSettings(){
    	return(new PasswordSetting(
    			this.fieldPasswordName.getText(),
    			this.checkLowerCase.isSelected(),
    			this.checkUpperCase.isSelected(),
    			this.checkNumbers.isSelected(),
    			this.checkSpecialCharacters.isSelected(),
    			(int)this.sliderPasswordLength.getValue(),
    			this.fieldPasswordNotes.getText()));
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
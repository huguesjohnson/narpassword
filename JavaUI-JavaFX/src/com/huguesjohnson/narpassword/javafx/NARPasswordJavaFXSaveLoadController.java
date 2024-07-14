/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpassword.javafx;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huguesjohnson.dubbel.fx.ImageUtil;
import com.huguesjohnson.narpas.PasswordSetting;
import com.huguesjohnson.narpas.PasswordSettingNameComparator;
import com.huguesjohnson.narpas.StringEncryptDecrypt;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NARPasswordJavaFXSaveLoadController implements Initializable{
	private ResourceBundle bundle;
	private String savePath;
	private boolean cancel=true;
	private List<PasswordSetting> passwordSettingList;
    @FXML private TextField fieldSavePath;
    @FXML private PasswordField fieldSavePassword;
    @FXML private PasswordField fieldConfirmSavePassword;
    @FXML private Label labelSaveLoadError;
    @FXML private Label labelConfirmPassword;
    @FXML private Button saveLoadButton;
    @FXML private Button browseButton;
    @FXML private Button cancelButton;
    @FXML private String settingsJson;
    public enum SaveDialogMode{SAVE,LOAD};
    private SaveDialogMode mode;

	@Override
	public void initialize(URL url,ResourceBundle bundle){
		this.bundle=bundle;
		ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.browseButton,"open.png",NARPasswordJavaFXSaveLoadController.class);
		ImageUtil.drawButtonImageIfNotLoadedFromFXML(this.cancelButton,"cancel.png",NARPasswordJavaFXSaveLoadController.class);
	}

	public boolean getCancel(){return(this.cancel);}

	public List<PasswordSetting> getPasswordSettingList(){return(this.passwordSettingList);}

	public void setPasswordSettingList(List<PasswordSetting> passwordSettingList){this.passwordSettingList=passwordSettingList;}
	
	public void setSavePath(String savePath){
		this.savePath=savePath;
		this.fieldSavePath.setText(savePath);
		if(savePath!=null){
			if((new File(savePath)).exists()){
				this.saveLoadButton.setDisable(false);
			}
		}
	}

	public String getSavePath(){return(this.savePath);}

	public void setMode(SaveDialogMode mode){
		this.mode=mode;
		if(mode==SaveDialogMode.SAVE){
			this.saveLoadButton.setText(this.bundle.getString("button_save"));
			this.saveLoadButton.setGraphic(new ImageView(new Image(NARPasswordJavaFXSaveLoadController.class.getResourceAsStream("save.png"))));
			this.saveLoadButton.setTooltip(new Tooltip(this.bundle.getString("tooltip_savedialog")));
			this.labelConfirmPassword.setVisible(true);
			this.fieldConfirmSavePassword.setVisible(true);
		}else{
			this.saveLoadButton.setText(this.bundle.getString("button_load"));
			this.saveLoadButton.setGraphic(new ImageView(new Image(NARPasswordJavaFXSaveLoadController.class.getResourceAsStream("open.png"))));
			this.saveLoadButton.setTooltip(new Tooltip(this.bundle.getString("tooltip_loaddialog")));
			this.labelConfirmPassword.setVisible(false);
			this.fieldConfirmSavePassword.setVisible(false);
		}
	}
	
    //event for the browse button
    @FXML
    private void onBrowse(ActionEvent event){
        if(mode==SaveDialogMode.SAVE){
   			FileChooser fileChooser=new FileChooser();
   			fileChooser.setTitle(this.bundle.getString("savefc_title"));
   			fileChooser.setInitialFileName(savePath);
   			File file=fileChooser.showSaveDialog(null);
            if(file!=null){
            	this.saveLoadButton.setDisable(false);
            	savePath=file.getAbsolutePath();
            	this.fieldSavePath.setText(savePath);
            }
            verifyEncryptionPasswordMatch();
        }else{
   			FileChooser fileChooser=new FileChooser();
   			fileChooser.setTitle(this.bundle.getString("loadfc_title"));
   			fileChooser.setInitialFileName(savePath);
            File file=fileChooser.showOpenDialog(null);
            if(file!=null){
            	saveLoadButton.setDisable(false);
            	savePath=file.getAbsolutePath();
            	fieldSavePath.setText(savePath);
            }        	
        }

    }

    //event for changes to the encryption password fields
    @FXML
    private void onEncryptionPasswordChange(KeyEvent event){
    	this.verifyEncryptionPasswordMatch();
    }
    
    //event for the save/load button
    @FXML
    private void onSaveLoad(ActionEvent event){
        if(mode==SaveDialogMode.SAVE){
			//encrypt password settings list
			settingsJson=new Gson().toJson(this.passwordSettingList);
			try{
				String encryptedString=StringEncryptDecrypt.encrypt(fieldSavePassword.getText(),settingsJson);
				PrintWriter writer=new PrintWriter(fieldSavePath.getText());
				writer.write(encryptedString);
				writer.flush();
				writer.close();
				this.cancel=false;
				this.closeWindow(event);
			}catch(Exception x){
				labelSaveLoadError.setText(x.getMessage());
			}
		}else{//load
			String encryptedString=null;
			try{
				encryptedString=Files.readString(Paths.get(fieldSavePath.getText()));
    		}catch(Exception x){
				labelSaveLoadError.setText(x.getMessage());
			}
			if(encryptedString!=null){
    			try{
    				String json=StringEncryptDecrypt.decryptString(fieldSavePassword.getText(),encryptedString);
    				Type listType=new TypeToken<ArrayList<PasswordSetting>>(){}.getType();
    				this.passwordSettingList=new Gson().fromJson(json,listType);
    				this.passwordSettingList.sort(new PasswordSettingNameComparator(false));
    				this.cancel=false;
    				this.closeWindow(event);
        		}catch(Exception x){
    				labelSaveLoadError.setText("Decrypt error "+x.getMessage());
    			}
			}
		}
    }
    
    //event for the cancel button
    @FXML
    private void onCancel(ActionEvent event){
    	this.cancel=true;
    	this.closeWindow(event);
    }
    
    private void closeWindow(Event event){
    	//extremely intuitive way to close the parent window
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
    
    private void verifyEncryptionPasswordMatch(){
    	if(this.mode==SaveDialogMode.SAVE){
	    	if(this.fieldSavePassword.getText().equals(this.fieldConfirmSavePassword.getText())){
	    		this.labelSaveLoadError.setText("");
	    		this.saveLoadButton.setDisable(false);
	    	}
	    	else{
	    		this.labelSaveLoadError.setText(this.bundle.getString("label_errorpasswordmatch"));
	    		this.saveLoadButton.setDisable(true);
	    	}
	    }
    }
}
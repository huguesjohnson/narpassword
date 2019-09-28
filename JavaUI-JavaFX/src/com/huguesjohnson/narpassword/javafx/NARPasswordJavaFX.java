/*
NARPassword for Java - Application to generate a non-random password
Copyright (C) 2011-2019 Hugues Johnson

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

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huguesjohnson.narpas.Narpas;
import com.huguesjohnson.narpas.PasswordSetting;
import com.huguesjohnson.narpas.StringEncryptDecrypt;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NARPasswordJavaFX extends Application{
	private Stage primaryStage;
	private PasswordField fieldPassPhrase;
    private TextField fieldPasswordName;
    private TextField fieldPassword;
    private TextField fieldPasswordNotes;
    private Button buttonCopy;
    private CheckBox checkLowerCase;
    private CheckBox checkUpperCase;
    private CheckBox checkNumbers;
    private CheckBox checkSpecialCharacters;
    private Slider sliderPasswordLength;
    private ListView<PasswordSetting> passwordList;
    private Button openButton;
    private Button saveButton;
    private Button listAddButton;
    private Button listRemoveButton;
    private int lastSelectionIndex;
    private boolean isRemoving=false;
    //stuff for save & load
    private String savePath;
    private TextField fieldSavePath;
    private PasswordField fieldSavePassword;
    private PasswordField fieldConfirmSavePassword;
    private Label labelSaveLoadError;
    private Button saveLoadButton;
    private String settingsJson;
    enum SaveDialogMode{SAVE,LOAD};
    
    public static void main(String[] args){
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
    	this.primaryStage=primaryStage;
    	SplitPane splitPane=new SplitPane();
    	//splitPane.
    	
    	//left side of UI
    	VBox leftPane=new VBox();

    	//caption
    	Label passwordLabel=new Label("Passwords");
        passwordLabel.setAlignment(Pos.BASELINE_LEFT);
        leftPane.getChildren().add(passwordLabel);

        //list of passwords
        PasswordSetting defaultPassword=new PasswordSetting("<new password>",true,true,true,true,32,"");
        this.passwordList=new ListView<PasswordSetting>();
        this.passwordList.setEditable(false);
        ObservableList<PasswordSetting> items=FXCollections.observableArrayList(defaultPassword);
        this.passwordList.setItems(items);
        this.passwordList.getSelectionModel().select(0);
        this.lastSelectionIndex=0;
        this.passwordList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PasswordSetting>() {
            @Override
            public void changed(ObservableValue<? extends PasswordSetting> observable,PasswordSetting oldValue,PasswordSetting newValue){
                int currentSelectionIndex=passwordList.getSelectionModel().getSelectedIndex();
                if(currentSelectionIndex==lastSelectionIndex)
                {
                	return;                	
                }
            	//save previous item
           		updateSelectedListItem();
                //update stuff and deal with annoying lose focus behavior
                if(currentSelectionIndex>-1)
                {
                	lastSelectionIndex=currentSelectionIndex;
                }
                else
                {
                	//this is to deal with the selected index changing when focus is lost
                	if(passwordList.getItems().size()<0)
                	{
                		lastSelectionIndex=-1;
                	}           	
                }
                //now update the form fields
                updateFormFields(newValue);
            }
        });

        leftPane.getChildren().add(passwordList);

        //listview buttons
        ToolBar toolbar=new ToolBar();
        this.openButton=new Button();
        this.openButton.setTooltip(new Tooltip("Open a password list.."));
        this.openButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("document-open.png"))));
        this.openButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t){
                showSaveLoadDialog(SaveDialogMode.LOAD);
            }
        });
        toolbar.getItems().add(this.openButton);

        this.saveButton=new Button();
        this.saveButton.setTooltip(new Tooltip("Save password list.."));
        this.saveButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("document-save.png"))));
        this.saveButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t){
                showSaveLoadDialog(SaveDialogMode.SAVE);
            }
        });
        toolbar.getItems().add(this.saveButton);

        toolbar.getItems().add(new Separator());
        
        this.listAddButton=new Button();
        this.listAddButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("list-add.png"))));
        this.listAddButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t){
            	PasswordSetting newPassword=new PasswordSetting("<new password "+(passwordList.getItems().size()+1)+">",true,true,true,true,32,"");
                passwordList.getItems().add(newPassword);
                passwordList.getSelectionModel().select(passwordList.getItems().size()-1);
                listRemoveButton.setDisable(false);
            }
        });
        toolbar.getItems().add(this.listAddButton);

        this.listRemoveButton=new Button();
        this.listRemoveButton.setDisable(true);
        this.listRemoveButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("list-remove.png"))));
        this.listRemoveButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t){
            	//sanity check
            	if(passwordList.getItems().size()<1)
            	{
            		return;
            	}
            	int currentSelection=passwordList.getSelectionModel().getSelectedIndex();
            	if(currentSelection<0)
            	{
            		return;
            	}
            	isRemoving=true;
            	passwordList.getItems().remove(passwordList.getSelectionModel().getSelectedIndex());
            	isRemoving=false;
            	int newSize=passwordList.getItems().size();
            	if(newSize<=1)
            	{
            		listRemoveButton.setDisable(true);
                	lastSelectionIndex=-1;
            	}
            }
        });
        toolbar.getItems().add(this.listRemoveButton);

        leftPane.getChildren().add(toolbar);

        
    	//right side of UI
        GridPane rightPane=new GridPane();
        rightPane.setHgap(2);
        rightPane.setVgap(2);

        //Pass phrase
        HBox boxPassPhrase=new HBox();
        Label labelPassPhrase=new Label("Pass Phrase: ");
        labelPassPhrase.setPrefWidth(120);
        labelPassPhrase.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPassPhrase=new PasswordField();
        this.fieldPassPhrase.setPrefColumnCount(32);
        this.fieldPassPhrase.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override 
            public void handle(KeyEvent t){
                generatePassword();
            }
        });        
        boxPassPhrase.getChildren().addAll(labelPassPhrase,this.fieldPassPhrase);
        rightPane.add(boxPassPhrase,0,0);

        //Pass phrase hint
        HBox boxPassPhraseHint=new HBox();
        Label labelPassPhraseHintSpacer=new Label(" ");
        labelPassPhraseHintSpacer.setPrefWidth(120);
        Label labelPassPhraseHint=new Label("i.e.'I like pie' or 'Please no more California songs'");
        boxPassPhraseHint.getChildren().addAll(labelPassPhraseHintSpacer,labelPassPhraseHint);
        rightPane.add(boxPassPhraseHint,0,1);
        
        //Password name
        HBox boxPasswordName=new HBox();
        Label labelPasswordName=new Label("Password Name: ");
        labelPasswordName.setPrefWidth(120);
        labelPasswordName.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPasswordName=new TextField(defaultPassword.getPasswordName());
        this.fieldPasswordName.setPrefColumnCount(32);
        this.fieldPasswordName.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override 
            public void handle(KeyEvent t){
                generatePassword();
            }
        });           
        boxPasswordName.getChildren().addAll(labelPasswordName,this.fieldPasswordName);
        rightPane.add(boxPasswordName,0,2);

        //Password name hint
        HBox boxPasswordNameHint=new HBox();
        Label labelPasswordNameHintSpacer=new Label(" ");
        labelPasswordNameHintSpacer.setPrefWidth(120);
        Label labelPasswordNameHint=new Label("i.e.'Facebook' or 'yourname@gmail.com'");
        boxPasswordNameHint.getChildren().addAll(labelPasswordNameHintSpacer,labelPasswordNameHint);
        rightPane.add(boxPasswordNameHint,0,3);
        
        //Password
        HBox boxPassword=new HBox();
        Label labelPassword=new Label("Password: ");
        labelPassword.setPrefWidth(120);
        labelPassword.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPassword=new TextField();
        this.fieldPassword.setPrefColumnCount(32);
        this.fieldPassword.setEditable(false);
        this.buttonCopy=new Button("Copy");
        this.buttonCopy.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t){
                final Clipboard clipboard=Clipboard.getSystemClipboard();
                final ClipboardContent content=new ClipboardContent();
                content.putString(fieldPassword.getText());
                clipboard.setContent(content);
            }
        });
        boxPassword.getChildren().addAll(labelPassword,this.fieldPassword,this.buttonCopy);
        rightPane.add(boxPassword,0,4);
        
        //Password options
        HBox boxPasswordOptions=new HBox();
        Label labelPasswordOptions=new Label("Password Options");
        boxPasswordOptions.getChildren().addAll(labelPasswordOptions);
        rightPane.add(boxPasswordOptions,0,5);
        
        //Lowercase checkbox
        this.checkLowerCase=new CheckBox("Use lower case characters (a-z)");
        this.checkLowerCase.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t) {
                generatePassword();
            }
        });
        this.checkLowerCase.setSelected(true);
        rightPane.add(this.checkLowerCase,0,6);
        
        //Uppercase checkbox
        this.checkUpperCase=new CheckBox("Use upper case characters (A-Z)");
        this.checkUpperCase.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t) {
                generatePassword();
            }
        });
        this.checkUpperCase.setSelected(true);
        rightPane.add(this.checkUpperCase,0,7);

        //Numbers checkbox
        this.checkNumbers=new CheckBox("Use numbers (0-9)");
        this.checkNumbers.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t) {
                generatePassword();
            }
        });
        this.checkNumbers.setSelected(true);
        rightPane.add(this.checkNumbers,0,8);

        //Special characters checkbox
        this.checkSpecialCharacters=new CheckBox("Use special characters (!@#$%^&*-=+:;?,.)");
        this.checkSpecialCharacters.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t) {
                generatePassword();
            }
        });
        this.checkSpecialCharacters.setSelected(true);
        rightPane.add(this.checkSpecialCharacters,0,9);
        
        //Password length
        HBox boxPasswordLength=new HBox();
        Label labelPasswordLength=new Label("Password Length: ");
        labelPasswordLength.setPrefWidth(120);
        labelPasswordLength.setAlignment(Pos.BASELINE_RIGHT);
        this.sliderPasswordLength=new Slider();
        this.sliderPasswordLength.setPrefWidth(380);
        this.sliderPasswordLength.setMin(8);
        this.sliderPasswordLength.setMax(128);
        this.sliderPasswordLength.setBlockIncrement(8);
        this.sliderPasswordLength.setMajorTickUnit(8);
        this.sliderPasswordLength.setShowTickLabels(true);
        this.sliderPasswordLength.setShowTickMarks(true);
        this.sliderPasswordLength.setMinorTickCount(0);
        this.sliderPasswordLength.setValue(32);
        this.sliderPasswordLength.setSnapToTicks(true);
        this.sliderPasswordLength.valueProperty().addListener(new ChangeListener<Number>(){
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                sliderPasswordLength.setTooltip(new Tooltip(new_val.toString()));
                generatePassword();
            }
        });
        
        boxPasswordLength.getChildren().addAll(labelPasswordLength,this.sliderPasswordLength);
        rightPane.add(boxPasswordLength,0,10);

        //Password notes
        HBox boxPasswordNotes=new HBox();
        Label labelPasswordNotes=new Label("Password Notes: ");
        labelPasswordNotes.setPrefWidth(120);
        labelPasswordNotes.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPasswordNotes=new TextField();
        this.fieldPasswordNotes.setPrefColumnCount(32);
        boxPasswordNotes.getChildren().addAll(labelPasswordNotes,this.fieldPasswordNotes);
        rightPane.add(boxPasswordNotes,0,11);
        
        //setup main windows
        splitPane.getItems().add(leftPane);
        splitPane.getItems().add(rightPane);
        splitPane.setDividerPositions(0.25f);
        Scene scene=new Scene(splitPane);
        primaryStage.setTitle("NARPassword for JavaFX v1.1");
        primaryStage.getIcons().add(new Image(NARPasswordJavaFX.class.getResourceAsStream("narpas-icon-16.png")));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void updateSelectedListItem()
    {
    	if(isRemoving){return;}
    	int size=this.passwordList.getItems().size();
    	if((lastSelectionIndex>-1)&&(size>0)&&(lastSelectionIndex<size))
    	{
			PasswordSetting setting=new PasswordSetting(
					fieldPasswordName.getText(),
					checkLowerCase.isSelected(),
					checkUpperCase.isSelected(),
					checkNumbers.isSelected(),
					checkSpecialCharacters.isSelected(),
					(int)sliderPasswordLength.getValue(),
					fieldPasswordNotes.getText());
			passwordList.getItems().set(lastSelectionIndex,setting);
		}
    }

    private void updateFormFields(PasswordSetting setting)
    {
    	if(setting!=null)
    	{
	    	this.fieldPasswordName.setText(setting.getPasswordName());
	    	this.fieldPasswordNotes.setText(setting.getPasswordNotes());
	        this.checkLowerCase.setSelected(setting.isOptionUseLCase());
	        this.checkUpperCase.setSelected(setting.isOptionUseUCase());
	        this.checkNumbers.setSelected(setting.isOptionUseNumbers());
	        this.checkSpecialCharacters.setSelected(setting.isOptionUseSChars());
	        this.sliderPasswordLength.setValue(setting.getPasswordLength());
	    	generatePassword();
	    }
    }

    private void showSaveLoadDialog(SaveDialogMode mode)
    {
    	Stage dialog=new Stage();

        GridPane pane=new GridPane();
        pane.setHgap(2);
        pane.setVgap(2);
        int paneY=0;
        
        //file path
        HBox boxFilePathBox=new HBox();
        Label labelFilePath=new Label("File path: ");
        labelFilePath.setPrefWidth(180);
        labelFilePath.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldSavePath=new TextField();
        this.fieldSavePath.setPrefColumnCount(40);
        if((this.savePath!=null)&&(this.savePath.length()>0))
        {
        	this.fieldSavePath.setText(this.savePath);
        }
        Button browseButton=new Button("Browse");
        browseButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("document-open.png"))));
        if(mode==SaveDialogMode.SAVE)
        {
            browseButton.setOnAction(new EventHandler<ActionEvent>(){
           		@Override 
           		public void handle(ActionEvent t){
           			FileChooser fileChooser=new FileChooser();
           			fileChooser.setTitle("Select save location");
           			fileChooser.setInitialFileName(savePath);
                    File file=fileChooser.showSaveDialog(dialog);
                    if(file!=null)
                    {
                    	saveLoadButton.setDisable(false);
                    	savePath=file.getAbsolutePath();
                    	fieldSavePath.setText(savePath);
                    }
                    verifyEncryptionPasswordMatch();
           		}
           	});
        }
        else
        {
            browseButton.setOnAction(new EventHandler<ActionEvent>(){
           		@Override 
           		public void handle(ActionEvent t){
           			FileChooser fileChooser=new FileChooser();
           			fileChooser.setTitle("Select password file to load");
           			fileChooser.setInitialFileName(savePath);
                    File file=fileChooser.showOpenDialog(dialog);
                    if(file!=null)
                    {
                    	saveLoadButton.setDisable(false);
                    	savePath=file.getAbsolutePath();
                    	fieldSavePath.setText(savePath);
                    }
           		}
           	});
        }
        boxFilePathBox.getChildren().addAll(labelFilePath,this.fieldSavePath,browseButton);
        pane.add(boxFilePathBox,0,paneY);
        paneY++;

        //encryption password
        HBox encryptionPasswordBox=new HBox();
        Label labelEncryptionPassword=new Label("Encryption password: ");
        labelEncryptionPassword.setPrefWidth(180);
        labelEncryptionPassword.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldSavePassword=new PasswordField();
        this.fieldSavePassword.setPrefColumnCount(40);
        if(mode==SaveDialogMode.SAVE)
        {
	        this.fieldSavePassword.setOnKeyReleased(new EventHandler<KeyEvent>(){
	            @Override 
	            public void handle(KeyEvent t){
	                verifyEncryptionPasswordMatch();
	            }
	        });
	    }
        encryptionPasswordBox.getChildren().addAll(labelEncryptionPassword,this.fieldSavePassword);
        pane.add(encryptionPasswordBox,0,paneY);
        paneY++;

        //confirm encryption password
        if(mode==SaveDialogMode.SAVE)
        {
            HBox confirmEncryptionPasswordBox=new HBox();
            Label labelConfirmEncryptionPassword=new Label("Confirm password: ");
            labelConfirmEncryptionPassword.setPrefWidth(180);
            labelConfirmEncryptionPassword.setAlignment(Pos.BASELINE_RIGHT);
            this.fieldConfirmSavePassword=new PasswordField();
            this.fieldConfirmSavePassword.setPrefColumnCount(40);
	        this.fieldConfirmSavePassword.setOnKeyReleased(new EventHandler<KeyEvent>(){
	            @Override 
	            public void handle(KeyEvent t){
	                verifyEncryptionPasswordMatch();
	            }
	        });
	        confirmEncryptionPasswordBox.getChildren().addAll(labelConfirmEncryptionPassword,this.fieldConfirmSavePassword);
            pane.add(confirmEncryptionPasswordBox,0,paneY);
            paneY++;
        }
        
        this.labelSaveLoadError=new Label("");
        this.labelSaveLoadError.setPrefWidth(320);
        this.labelSaveLoadError.setAlignment(Pos.BASELINE_LEFT);
        pane.add(this.labelSaveLoadError,0,paneY);
        paneY++;

        //toolbar
        HBox toolbarbox=new HBox();
        toolbarbox.setAlignment(Pos.BASELINE_RIGHT);
        ToolBar toolbar=new ToolBar();
        if(mode==SaveDialogMode.SAVE)
        {
            this.saveLoadButton=new Button("Save");
            this.saveLoadButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("document-save.png"))));
            this.saveLoadButton.setOnAction(new EventHandler<ActionEvent>(){
        		@Override 
        		public void handle(ActionEvent t){
        			//encrypt password settings list
        			List<PasswordSetting> settingsList=passwordList.getItems();
        			settingsJson=new Gson().toJson(settingsList);
        			try
        			{
        				String encryptedString=StringEncryptDecrypt.encrypt(fieldSavePassword.getText(),settingsJson);
        				PrintWriter writer=new PrintWriter(fieldSavePath.getText());
        				writer.write(encryptedString);
        				writer.flush();
        				writer.close();
        				labelSaveLoadError.setText("Saved "+fieldSavePath.getText());
        			}
        			catch(Exception x)
        			{
        				labelSaveLoadError.setText(x.getMessage());
        			}
                }
            });
        }
        else
        {
            this.saveLoadButton=new Button("Load");
            this.saveLoadButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("document-open.png"))));
            this.saveLoadButton.setOnAction(new EventHandler<ActionEvent>(){
        		@Override 
        		public void handle(ActionEvent t){
        			String encryptedString=null;
        			try
        			{
        				encryptedString=Files.readString(Paths.get(fieldSavePath.getText()));
            		}
        			catch(Exception x)
        			{
        				labelSaveLoadError.setText(x.getMessage());
        			}
        			if(encryptedString!=null)
        			{
	        			try
	        			{
	        				String json=StringEncryptDecrypt.decryptString(fieldSavePassword.getText(),encryptedString);
	        				Type listType=new TypeToken<ArrayList<PasswordSetting>>(){}.getType();
	        				List<PasswordSetting> list=new Gson().fromJson(json,listType);
	        				passwordList.getItems().clear();
	        				passwordList.getItems().addAll(list);
	        				System.out.println(json);
	            			((Node)(t.getSource())).getScene().getWindow().hide();
	            		}
	        			catch(Exception x)
	        			{
	        				labelSaveLoadError.setText("Decrypt error "+x.getMessage());
	        			}
        			}
                }
            });
        }
        if(savePath==null)
        {
        	this.saveLoadButton.setDisable(true);
        }
        toolbar.getItems().add(this.saveLoadButton);
        Button cancelButton=new Button("Close");
        cancelButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("process-stop.png"))));
        cancelButton.setOnAction(new EventHandler<ActionEvent>(){
    		@Override 
    		public void handle(ActionEvent t){
    			((Node)(t.getSource())).getScene().getWindow().hide();
            }
        });
        toolbar.getItems().add(cancelButton);
        toolbarbox.getChildren().addAll(toolbar);
        pane.add(toolbarbox,0,paneY);
        
        Scene scene=new Scene(pane);
        if(mode==SaveDialogMode.LOAD) 
        {
            dialog.setTitle("Load");
        }
        else 
        {
            dialog.setTitle("Save");
        }
        dialog.getIcons().add(new Image(NARPasswordJavaFX.class.getResourceAsStream("narpas-icon-16.png")));
        dialog.setResizable(false);
        dialog.setScene(scene);
    	dialog.initOwner(this.primaryStage);
    	dialog.initModality(Modality.APPLICATION_MODAL); 
    	dialog.showAndWait();

    	
    }
    
    private void verifyEncryptionPasswordMatch()
    {
    	if(this.fieldSavePassword.getText().equals(this.fieldConfirmSavePassword.getText()))
    	{
    		this.labelSaveLoadError.setText("");
    		this.saveLoadButton.setDisable(false);
    	}
    	else
    	{
    		this.labelSaveLoadError.setText("Passwords don't match");
    		this.saveLoadButton.setDisable(true);
    	}
    	
    	
    }
    
    private void generatePassword()
    {
    	updateSelectedListItem();
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
        }
        this.fieldPassword.setText(password);
    }
}
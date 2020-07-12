/*
NARPassword for Java - Application to generate a non-random password
Copyright (C) 2011-2020 Hugues Johnson

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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huguesjohnson.narpas.Narpas;
import com.huguesjohnson.narpas.PasswordSetting;
import com.huguesjohnson.narpas.PasswordSettingComparator;
import com.huguesjohnson.narpas.StringEncryptDecrypt;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private CheckBox checkClearClipboard;
    private TextField fieldClearClipboardSeconds;
    private long lastClipboardClear=0;
    private CheckBox checkClearPassPhrase;
    private TextField fieldClearPassPhraseSeconds;
    private long lastPassPhraseClear=0;
    private boolean passwordInClipboard=false;
    private Slider sliderPasswordLength;
    private ListView<PasswordSetting> passwordList;
    private Button openButton;
    private Button saveButton;
    private Button listAddButton;
    private Button listRemoveButton;
    private int lastSelectionIndex;
    private boolean isRemoving=false;
    private Insets hboxInsets=new Insets(8,4,0,4);
    private Insets checkboxInsets=new Insets(4,4,0,4);
    private Pos hboxPos=Pos.CENTER_LEFT;
    //stuff for save & load
    private static String savePath;
    private TextField fieldSavePath;
    private PasswordField fieldSavePassword;
    private PasswordField fieldConfirmSavePassword;
    private Label labelSaveLoadError;
    private Button saveLoadButton;
    private String settingsJson;
    enum SaveDialogMode{SAVE,LOAD};
    //fonts
	private static double size;
	private static Font fontRegular;
	private static Font fontBold;
	private static Font fontMono;
    
    public static void main(String[] args)
    {
    	//check command line
        if((args!=null)&&(args.length>0))
        {
        	for(int i=0;i<args.length;i++)
        	{
        		try
        		{
            		if(args[i].contains("="))
            		{
            			String[] split=args[i].split("=");
            			if(split.length==2)
            			{
            				if(split[0].toLowerCase().equals("--passwordlist"))
            				{
            					savePath=split[1];
            				}
            				else
            				{
            					throw(new Exception());
            				}
            			}
            			else
            			{
            				throw(new Exception());
            			}
            			
            		}
        			
        		}
        		catch(Exception x)
        		{
        			System.out.println("Invalid command line argument: "+args[i]);
        		}
        	}
        }
    	
    	//launch
    	Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)
    {
    	//load fonts
    	size=Font.getDefault().getSize();
    	fontRegular=(Font.loadFont(this.getClass().getResourceAsStream("Ubuntu-R.ttf"),size));
    	fontBold=(Font.loadFont(this.getClass().getResourceAsStream("Ubuntu-B.ttf"),size));
    	fontMono=(Font.loadFont(this.getClass().getResourceAsStream("UbuntuMono-R.ttf"),size));
    	
    	this.primaryStage=primaryStage;
    	SplitPane splitPane=new SplitPane();

    	//left side of UI
    	VBox leftPane=new VBox();
    	//caption
    	Label passwordLabel=new Label("Passwords");
    	passwordLabel.setPadding(new Insets(2,2,2,2));
    	passwordLabel.setFont(fontBold);
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
        this.passwordList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PasswordSetting>()
        {
            @Override
            public void changed(ObservableValue<? extends PasswordSetting> observable,PasswordSetting oldValue,PasswordSetting newValue)
            {
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
        this.openButton.setFont(fontRegular);
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
        this.saveButton.setFont(fontRegular);
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
        this.listAddButton.setFont(fontRegular);
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
        this.listRemoveButton.setFont(fontRegular);
        this.listRemoveButton.setDisable(true);
        this.listRemoveButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("list-remove.png"))));
        this.listRemoveButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent t)
            {
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
        boxPassPhrase.setAlignment(hboxPos);
        boxPassPhrase.setPadding(hboxInsets);
        Label labelPassPhrase=new Label("Pass Phrase: ");
        labelPassPhrase.setFont(fontRegular);
        labelPassPhrase.setPrefWidth(120);
        labelPassPhrase.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPassPhrase=new PasswordField();
        this.fieldPassPhrase.setPrefColumnCount(32);
        this.fieldPassPhrase.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override 
            public void handle(KeyEvent t)
            {
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
        labelPassPhraseHint.setFont(fontRegular);
        boxPassPhraseHint.getChildren().addAll(labelPassPhraseHintSpacer,labelPassPhraseHint);
        rightPane.add(boxPassPhraseHint,0,1);
        
        //Password name
        HBox boxPasswordName=new HBox();
        boxPasswordName.setPadding(hboxInsets);
        boxPasswordName.setAlignment(hboxPos);
        Label labelPasswordName=new Label("Password Name: ");
        labelPasswordName.setFont(fontRegular);
        labelPasswordName.setPrefWidth(120);
        labelPasswordName.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPasswordName=new TextField(defaultPassword.getPasswordName());
        this.fieldPasswordName.setPrefColumnCount(32);
        this.fieldPasswordName.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override 
            public void handle(KeyEvent t)
            {
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
        labelPasswordNameHint.setFont(fontRegular);
        boxPasswordNameHint.getChildren().addAll(labelPasswordNameHintSpacer,labelPasswordNameHint);
        rightPane.add(boxPasswordNameHint,0,3);
        
        //Password
        HBox boxPassword=new HBox();
        boxPassword.setPadding(hboxInsets);
        boxPassword.setAlignment(hboxPos);
        Label labelPassword=new Label("Password: ");
        labelPassword.setFont(fontRegular);
        labelPassword.setPrefWidth(120);
        labelPassword.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPassword=new TextField();
        this.fieldPassword.setFont(fontMono);
        this.fieldPassword.setStyle("-fx-background-color: #f5f5f5;");
        this.fieldPassword.setPrefColumnCount(60);
        this.fieldPassword.setEditable(false);
        this.buttonCopy=new Button("Copy");
        this.buttonCopy.setFont(fontRegular);
        this.buttonCopy.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent t)
            {
                final Clipboard clipboard=Clipboard.getSystemClipboard();
                final ClipboardContent content=new ClipboardContent();
                content.putString(fieldPassword.getText());
                clipboard.setContent(content);
                passwordInClipboard=true;
            }
        });
        boxPassword.getChildren().addAll(labelPassword,this.fieldPassword,this.buttonCopy);
        rightPane.add(boxPassword,0,4);
        
        //Password options
        HBox boxPasswordOptions=new HBox();
        boxPasswordOptions.setPadding(hboxInsets);
        Label labelPasswordOptions=new Label("Password Options");
        labelPasswordOptions.setPadding(new Insets(2,2,2,2));
        labelPasswordOptions.setFont(fontBold);
        boxPasswordOptions.getChildren().addAll(labelPasswordOptions);
        rightPane.add(boxPasswordOptions,0,5);

        //Lowercase checkbox
        this.checkLowerCase=new CheckBox("Use lower case characters (a-z)");
        this.checkLowerCase.setFont(fontRegular);
        this.checkLowerCase.setPadding(checkboxInsets);
        this.checkLowerCase.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent t)
            {
                generatePassword();
            }
        });
        this.checkLowerCase.setSelected(true);
        rightPane.add(this.checkLowerCase,0,6);
        
        //Uppercase checkbox
        this.checkUpperCase=new CheckBox("Use upper case characters (A-Z)");
        this.checkUpperCase.setFont(fontRegular);
        this.checkUpperCase.setPadding(checkboxInsets);
        this.checkUpperCase.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent t)
            {
                generatePassword();
            }
        });
        this.checkUpperCase.setSelected(true);
        rightPane.add(this.checkUpperCase,0,7);

        //Numbers checkbox
        this.checkNumbers=new CheckBox("Use numbers (0-9)");
        this.checkNumbers.setFont(fontRegular);
        this.checkNumbers.setPadding(checkboxInsets);
        this.checkNumbers.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent t)
            {
                generatePassword();
            }
        });
        this.checkNumbers.setSelected(true);
        rightPane.add(this.checkNumbers,0,8);

        //Special characters checkbox
        this.checkSpecialCharacters=new CheckBox("Use special characters (!@#$%^&*-=+:;?,.)");
        this.checkSpecialCharacters.setFont(fontRegular);
        this.checkSpecialCharacters.setPadding(checkboxInsets);
        this.checkSpecialCharacters.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent t)
            {
                generatePassword();
            }
        });
        this.checkSpecialCharacters.setSelected(true);
        rightPane.add(this.checkSpecialCharacters,0,9);
        
        //Password length
        HBox boxPasswordLength=new HBox();
        boxPasswordLength.setPadding(hboxInsets);
        boxPasswordLength.setAlignment(hboxPos);
        Label labelPasswordLength=new Label("Password Length: ");
        labelPasswordLength.setFont(fontRegular);
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
        this.sliderPasswordLength.valueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,Number old_val,Number new_val)
            {
                sliderPasswordLength.setTooltip(new Tooltip(new_val.toString()));
                generatePassword();
            }
        });
        
        boxPasswordLength.getChildren().addAll(labelPasswordLength,this.sliderPasswordLength);
        rightPane.add(boxPasswordLength,0,10);

        //Password notes
        HBox boxPasswordNotes=new HBox();
        boxPasswordNotes.setPadding(hboxInsets);
        boxPasswordNotes.setAlignment(hboxPos);
        Label labelPasswordNotes=new Label("Password Notes: ");
        labelPasswordNotes.setFont(fontRegular);
        labelPasswordNotes.setPrefWidth(120);
        labelPasswordNotes.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPasswordNotes=new TextField();
        this.fieldPasswordNotes.setPrefColumnCount(32);
        boxPasswordNotes.getChildren().addAll(labelPasswordNotes,this.fieldPasswordNotes);
        rightPane.add(boxPasswordNotes,0,11);

        //ui options
        HBox boxUIOptions=new HBox();
        boxUIOptions.setPadding(hboxInsets);
        Label labelUIOptions=new Label("UI Options");
        labelUIOptions.setFont(fontBold);
        boxUIOptions.getChildren().addAll(labelUIOptions);
        rightPane.add(boxUIOptions,0,12);
        
        //clear clipboard
        HBox boxClearClipboard=new HBox();
        boxClearClipboard.setPadding(checkboxInsets);
        boxClearClipboard.setAlignment(hboxPos);
        this.checkClearClipboard=new CheckBox("Clear password from clipboard after ");
        this.checkClearClipboard.setFont(fontRegular);
        this.checkClearClipboard.setSelected(true);
        this.checkClearClipboard.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent t)
            {
            	lastClipboardClear=0;
            }
        });        
        this.fieldClearClipboardSeconds=new TextField();
        this.fieldClearClipboardSeconds.setFont(fontRegular);
        this.fieldClearClipboardSeconds.setPrefColumnCount(4);
        this.fieldClearClipboardSeconds.setText("60");
        this.fieldClearClipboardSeconds.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override 
            public void handle(KeyEvent t)
            {
            	lastClipboardClear=0;
                String newText=fieldClearClipboardSeconds.getText();
                try
                {
                	int value=Integer.parseInt(newText);
                	if(value<0){
                		throw(new Exception(""));
                	}
                	fieldClearClipboardSeconds.setStyle("");
                }
                catch(Exception x)
                {
                	fieldClearClipboardSeconds.setStyle("-fx-background-color: red;");
                }
            }
        });           
        Label labelClearClipboardSeconds=new Label(" seconds");
        labelClearClipboardSeconds.setFont(fontRegular);
        labelClearClipboardSeconds.setPrefWidth(120);
        labelClearClipboardSeconds.setAlignment(Pos.BASELINE_LEFT);
        boxClearClipboard.getChildren().addAll(this.checkClearClipboard,this.fieldClearClipboardSeconds,labelClearClipboardSeconds);
        rightPane.add(boxClearClipboard,0,13);

        //clear passphrase
        HBox boxClearPassPhrase=new HBox();
        boxClearPassPhrase.setPadding(checkboxInsets);
        boxClearPassPhrase.setAlignment(hboxPos);
        this.checkClearPassPhrase=new CheckBox("Clear Pass Phrase after ");
        this.checkClearPassPhrase.setFont(fontRegular);
        this.checkClearPassPhrase.setSelected(true);
        this.checkClearPassPhrase.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override 
            public void handle(ActionEvent t) {
            	lastPassPhraseClear=0;
            }
        });        
        this.fieldClearPassPhraseSeconds=new TextField();
        this.fieldClearPassPhraseSeconds.setFont(fontRegular);
        this.fieldClearPassPhraseSeconds.setPrefColumnCount(4);
        this.fieldClearPassPhraseSeconds.setText("600");
        this.fieldClearPassPhraseSeconds.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override 
            public void handle(KeyEvent t)
            {
            	lastClipboardClear=0;
                String newText=fieldClearPassPhraseSeconds.getText();
                try{
                	int value=Integer.parseInt(newText);
                	if(value<0){
                		throw(new Exception(""));
                	}
                	fieldClearPassPhraseSeconds.setStyle("");
                }catch(Exception x){
                	fieldClearPassPhraseSeconds.setStyle("-fx-background-color: red;");
                }
            }
        });           
        Label labelClearPassPhraseSeconds=new Label(" seconds");
        labelClearPassPhraseSeconds.setFont(fontRegular);
        labelClearPassPhraseSeconds.setPrefWidth(120);
        labelClearPassPhraseSeconds.setAlignment(Pos.BASELINE_LEFT);
        boxClearPassPhrase.getChildren().addAll(this.checkClearPassPhrase,this.fieldClearPassPhraseSeconds,labelClearPassPhraseSeconds);
        rightPane.add(boxClearPassPhrase,0,14);

        //setup timer
        Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e)
        	{
        		if(passwordInClipboard&&checkClearClipboard.isSelected())
        		{
        			try{
        				int value=Integer.parseInt(fieldClearClipboardSeconds.getText());
        				if(lastClipboardClear>value){
        					lastClipboardClear=0;
        	                final Clipboard clipboard=Clipboard.getSystemClipboard();
        	                final ClipboardContent content=new ClipboardContent();
        	                content.putString("");
        	                clipboard.setContent(content);
        	                passwordInClipboard=false;
        				}else{
        					lastClipboardClear++;
        				}
        			}catch(Exception x){/* do nothing */}
        		}
        		if(checkClearPassPhrase.isSelected())
        		{
        			try{
        				int value=Integer.parseInt(fieldClearPassPhraseSeconds.getText());
        				if(lastPassPhraseClear>value){
        					lastPassPhraseClear=0;
        					fieldPassPhrase.setText("");
        					generatePassword();
        				}else{
        					lastPassPhraseClear++;
        				}
        			}catch(Exception x){/* do nothing */}
        		}
        	}
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        //setup main windows
        splitPane.getItems().add(leftPane);
        splitPane.getItems().add(rightPane);
        splitPane.setDividerPositions(0.25f);
        Scene scene=new Scene(splitPane);
        scene.getStylesheets().add("narpasswordfx.css");
        primaryStage.setTitle("NARPassword for JavaFX v1.1");
        primaryStage.getIcons().add(new Image(NARPasswordJavaFX.class.getResourceAsStream("narpas-icon-16.png")));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //check if a password list was passed at the command-line
        if(savePath!=null)
        {
        	showSaveLoadDialog(SaveDialogMode.LOAD);
        }
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
        pane.setHgap(4);
        pane.setVgap(4);
        int paneY=0;
        
        //file path
        HBox boxFilePathBox=new HBox();
        boxFilePathBox.setAlignment(hboxPos);
        boxFilePathBox.setPadding(hboxInsets);        
        Label labelFilePath=new Label("File path: ");
        labelFilePath.setFont(fontRegular);
        labelFilePath.setPrefWidth(180);
        labelFilePath.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldSavePath=new TextField();
        this.fieldSavePath.setFont(fontRegular);
        this.fieldSavePath.setPrefColumnCount(40);
        if((savePath!=null)&&(savePath.length()>0))
        {
        	this.fieldSavePath.setText(savePath);
        }
        Button browseButton=new Button("Browse");
        browseButton.setFont(fontRegular);
        browseButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("document-open.png"))));
        if(mode==SaveDialogMode.SAVE)
        {
            browseButton.setOnAction(new EventHandler<ActionEvent>()
            {
           		@Override 
           		public void handle(ActionEvent t) 
           		{
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
            browseButton.setOnAction(new EventHandler<ActionEvent>()
            {
           		@Override 
           		public void handle(ActionEvent t)
           		{
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
        encryptionPasswordBox.setAlignment(hboxPos);
        encryptionPasswordBox.setPadding(hboxInsets);        
        Label labelEncryptionPassword=new Label("Encryption password: ");
        labelEncryptionPassword.setFont(fontRegular);
        labelEncryptionPassword.setPrefWidth(180);
        labelEncryptionPassword.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldSavePassword=new PasswordField();
        this.fieldSavePassword.setPrefColumnCount(40);
        if(mode==SaveDialogMode.SAVE)
        {
	        this.fieldSavePassword.setOnKeyReleased(new EventHandler<KeyEvent>()
	        {
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
            confirmEncryptionPasswordBox.setAlignment(hboxPos);
            confirmEncryptionPasswordBox.setPadding(hboxInsets);        
            Label labelConfirmEncryptionPassword=new Label("Confirm password: ");
            labelConfirmEncryptionPassword.setFont(fontRegular);
            labelConfirmEncryptionPassword.setPrefWidth(180);
            labelConfirmEncryptionPassword.setAlignment(Pos.BASELINE_RIGHT);
            this.fieldConfirmSavePassword=new PasswordField();
            this.fieldConfirmSavePassword.setPrefColumnCount(40);
	        this.fieldConfirmSavePassword.setOnKeyReleased(new EventHandler<KeyEvent>()
	        {
	            @Override 
	            public void handle(KeyEvent t)
	            {
	                verifyEncryptionPasswordMatch();
	            }
	        });
	        confirmEncryptionPasswordBox.getChildren().addAll(labelConfirmEncryptionPassword,this.fieldConfirmSavePassword);
            pane.add(confirmEncryptionPasswordBox,0,paneY);
            paneY++;
        }
        
        this.labelSaveLoadError=new Label("");
        this.labelSaveLoadError.setFont(fontRegular);
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
            this.saveLoadButton.setFont(fontRegular);
            this.saveLoadButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("document-save.png"))));
            this.saveLoadButton.setOnAction(new EventHandler<ActionEvent>()
            {
        		@Override 
        		public void handle(ActionEvent t)
        		{
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
            this.saveLoadButton.setFont(fontRegular);
            this.saveLoadButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("document-open.png"))));
            this.saveLoadButton.setOnAction(new EventHandler<ActionEvent>()
            {
        		@Override 
        		public void handle(ActionEvent t)
        		{
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
	        				list.sort(new PasswordSettingComparator());
	        				passwordList.getItems().clear();
	        				passwordList.getItems().addAll(list);
	        				passwordList.getSelectionModel().select(0);
	        				passwordList.getFocusModel().focus(0);
	        				updateFormFields(passwordList.getItems().get(0));
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
        cancelButton.setFont(fontRegular);
        cancelButton.setGraphic(new ImageView(new Image(NARPasswordJavaFX.class.getResourceAsStream("process-stop.png"))));
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
    		@Override 
    		public void handle(ActionEvent t)
    		{
    			((Node)(t.getSource())).getScene().getWindow().hide();
            }
        });
        toolbar.getItems().add(cancelButton);
        toolbarbox.getChildren().addAll(toolbar);
        pane.add(toolbarbox,0,paneY);
        
        Scene scene=new Scene(pane);
        scene.getStylesheets().add("narpasswordfx.css");
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
        if((passPhrase.length()>0)&&(passwordName.length()>0)&&(!passPhrase.equals(passwordName))&&(useLCase||useUCase||useNumbers||useSpecialCharacters))
        {
            password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
        }
        this.fieldPassword.setText(password);
    }
}
package com.huguesjohnson.narpassword.javafx;

/*
NARPassword for Java - Application to generate a non-random password
Copyright (C) 2011 Hugues Johnson

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; version 2.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software 
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

import com.huguesjohnson.narpas.Narpas;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Hugues Johnson
 */
public class NARPasswordJavaFX extends Application{
    private PasswordField fieldPassPhrase;
    private TextField fieldPasswordName;
    private TextField fieldPassword;
    private Button buttonCopy;
    private CheckBox checkLowerCase;
    private CheckBox checkUpperCase;
    private CheckBox checkNumbers;
    private CheckBox checkSpecialCharacters;
    private Slider sliderPasswordLength;
    
    public static void main(String[] args){
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
        GridPane grid=new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);

        //Pass phrase
        HBox boxPassPhrase=new HBox();
        Label labelPassPhrase=new Label("Pass Phrase: ");
        labelPassPhrase.setPrefWidth(100);
        labelPassPhrase.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPassPhrase=new PasswordField();
        this.fieldPassPhrase.setPrefColumnCount(24);
        this.fieldPassPhrase.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override 
            public void handle(KeyEvent t){
                generatePassword();
            }
        });        
        boxPassPhrase.getChildren().addAll(labelPassPhrase,this.fieldPassPhrase);
        grid.add(boxPassPhrase,0,0);

        //Pass phrase hint
        HBox boxPassPhraseHint=new HBox();
        Label labelPassPhraseHintSpacer=new Label(" ");
        labelPassPhraseHintSpacer.setPrefWidth(100);
        Label labelPassPhraseHint=new Label("i.e.'I like pie' or 'Please no more California songs'");
        boxPassPhraseHint.getChildren().addAll(labelPassPhraseHintSpacer,labelPassPhraseHint);
        grid.add(boxPassPhraseHint,0,1);
        
        //Password name
        HBox boxPasswordName=new HBox();
        Label labelPasswordName=new Label("Password Name: ");
        labelPasswordName.setPrefWidth(100);
        labelPasswordName.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPasswordName=new TextField();
        this.fieldPasswordName.setPrefColumnCount(24);
        this.fieldPasswordName.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override 
            public void handle(KeyEvent t){
                generatePassword();
            }
        });           
        boxPasswordName.getChildren().addAll(labelPasswordName,this.fieldPasswordName);
        grid.add(boxPasswordName,0,2);

        //Password name hint
        HBox boxPasswordNameHint=new HBox();
        Label labelPasswordNameHintSpacer=new Label(" ");
        labelPasswordNameHintSpacer.setPrefWidth(100);
        Label labelPasswordNameHint=new Label("i.e.'Facebook' or 'yourname@gmail.com'");
        boxPasswordNameHint.getChildren().addAll(labelPasswordNameHintSpacer,labelPasswordNameHint);
        grid.add(boxPasswordNameHint,0,3);
        
        //Password
        HBox boxPassword=new HBox();
        Label labelPassword=new Label("Password: ");
        labelPassword.setPrefWidth(100);
        labelPassword.setAlignment(Pos.BASELINE_RIGHT);
        this.fieldPassword=new TextField();
        this.fieldPassword.setPrefColumnCount(20);
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
        grid.add(boxPassword,0,4);
        
        //Password options
        HBox boxPasswordOptions=new HBox();
        Label labelPasswordOptions=new Label("Password Options");
        boxPasswordOptions.getChildren().addAll(labelPasswordOptions);
        grid.add(boxPasswordOptions,0,5);
        
        //Lowercase checkbox
        this.checkLowerCase=new CheckBox("Use lower case characters (a-z)");
        this.checkLowerCase.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t) {
                generatePassword();
            }
        });
        this.checkLowerCase.setSelected(true);
        grid.add(this.checkLowerCase,0,6);
        
        //Uppercase checkbox
        this.checkUpperCase=new CheckBox("Use upper case characters (A-Z)");
        this.checkUpperCase.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t) {
                generatePassword();
            }
        });
        this.checkUpperCase.setSelected(true);
        grid.add(this.checkUpperCase,0,7);

        //Numbers checkbox
        this.checkNumbers=new CheckBox("Use numbers (0-9)");
        this.checkNumbers.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t) {
                generatePassword();
            }
        });
        this.checkNumbers.setSelected(true);
        grid.add(this.checkNumbers,0,8);

        //Special characters checkbox
        this.checkSpecialCharacters=new CheckBox("Use special characters (!@#$%^&*-=+:;?,.)");
        this.checkSpecialCharacters.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent t) {
                generatePassword();
            }
        });
        this.checkSpecialCharacters.setSelected(true);
        grid.add(this.checkSpecialCharacters,0,9);
        
        //Password length
        HBox boxPasswordLength=new HBox();
        Label labelPasswordLength=new Label("Password Length: ");
        labelPasswordLength.setPrefWidth(100);
        labelPasswordLength.setAlignment(Pos.BASELINE_RIGHT);
        this.sliderPasswordLength=new Slider();
        this.sliderPasswordLength.setPrefWidth(260);
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
        grid.add(boxPasswordLength,0,10);

        //setup main windows
        Scene scene=new Scene(grid);
        primaryStage.setTitle("NARPassword for JavaFX v1.0");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
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
        }
        this.fieldPassword.setText(password);
    }
}
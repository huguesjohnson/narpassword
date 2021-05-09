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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class NARPasswordJavaFX extends Application{
    private final static String DEFAULT_LOCALE="eng";
    private final static String BUNDLE_BASE="com.huguesjohnson.narpassword.javafx.bundles.narpassword_";
	private static String savePath;
    
	public static void main(String[] args){
    	//check command line
        if((args!=null)&&(args.length>0)){
        	for(int i=0;i<args.length;i++){
        		try{
        			if(args[i].contains("=")){
        				String[] split=args[i].split("=");
        				if(split.length==2){
        					if(split[0].toLowerCase().equals("--passwordlist")){
            					savePath=split[1];
            				}else{
            					throw(new Exception());
            				}
        				}else{
            				throw(new Exception());
            			}
            		}
        		}catch(Exception x){
        			System.out.println("Invalid command line argument: "+args[i]);
        		}
        	}
        }		
        launch(args);
	}
	
    @Override
    public void start(Stage stage) throws Exception{
        ResourceBundle resources=null;
        String locale=Locale.getDefault().getISO3Language();
        String bundleName=BUNDLE_BASE+locale;
        try{
            resources=ResourceBundle.getBundle(bundleName);
        }catch(MissingResourceException mrx){ 
            resources=ResourceBundle.getBundle(BUNDLE_BASE+DEFAULT_LOCALE);
        }
        FXMLLoader loader=new FXMLLoader(getClass().getResource("NARPasswordFX.fxml"),resources);
        Scene scene=new Scene(loader.load());        
        NARPasswordJavaFXController controller=(NARPasswordJavaFXController)loader.getController();
        try{
            stage.getIcons().add(new Image(NARPasswordJavaFX.class.getResourceAsStream("narpas-icon-16.png"))); 
        }catch(Exception x){/* not implemented - just here to prevent application from crashing if for some reason the icon can't be loaded */}
        stage.setScene(scene);
        stage.setTitle(resources.getString("app_title"));
        stage.show();
        if(savePath!=null){
            controller.setSavePath(savePath);
        	controller.showSaveLoadDialog(NARPasswordJavaFXSaveLoadController.SaveDialogMode.LOAD);
        }
    }	
}
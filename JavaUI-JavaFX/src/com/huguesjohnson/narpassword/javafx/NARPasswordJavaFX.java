/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

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
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

//I will eventually pull this and other related utility classes into a separate library

package com.huguesjohnson.narpassword.javafx;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class util{

	//this exists because JavaFX distribution is a total nightmare filled with misery and suffering
	public static void drawButtonImageIfNotLoadedFromFXML(Button b,String imgName,Class<?> c){
		boolean needToFix=true;
		if(b.getGraphic()!=null){
			ImageView iv=(ImageView)b.getGraphic();
			if(iv!=null){
				Image i=iv.getImage();
				if(i!=null){
					needToFix=!((i.getWidth()>0.0D)&&(i.getHeight()>0.0D));
				}
			}
        }
		if(needToFix){
			b.setGraphic(new ImageView(new Image(c.getResourceAsStream(imgName))));
		}
	}
	
}
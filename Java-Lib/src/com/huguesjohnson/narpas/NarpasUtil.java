/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.util.ArrayList;
import java.util.List;

import com.huguesjohnson.dubbel.util.StringComparator;

/* 
 * ...because there's nothing I enjoy more than static single-purpose utility functions
 * These are things that aren't used for password generation.
*/
public abstract class NarpasUtil{
	public final static String DEFAULT_NO_CATEGORY="[Unsorted]";
	
	public static List<String> getAllCategories(List<PasswordSetting> list){
		List<String> categories=new ArrayList<String>();
		for(PasswordSetting ps:list){
			String c=ps.getCategory();
			if((c==null)||(c.length()<1)){
				c=DEFAULT_NO_CATEGORY;
			}
			if(!categories.contains(c)){
				categories.add(c);
			}
		}
		categories.sort(new StringComparator());
		return(categories);
	}
}
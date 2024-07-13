/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.huguesjohnson.dubbel.util.StringComparator;

/* 
 * ...because there's nothing I enjoy more than static single-purpose utility functions
 * These are things that aren't used for password generation.
*/
public abstract class NarpasUtil{
	public final static String DEFAULT_NO_CATEGORY="[Unsorted]";
	public final static String DEFAULT_ALL_CATEGORY="";
	
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
	
	//fill in fields that are missing from v1 passwords
	//the method could be void(), just being explicit that the list is being changed
	public static List<PasswordSetting> prepV2Migrate(List<PasswordSetting> list){
		long lastUsed=System.currentTimeMillis();
		for(PasswordSetting ps:list){
			String c=ps.getCategory();
			if((c==null)||(c.length()<1)){
				ps.setCategory(DEFAULT_NO_CATEGORY);
			}
			String uuid=ps.getUuid();
			if((uuid==null)||(uuid.length()<1)){
				ps.setUuid(UUID.randomUUID().toString());
			}
			if(ps.getLastUsed()<=0L){
				ps.setLastUsed(lastUsed);
			}
		}
		return(list);
	}
}
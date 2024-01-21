/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.util.Comparator;
import java.util.Date;

public class PasswordSettingDateComparator implements Comparator<PasswordSetting>{
	@Override
	public int compare(PasswordSetting ps0,PasswordSetting ps1){
		Date d0=ps0.getLastUsed();
		Date d1=ps1.getLastUsed();
		if(d0==null){
			if(d1==null){
				return(0);
			}
			return(-1);
		}
		if(d1==null){
			return(1);
		}
		if(d0.equals(d1)){
			return(ps0.getPasswordName().compareToIgnoreCase(ps1.getPasswordName()));
		}
		return(d0.compareTo(d1));
	}
}
/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.util.Comparator;

public class PasswordSettingNameComparator implements Comparator<PasswordSetting>{
	boolean reverse=false;

	public PasswordSettingNameComparator(boolean reverse){
		this.reverse=reverse;
	}
	
	@Override
	public int compare(PasswordSetting ps0,PasswordSetting ps1){
		int compare=ps0.getPasswordName().compareToIgnoreCase(ps1.getPasswordName());
		if(this.reverse){
			return(-1*compare);
		}
		return(compare);
	}
}
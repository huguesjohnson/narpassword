/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.util.Comparator;

public class PasswordSettingDateComparator implements Comparator<PasswordSetting>{
	boolean reverse=false;
	
	public PasswordSettingDateComparator(boolean reverse){
		this.reverse=reverse;
	}
	
	@Override
	public int compare(PasswordSetting ps0,PasswordSetting ps1){
		//want the opposite of default Long compare - higher values sorted first
		int compare=Long.compare(ps0.getLastUsed(),ps1.getLastUsed());
		if(reverse){
			return(compare);
		}
		return(-1*compare);
	}
}
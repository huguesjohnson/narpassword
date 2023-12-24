/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.util.Comparator;

public class PasswordSettingComparator implements Comparator<PasswordSetting>{
	@Override
	public int compare(PasswordSetting ps0,PasswordSetting ps1){
		return(ps0.getPasswordName().compareToIgnoreCase(ps1.getPasswordName()));
	}
}
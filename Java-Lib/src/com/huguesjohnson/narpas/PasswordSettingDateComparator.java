/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.util.Comparator;

public class PasswordSettingDateComparator implements Comparator<PasswordSetting>{
	@Override
	public int compare(PasswordSetting ps0,PasswordSetting ps1){
		return(Long.compare(ps0.getLastUsed(),ps1.getLastUsed()));
	}
}
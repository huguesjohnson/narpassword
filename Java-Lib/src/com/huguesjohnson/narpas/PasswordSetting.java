/*
NARPassword for Java - Application to generate a non-random password
Copyright (C) 2011-2020 Hugues Johnson

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

package com.huguesjohnson.narpas;

import java.io.Serializable;

public class PasswordSetting implements Serializable{
	private static final long serialVersionUID=666;
	private String passwordName;
	private boolean optionUseLCase;
	private boolean optionUseUCase;
	private boolean optionUseNumbers;
	private boolean optionUseSChars;
	private int	passwordLength;
	private String passwordNotes;
	
	/* for the purposes of searching/sorting/comparing we only care about the name matching */

	@Override
	public int hashCode(){
		return(this.passwordName.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if(this==obj){return(true);};
		if(obj==null){return(false);};
		if(getClass()!=obj.getClass()){return(false);}
		PasswordSetting compareToObj=(PasswordSetting)obj;
		if((this.passwordName==null)&&(compareToObj.passwordName!=null)){return(false);}
		return(this.getPasswordName().equals(compareToObj.getPasswordName()));
	}	
	
	/* autogenerated code below */
	
	@Override
	public String toString() {
		return passwordName;
	}

	public String getPasswordName() {
		return passwordName;
	}

	public void setPasswordName(String passwordName) {
		this.passwordName = passwordName;
	}
	public boolean isOptionUseLCase() {
		return optionUseLCase;
	}
	public void setOptionUseLCase(boolean optionUseLCase) {
		this.optionUseLCase = optionUseLCase;
	}
	public boolean isOptionUseUCase() {
		return optionUseUCase;
	}
	public void setOptionUseUCase(boolean optionUseUCase) {
		this.optionUseUCase = optionUseUCase;
	}
	public boolean isOptionUseNumbers() {
		return optionUseNumbers;
	}
	public void setOptionUseNumbers(boolean optionUseNumbers) {
		this.optionUseNumbers = optionUseNumbers;
	}
	public boolean isOptionUseSChars() {
		return optionUseSChars;
	}
	public void setOptionUseSChars(boolean optionUseSChars) {
		this.optionUseSChars = optionUseSChars;
	}
	public int getPasswordLength() {
		return passwordLength;
	}
	public void setPasswordLength(int passwordLength) {
		this.passwordLength = passwordLength;
	}
	public String getPasswordNotes() {
		return passwordNotes;
	}
	public void setPasswordNotes(String passwordNotes) {
		this.passwordNotes = passwordNotes;
	}

	public PasswordSetting(String passwordName, boolean optionUseLCase, boolean optionUseUCase,
			boolean optionUseNumbers, boolean optionUseSChars, int passwordLength, String passwordNotes) {
		super();
		this.passwordName = passwordName;
		this.optionUseLCase = optionUseLCase;
		this.optionUseUCase = optionUseUCase;
		this.optionUseNumbers = optionUseNumbers;
		this.optionUseSChars = optionUseSChars;
		this.passwordLength = passwordLength;
		this.passwordNotes = passwordNotes;
	}

	public PasswordSetting(String passwordName, boolean optionUseLCase, boolean optionUseUCase,
			boolean optionUseNumbers, boolean optionUseSChars, int passwordLength) {
		super();
		this.passwordName = passwordName;
		this.optionUseLCase = optionUseLCase;
		this.optionUseUCase = optionUseUCase;
		this.optionUseNumbers = optionUseNumbers;
		this.optionUseSChars = optionUseSChars;
		this.passwordLength = passwordLength;
	}
}

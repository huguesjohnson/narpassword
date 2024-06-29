/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas;

public enum CharacterSetArrayIndex{
	LCHARS(0),
	UCHARS(1),
	NUMS(2),
	SCHARS(3),
	EXTCHARS(4);

	private final int value;
	CharacterSetArrayIndex(final int value){this.value=value;} 
	public int getValue(){return(this.value);};
	public CharacterSetArrayIndex getCharacterSetArrayIndex(int index){
		if(index==CharacterSetArrayIndex.EXTCHARS.getValue()){return(CharacterSetArrayIndex.EXTCHARS);}
		if(index==CharacterSetArrayIndex.SCHARS.getValue()){return(CharacterSetArrayIndex.SCHARS);}
		if(index==CharacterSetArrayIndex.NUMS.getValue()){return(CharacterSetArrayIndex.NUMS);}
		if(index==CharacterSetArrayIndex.UCHARS.getValue()){return(CharacterSetArrayIndex.UCHARS);}
		return(CharacterSetArrayIndex.LCHARS);//default
	}
}
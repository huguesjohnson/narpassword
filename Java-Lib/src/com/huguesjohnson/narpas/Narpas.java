/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.huguesjohnson.dubbel.util.ArrayUtil;
import com.huguesjohnson.dubbel.util.ChecksumUtil;
import com.huguesjohnson.dubbel.util.HashUtil;

/*
 * The first goal of all this is to produce repeatable passwords from:
 * -A passphrase (akin to master password)
 * -Password settings
 *  -Password name
 *  -Password length
 *  -Password character sets
 *  -Password version
 * Changing any of these cause significant changes to the resulting password.
 * 
 * The second goal is to create passwords that can not be reversed back to the passphrase.
 * If a password generated by this was exposed an attacker would know:
 *  -Password length
 *  -Password character sets selected
 * An attacker could also reasonably guess the password name.
 * 
 * Of course they would first have to know this was the tool used to generate the password opposed to any other one.
 * The main weakness of the original algorithm is that it is really easy to identify passwords created by it.
 * If all character sets are selected then every block of 4 characters contains one element from each set.
 * For example an 8 character password like a1B#2*cD is two blocks of 4 characters -> a1B# 2*cD.
 * The whole password is blocks of 4 like this.
 * The original algorithm also only supported password lengths that were multiples of 8.
 * I don't recall why I did that. 
 * There are many sites with dumb restrictions like 12 character maximums. 
 * A site with a 12 character maximum is definitely storing your password in plain text of course.
 * Anyway, this means the original algorithm could only create an 8 character password for that site.
 * In the very unlikely chance this site hashed passwords, an 8 character one could be cracked much faster than a 12 character one.
 * 5 minutes vs 226 years at the time this comment was made.
 * That is a very glaring weakness in the original algorithm.
 * 
 * The V2 algorithm addresses these weaknesses by:
 * -Changing character distribution to eliminate the previous block pattern.
 * -Allowing passwords of any length between 4-128 characters (4 to allow support for PINs).
 * -Character weighting so each password doesn't contain an equal number from each character set.
 * -Allowing passwords to contain extended ASCII characters (>127).
 * -Adding a password version option that also changes the password.
 * -Advanced settings to help with sites that have dumb password rules.
 * 	-Special character limits.
 *  -Disallow repeat characters, maybe not a dumb rule, just an odd one.
 * 
 * V3 algorithm will address whatever glaring weakness there is in V2 once I figure it out.
 */
public abstract class Narpas{
    
	public static class Constants{
        public static final char[] lchars=new char[] { 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
        public static final char[] uchars=new char[] { 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
        public static final char[] nums=new char[] { '0','1','2','3','4','5','6','7','8','9' };
        public static final char[] schars=new char[] { '!','@','#','$','%','^','&','*','-','=','+',':',';','?',',','.' };
        public static final char[] extchars=new char[] { 'À','Á','Â','Ã','Ä','Å','Æ','Ç','È','É','Ê','Ë','Ì','Í','Î','Ï','Ð','Ñ','Ò','Ó','Ô','Õ','Ö','Ø','Ù','Ú','Û','Ü','Ý','Þ','ß','à','á','â','ã','ä','å','æ','ç','è','é','ê','ë','ì','í','î','ï','ð','ñ','ò','ó','ô','õ','ö','÷','ø','ù','ú','û','ü','ý','þ','ÿ' };
        public static final int MIN_LENGTH=4;
        public static final int MAX_LENGTH=128;
        public static final int CURRENT_ALGORITHM_VERSION=2;
	}
	
	/* This is deprecated in favor of HashUtil.
	 * This implementation hides errors and illustrates why MD5 was a bad choice with the padding.
	 * Changing either breaks v1 password generation. */
    static MessageDigest md5Hash=null;
    @Deprecated 
    public static String computeHash(String input,int minLength){
        if(md5Hash==null){
            try{
				md5Hash=MessageDigest.getInstance("MD5");
			}catch(NoSuchAlgorithmException e){
				e.printStackTrace();
			}
        }
        StringBuffer sBuilder=new StringBuffer();
        while(sBuilder.length()<minLength){
            byte[] data;
            if(sBuilder.length()==0){
                data=md5Hash.digest(input.getBytes());
            }
            else{
                data=md5Hash.digest(sBuilder.toString().getBytes());
            }
            for(int i=0;i<data.length;i++){
            	int di=data[i]&0xff; 
            	if(di<16){ sBuilder.append("0"); } 
            	sBuilder.append(Integer.toHexString(di));
            }
        }
        return(sBuilder.toString());
    }
    
    /*
     * Builds the string used for the password settings hash.
     * Adding entropy so changes to a password setting forces a different hash.
     * Public for unit testing.
     * Could throw an exception if I royally messed up.
     */
    public static String passwordSettingHashString(PasswordSetting ps) throws Exception{
    	int settingsPad=ChecksumUtil.subBytes(ps.getPasswordVersion().getBytes());
    	if(settingsPad<0){
    		settingsPad=Math.abs(settingsPad);
    		settingsPad^=0;
    	}
    	if(ps.isOptionAllowDuplicateCharacters()){settingsPad^=1;}
    	if(ps.isOptionUseLCase()){settingsPad^=2;}
    	if(ps.isOptionUseUCase()){settingsPad^=4;}
    	if(ps.isOptionUseNumbers()){settingsPad^=8;}
    	if(ps.isOptionUseExtChars()){settingsPad^=16;}
    	if(ps.isOptionUseSChars()){settingsPad^=32;}
    	char[] limitSpecialChars=ps.getLimitSpecialChars();
    	if((limitSpecialChars!=null)&&(limitSpecialChars.length>0)){
        	settingsPad^=64;
    	} 
    	StringBuilder sb=new StringBuilder(ps.getPasswordName());
    	String padToString=Integer.toString(settingsPad);
    	for(int i=0;i<padToString.length();i++){
        	int insertIndex=ArrayUtil.pickIndex(settingsPad,sb.length());
        	sb.insert(insertIndex,padToString.charAt(i));
    	}
    	sb.append(ps.getPasswordVersion());
    	return(sb.toString());
    }
    
	/*
	 * These are not exceptions because these are all things that can be toggled in the UI vs something breaking during password generation
	 * The intent is the UI would only be catching fatal problems with generating a password.
	 * Seeing a blank string in the password name is a perfectly good indicator that the settings are wrong.
	 * Blank password means "you need to fix your password".
	 * Exception means "I personally messed up in some way you can not fix".
	 */
    public static boolean validate(String passPhrase,PasswordSetting settings){
    	//check if passphrase or password name is null
    	if((passPhrase==null)||(settings==null)||(settings.getPasswordName()==null)){
            return(false);
    	}
    	//check if passphrase or password name is blank
        if((passPhrase.length()<1)||(settings.getPasswordName().length()<1)){
            return(false);
        }
        //passphrase and password name can't be the same
        if(passPhrase.equals(settings.getPasswordName())){
            return(false);
        }
        //at least one character set has to be selected
        int algorithmVersion=settings.getAlgorithmVersion();
        if((!settings.isOptionUseLCase())&&(!settings.isOptionUseNumbers())&&(!settings.isOptionUseSChars())&&(!settings.isOptionUseUCase())){
            if(algorithmVersion>1){
            	if(!settings.isOptionUseExtChars()){
                	return(false);
            	}        	
            }else{
            	return(false);
            }
        }
        //check for valid algorithm version
        if((algorithmVersion>Constants.CURRENT_ALGORITHM_VERSION)||(algorithmVersion<0)){
            return(false);
        }
        //check for valid password length
        if(algorithmVersion==Constants.CURRENT_ALGORITHM_VERSION){
            int passwordLength=settings.getPasswordLength();
            if((passwordLength<Constants.MIN_LENGTH)||(settings.getPasswordLength()>Constants.MAX_LENGTH)){
                return(false);
            }
        }
        return(true);
    }
    
    public static String generatePassword(String passPhrase,PasswordSetting settings) throws Exception{
    	if(!validate(passPhrase,settings)){
            return("");
    	}
        //ok, validations passed
    	if(settings.getAlgorithmVersion()<=1){
    		return(generatePassword(
    				passPhrase,
    				settings.getPasswordName(),
    				settings.isOptionUseLCase(),
    				settings.isOptionUseUCase(),
    				settings.isOptionUseNumbers(),
    				settings.isOptionUseSChars(),
    				settings.getPasswordLength()));
    	}else if(settings.getAlgorithmVersion()==2){
    		return(generatePasswordV2(passPhrase,settings));
    	}
    	return("");
    }
    
    //original (v1) algorithm
    public static String generatePassword(String passPhrase,String passwordName,boolean useLCase,boolean useUCase,boolean useNumbers,boolean useSpecialCharacters,int basePasswordLength)
    {
        //validations
        if((passPhrase.length()<1)||(passwordName.length()<1))
        {
            return("");
        }
        if(passPhrase.equals(passwordName))
        {
            return("");
        }
        if((!useLCase)&&(!useUCase)&&(!useNumbers)&&(!useSpecialCharacters))
        {
            return("");
        }

        int passwordLength=8*basePasswordLength;

        //compute hashcodes
        String phraseHash=computeHash(passPhrase,passwordLength);
        byte[] phraseBytes=phraseHash.getBytes();
        String nameHash=computeHash(passwordName,passwordLength);
        byte[] nameBytes=nameHash.getBytes();

        byte[] xorBytes=null;
        //compute array order
        try{
        	xorBytes=ArrayUtil.xorBytes(phraseBytes,nameBytes);
        }catch(Exception x){
        	return("");
        }

        //compute checksums which translate to starting points in hashCodes
        int selectedCharOffset=0;
        if(useLCase) { selectedCharOffset=selectedCharOffset^1; }
        if(useUCase) { selectedCharOffset=selectedCharOffset^2; }
        if(useNumbers) { selectedCharOffset=selectedCharOffset^4; }
        if(useSpecialCharacters) { selectedCharOffset=selectedCharOffset^8; }
        int startOffset=basePasswordLength*selectedCharOffset;
        int phraseStart=ChecksumUtil.xorBytes(passPhrase.getBytes())+startOffset;
        int phraseIndex=0;
        try{phraseIndex=ArrayUtil.pickIndex(phraseStart,phraseHash.length());}catch(Exception x){/* do nothing, this is all for backwards compatability */}
        int nameStart=ChecksumUtil.xorBytes(passwordName.getBytes())+startOffset;
        int nameIndex=0;
        try{nameIndex=ArrayUtil.pickIndex(nameStart,phraseHash.length());}catch(Exception x){/* do nothing, this is all for backwards compatability */}
        //other stuff used to build the password
        int arrayIndex=basePasswordLength;
        ArrayList<Character> lowerCase=new ArrayList<Character>();
        ArrayList<Character> upperCase=new ArrayList<Character>();
        ArrayList<Character> numbers=new ArrayList<Character>();
        ArrayList<Character> specialChars=new ArrayList<Character>();
        ArrayList<Integer> arrayOrder=new ArrayList<Integer>();

        StringBuilder password=new StringBuilder();
        while(password.length()<passwordLength)
        {
            //pick the next array to select characters from
            if(arrayOrder.size()==0)
            {
                if(useLCase) { arrayOrder.add(Integer.valueOf(0)); }
                if(useUCase) { arrayOrder.add(Integer.valueOf(1)); }
                if(useNumbers) { arrayOrder.add(Integer.valueOf(2)); }
                if(useSpecialCharacters) { arrayOrder.add(Integer.valueOf(3)); }
            }
            int pickArray=0;
            try{pickArray=ArrayUtil.pickIndex(xorBytes[arrayIndex],arrayOrder.size());}catch(Exception x){/* do nothing, this is all for backwards compatability */}
            int nextArray=arrayOrder.get(pickArray).intValue();
            arrayOrder.remove(pickArray);

            //pick the next character for the password
            int nextIndex=phraseBytes[phraseIndex]*nameBytes[nameIndex];
            if(nextArray==0) //lower case
            {
                if(lowerCase.size()==0)
                {
                	ArrayUtil.fillArrayList(lowerCase,Narpas.Constants.lchars);
                }
                int lcharIndex=0;
                try{lcharIndex=ArrayUtil.pickIndex(nextIndex,lowerCase.size());}catch(Exception x){/* do nothing, this is all for backwards compatability */}
                password.append(lowerCase.get(lcharIndex));
                lowerCase.remove(lcharIndex);
            }
            else if(nextArray==1) //upper case
            {
                if(upperCase.size()==0)
                {
                	ArrayUtil.fillArrayList(upperCase,Narpas.Constants.uchars);
                }
                int ucharIndex=0;
                try{ucharIndex=ArrayUtil.pickIndex(nextIndex,upperCase.size());}catch(Exception x){/* do nothing, this is all for backwards compatability */}
                password.append(upperCase.get(ucharIndex));
                upperCase.remove(ucharIndex);
            }
            else if(nextArray==2) //numbers
            {
                if(numbers.size()==0)
                {
                	ArrayUtil.fillArrayList(numbers,Narpas.Constants.nums);
                }
                int numIndex=0;
                try{numIndex=ArrayUtil.pickIndex(nextIndex,numbers.size());}catch(Exception x){/* do nothing, this is all for backwards compatability */}
                password.append(numbers.get(numIndex));
                numbers.remove(numIndex);
            }
            else //special characters
            {
                if(specialChars.size()==0)
                {
                	ArrayUtil.fillArrayList(specialChars,Narpas.Constants.schars);
                }
                int scharIndex=0;
                try{scharIndex=ArrayUtil.pickIndex(nextIndex,specialChars.size());}catch(Exception x){/* do nothing, this is all for backwards compatability */}
                password.append(specialChars.get(scharIndex));
                specialChars.remove(scharIndex);
            }

            //increment indexes
            phraseIndex++;
            if(phraseIndex==phraseBytes.length) { phraseIndex=0; }
            nameIndex++;
            if(nameIndex==nameBytes.length) { nameIndex=0; }
            arrayIndex++;
            if(arrayIndex==xorBytes.length) { arrayIndex=0; }
        }
        return(password.toString());
    }

    private static String generatePasswordV2(String passPhrase,PasswordSetting settings) throws Exception{
        int passwordLength=settings.getPasswordLength();
    	//remove is false if duplicate characters are allowed
    	boolean remove=!settings.isOptionAllowDuplicateCharacters();
    	//phraseHash is used to pick indexes in character arrays
    	String phraseHash=HashUtil.computeHashSHA3512(passPhrase);
        byte[] phraseBytes=phraseHash.getBytes();
        int phraseBytesSum=ChecksumUtil.sumBytes(phraseBytes);
    	//phraseHash is also used to pick indexes in character arrays
        String nameHash=HashUtil.computeHashSHA3512(passwordSettingHashString(settings));
        byte[] nameBytes=nameHash.getBytes();
        int namesBytesSum=ChecksumUtil.sumBytes(nameBytes);
        //xor of the two is used to pick where characters are inserted into the password
        byte[] xorBytes=ArrayUtil.xorBytes(phraseBytes,nameBytes);
        int xorBytesSum=ChecksumUtil.sumBytes(xorBytes);
        //used to pick starting offsets
        String versionHash=HashUtil.computeHashSHA3512(settings.getPasswordVersion());
        int versionBytesSum=ChecksumUtil.sumBytes(versionHash.getBytes());
        //weights to choose next array
        double[] nextArrayWeights=new double[CharacterSetArrayIndex.EXTCHARS.getValue()+1];
        for(int i=0;i<=CharacterSetArrayIndex.EXTCHARS.getValue();i++){nextArrayWeights[i]=-1.0d;}
        //rather than start at zero, vary based on options selected
        int startOffset=passwordLength;
        if(settings.isOptionUseLCase()){
        	startOffset=startOffset^1;
        	nextArrayWeights[CharacterSetArrayIndex.LCHARS.getValue()]=settings.getWeight(CharacterSetArrayIndex.LCHARS);
        }
        if(settings.isOptionUseUCase()){
        	startOffset=startOffset^2;
        	nextArrayWeights[CharacterSetArrayIndex.UCHARS.getValue()]=settings.getWeight(CharacterSetArrayIndex.UCHARS);
        }
        if(settings.isOptionUseNumbers()){
        	startOffset=startOffset^4;
        	nextArrayWeights[CharacterSetArrayIndex.NUMS.getValue()]=settings.getWeight(CharacterSetArrayIndex.NUMS);
        }
        if(settings.isOptionUseSChars()){
        	startOffset=startOffset^8;
        	nextArrayWeights[CharacterSetArrayIndex.SCHARS.getValue()]=settings.getWeight(CharacterSetArrayIndex.SCHARS);
        }
        if(settings.isOptionUseExtChars()){
        	startOffset=startOffset^16;
        	nextArrayWeights[CharacterSetArrayIndex.EXTCHARS.getValue()]=settings.getWeight(CharacterSetArrayIndex.EXTCHARS);
        }
        if(settings.isOptionAllowDuplicateCharacters()){
        	startOffset=startOffset^32;
        }
        //all these offsets may not all be used
        int	lcaseOffset=startOffset+Math.abs(settings.getPasswordLength()*versionBytesSum);
        int	ucaseOffset=startOffset+Math.abs(settings.getPasswordLength()&versionBytesSum);
        int numOffset=startOffset+Math.abs(settings.getPasswordLength()+versionBytesSum);
        int scharOffset=startOffset+Math.abs(settings.getPasswordLength()|versionBytesSum);
        int extcharOffset=startOffset+Math.abs(settings.getPasswordLength()^versionBytesSum);
        //these are used to pick characters and indexes
        int phraseStart=phraseBytesSum+startOffset;
        int phraseIndex=ArrayUtil.pickIndex(phraseStart,phraseBytes.length);
        int nameStart=namesBytesSum+startOffset;
        int nameIndex=ArrayUtil.pickIndex(nameStart,nameBytes.length);
        int xorStart=xorBytesSum+startOffset;
        int xorIndex=ArrayUtil.pickIndex(xorStart,xorBytes.length);
        //arrays containing characters used in the password
        ArrayList<Character> lowerCase=new ArrayList<Character>();
        ArrayList<Character> upperCase=new ArrayList<Character>();
        ArrayList<Character> numbers=new ArrayList<Character>();
        ArrayList<Character> specialChars=new ArrayList<Character>();
        ArrayList<Character> extChars=new ArrayList<Character>();
        //list of positions to insert characters
        ArrayList<Integer> passwordIndexList=new ArrayList<Integer>();
        for(int i=0;i<passwordLength;i++){
        	passwordIndexList.add(Integer.valueOf(i));
        }
        //where the password will go
        char[] password=new char[passwordLength];
        //counters
        int currentLength=0;
        CharacterSetArrayIndex nextArray=CharacterSetArrayIndex.LCHARS;
        //loop to build the password
        while(currentLength<passwordLength){
            //pick index in the character array that is being used next
            int nextIndex=phraseBytes[phraseIndex]*nameBytes[nameIndex];
            //pick index that the next character will be added to the password
            int pickIndex=ArrayUtil.pickIndex(xorBytes[xorIndex],passwordIndexList.size());
            int passwordIndex=passwordIndexList.get(pickIndex);
            passwordIndexList.remove(pickIndex);
            //pick which array we are reading from next
            ArrayList<Character> nextList=lowerCase; //default
            char[] nextRefill=Narpas.Constants.lchars; //default
            boolean haveNext=false;
            while(!haveNext){
            	if((nextArray==CharacterSetArrayIndex.LCHARS)&&(settings.isOptionUseLCase())){
            		//reminder next list and next refill are lchar by default
            		haveNext=true;
            		nextIndex+=lcaseOffset;
            	}else if((nextArray==CharacterSetArrayIndex.UCHARS)&&(settings.isOptionUseUCase())){
            		haveNext=true;
                    nextList=upperCase;
                    nextRefill=Narpas.Constants.uchars;
            		nextIndex+=ucaseOffset;
            	}else if((nextArray==CharacterSetArrayIndex.NUMS)&&(settings.isOptionUseNumbers())){
            		haveNext=true;
                    nextList=numbers;
                    nextRefill=Narpas.Constants.nums;
            		nextIndex+=numOffset;
            	}else if((nextArray==CharacterSetArrayIndex.EXTCHARS)&&(settings.isOptionUseExtChars())){
            		haveNext=true;
                    nextList=extChars;
                    nextRefill=Narpas.Constants.extchars;
            		nextIndex+=extcharOffset;
            	}else if((nextArray==CharacterSetArrayIndex.SCHARS)&&(settings.isOptionUseSChars())){
            		haveNext=true;
                    nextList=specialChars;
            		char[] limitSpecialChars=settings.getLimitSpecialChars();
            		if((limitSpecialChars!=null)&&(limitSpecialChars.length>0)){
                        nextRefill=limitSpecialChars;
            		}else{
            			nextRefill=Narpas.Constants.schars;
            		}
            		nextIndex+=scharOffset;
            	}
            	//cycle to next array
            	if(!haveNext){
            		//getCharacterSetArrayIndex defaults to lchar
               		int nextArrayIndex=nextArray.getValue()+1;
               		nextArray=nextArray.getCharacterSetArrayIndex(nextArrayIndex);
            	}
            }
            //this can happen with wild version numbers
            if(nextIndex<0){
            	nextIndex=Math.abs(nextIndex);
            }
            password[passwordIndex]=ArrayUtil.pickChar(nextList,nextIndex,remove,nextRefill);
            //increment indexes
            currentLength++;
            phraseIndex++;
            if(phraseIndex>=phraseBytes.length){phraseIndex=0;}
            nameIndex++;
            if(nameIndex>=nameBytes.length){nameIndex=0;}
            xorIndex++;
            if(xorIndex>=xorBytes.length){xorIndex=0;}
            //pick the next array - right now "nextArray" is the current array
        	int nextArrayWeightIndex=nextArray.getValue();
            if(!remove){
            	//weighting formula doesn't work right when duplicate chars allowed
            	nextArrayWeights[nextArrayWeightIndex]=nextArrayWeights[nextArrayWeightIndex]*0.9d;
            }else{
            	nextArrayWeights[nextArrayWeightIndex]=nextArrayWeights[nextArrayWeightIndex]*((double)nextList.size()/(double)nextRefill.length);
            }
            //nextArray=ArrayUtil.maxElementIndex(nextArrayWeights);
            int nextArrayIndex=ArrayUtil.maxElementIndex(nextArrayWeights);
            nextArray=nextArray.getCharacterSetArrayIndex(nextArrayIndex);
        }
        return(new String(password));
    }
}
/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import com.huguesjohnson.dubbel.util.ChecksumUtils;

public abstract class Narpas{
    
	public static class Constants
    {
        public static final String[] lchars=new String[] { "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z" };
        public static final String[] uchars=new String[] { "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z" };
        public static final String[] nums=new String[] { "0","1","2","3","4","5","6","7","8","9" };
        public static final String[] schars=new String[] { "!","@","#","$","%","^","&","*","-","=","+",":",";","?",",","." };
    }

    public static int computeChecksum(String s)
    {
        byte[] b=s.getBytes();
        return(ChecksumUtils.xorBytes(b));
    }

    /* 
     * I can't recall why I implemented it this way.
     * I think in real-life I was working on a project with concurrency issues and over-engineered this.
     * The bracket style is like .NET so I think I know exactly which project it was now.
     */
    static MessageDigest md5Hash=null;
    public static String computeHash(String input,int minLength)
    {
        if(md5Hash==null)
        {
            try{
				md5Hash=MessageDigest.getInstance("MD5");
			}catch(NoSuchAlgorithmException e){
				e.printStackTrace();
			}
        }
        StringBuffer sBuilder=new StringBuffer();
        while(sBuilder.length()<minLength)
        {
            byte[] data;
            if(sBuilder.length()==0)
            {
                data=md5Hash.digest(input.getBytes());
            }
            else
            {
                data=md5Hash.digest(sBuilder.toString().getBytes());
            }
            for(int i=0;i<data.length;i++)
            {
            	int di=data[i]&0xff; 
            	if(di<16){ sBuilder.append("0"); } 
            	sBuilder.append(Integer.toHexString(di));
            }
        }
        return(sBuilder.toString());
    }

    public static String computeHashSHA3512(String input) throws NoSuchAlgorithmException{
    	MessageDigest md=MessageDigest.getInstance("SHA3-512");
        byte[] data=md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<data.length;i++){
        	int di=data[i]&0xff;
        	if(di<16){sb.append("0");} 
        	sb.append(Integer.toHexString(di));
        }
        return(sb.toString());
    }
    
    private static int pickIndex(int i,int max)
    {
        if(i>=max)
        {
            i=i-((int)Math.floor(i/max)*max);
        }
        return(i);
    }
    
    public static byte[] xorBytes(byte[] a,byte[] b) throws Exception{
    	int l=a.length;
    	if(l!=b.length){throw(new Exception("Arrays are different lengths. a.length="+l+"b.length="+b.length));};
		byte[] xor=new byte[l];
		for(int i=0;i<l;i++){
			xor[i]=(byte)((int)a[i]^(int)b[i]);
		}
		return(xor);
    }

    public static String generatePassword(String passPhrase,PasswordSetting settings){
    	if(settings.getVersion()<=1){
    		return(generatePassword(
    				passPhrase,
    				settings.getPasswordName(),
    				settings.isOptionUseLCase(),
    				settings.isOptionUseUCase(),
    				settings.isOptionUseNumbers(),
    				settings.isOptionUseSChars(),
    				settings.getPasswordLength()));
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
        	xorBytes=xorBytes(phraseBytes,nameBytes);
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
        int phraseStart=computeChecksum(passPhrase)+startOffset;
        int phraseIndex=pickIndex(phraseStart,phraseHash.length());
        int nameStart=computeChecksum(passwordName)+startOffset;
        int nameIndex=pickIndex(nameStart,phraseHash.length());

        //other stuff used to build the password
        int arrayIndex=basePasswordLength;
        ArrayList<String> lowerCase=new ArrayList<String>();
        ArrayList<String> upperCase=new ArrayList<String>();
        ArrayList<String> numbers=new ArrayList<String>();
        ArrayList<String> specialChars=new ArrayList<String>();
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
            int pickArray=pickIndex(xorBytes[arrayIndex],arrayOrder.size());
            int nextArray=arrayOrder.get(pickArray).intValue();
            arrayOrder.remove(pickArray);

            //pick the next character for the password
            int nextIndex=phraseBytes[phraseIndex]*nameBytes[nameIndex];
            if(nextArray==0) //lower case
            {
                if(lowerCase.size()==0)
                {
                	lowerCase=new ArrayList<String>(Arrays.asList(Narpas.Constants.lchars));
                }
                int lcharIndex=pickIndex(nextIndex,lowerCase.size());
                password.append(lowerCase.get(lcharIndex));
                lowerCase.remove(lcharIndex);
            }
            else if(nextArray==1) //upper case
            {
                if(upperCase.size()==0)
                {
                	upperCase=new ArrayList<String>(Arrays.asList(Narpas.Constants.uchars));
                }
                int ucharIndex=pickIndex(nextIndex,upperCase.size());
                password.append(upperCase.get(ucharIndex));
                upperCase.remove(ucharIndex);
            }
            else if(nextArray==2) //numbers
            {
                if(numbers.size()==0)
                {
                	numbers=new ArrayList<String>(Arrays.asList(Narpas.Constants.nums));
                }
                int numIndex=pickIndex(nextIndex,numbers.size());
                password.append(numbers.get(numIndex));
                numbers.remove(numIndex);
            }
            else //special characters
            {
                if(specialChars.size()==0)
                {
                	specialChars=new ArrayList<String>(Arrays.asList(Narpas.Constants.schars));
                }
                int scharIndex=pickIndex(nextIndex,specialChars.size());
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

}

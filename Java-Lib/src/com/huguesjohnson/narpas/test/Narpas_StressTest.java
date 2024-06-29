/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.huguesjohnson.dubbel.util.ArrayUtil;
import com.huguesjohnson.dubbel.util.StringDistance;
import com.huguesjohnson.narpas.CharacterSetArrayIndex;
import com.huguesjohnson.narpas.Narpas;
import com.huguesjohnson.narpas.PasswordSetting;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


public class Narpas_StressTest{
	/*
	 * Brute-force tests used during development of v2 changes.
	 * I understand and accept the pitfalls with using random strings to test.
	 * The goal of these is to measure average and worst performance with arbitrary strings.
	 */
	
	@Test 
	public void test_v2LengthEntropy(){
		int numPasses=100;
		int totalPasses=0;
		double minDifference=1.0d;
		String minDifferencePassword1="";
		String minDifferencePassword2="";
		String minDifferencePassPhrase="";
		String minDifferencePassName="";
		double averageDifference=0.0d;
		for(int i=0;i<numPasses;i++){
			String passPhrase=UUID.randomUUID().toString();
			String passswordName=UUID.randomUUID().toString();
			PasswordSetting settings=new PasswordSetting();
			settings.setPasswordName(passswordName);
			settings.setAlgorithmVersion(2);
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(false);//going with most common setting
			settings.setOptionUseNumbers(true);
			settings.setPasswordLength(4);
			for(int length=4;length<=127;length++){
				try{
					settings.setPasswordLength(length);
					String password1=Narpas.generatePassword(passPhrase,settings);
					settings.setPasswordLength(length+1);
					String password2=Narpas.generatePassword(passPhrase,settings);
					int ld=StringDistance.levenshteinDistance(password1,password2);
					double difference=((double)ld/(double)length);
					averageDifference+=difference;
					if(difference<minDifference){
						minDifference=difference;
						minDifferencePassword1=password1;
						minDifferencePassword2=password2;
						minDifferencePassPhrase=passPhrase;
						minDifferencePassName=passswordName;
					}
					totalPasses++;
				}catch(Exception x){
					x.printStackTrace();
					fail(x.getMessage());
				}
			}
		}
		//print results
		System.out.println("test_v2LengthEntropy\n------------------------------");
		System.out.println("averageDifference:"+(averageDifference/(double)totalPasses));
		System.out.println("minDifference:"+minDifference);
		System.out.println("minDifferencePassPhrase:"+minDifferencePassPhrase);
		System.out.println("minDifferencePassName:"+minDifferencePassName);
		System.out.println("minDifferencePassword1:"+minDifferencePassword1);
		System.out.println("minDifferencePassword2:"+minDifferencePassword2);
		System.out.println("");
	}	

	@Test 
	public void test_v2PasswordVersionEntropy(){
		int numPasses=100;
		int totalPasses=0;
		double minDifference=1.0d;
		String minDifferencePassword1="";
		String minDifferencePassword2="";
		String minDifferencePassPhrase="";
		String minDifferencePassName="";
		int minDifferenceVersion1=0;
		int minDifferenceVersion2=0;
		double averageDifference=0.0d;
		double worstDistance=0.0d;
		String worstDistancePassword1="";
		String worstDistancePassword2="";
		String worstDistancePassPhrase="";
		String worstDistancePassName="";
		int worstDistanceVersion1=0;
		int worstDistanceVersion2=0;
		double averageDistance=0.0d;
		Random r=new Random();
		for(int i=0;i<numPasses;i++){
			String passPhrase=UUID.randomUUID().toString();
			String passswordName=UUID.randomUUID().toString();
			PasswordSetting settings=new PasswordSetting();
			settings.setPasswordName(passswordName);
			settings.setAlgorithmVersion(2);
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(false);//going with most common setting
			settings.setOptionUseNumbers(true);
			settings.setPasswordLength(4);
			for(int length=4;length<=128;length++){
				try{
					int v1=r.nextInt();
					int v2=v1+1;
					if(r.nextBoolean()){v2-=2;}
					settings.setPasswordLength(length);
					settings.setPasswordVersion(Integer.toString(v1));
					String password1=Narpas.generatePassword(passPhrase,settings);
					settings.setPasswordVersion(Integer.toString(v2));
					String password2=Narpas.generatePassword(passPhrase,settings);
					int ld=StringDistance.levenshteinDistance(password1,password2);
					double difference=((double)ld/(double)length);
					averageDifference+=difference;
					if(difference<minDifference){
						minDifference=difference;
						minDifferencePassword1=password1;
						minDifferencePassword2=password2;
						minDifferencePassPhrase=passPhrase;
						minDifferencePassName=passswordName;
						minDifferenceVersion1=v1;
						minDifferenceVersion2=v2;
					}
					int distance=length-StringDistance.hammingDistance(password1,password2);
					averageDistance+=(double)distance;
					if(distance>worstDistance){
						worstDistance=distance;
						worstDistancePassword1=password1;
						worstDistancePassword2=password2;
						worstDistancePassPhrase=passPhrase;
						worstDistancePassName=passswordName;
						worstDistanceVersion1=v1;
						worstDistanceVersion2=v2;
					}					
					totalPasses++;
				}catch(Exception x){
					x.printStackTrace();
					fail(x.getMessage());
				}
			}
		}
		//print results
		System.out.println("test_v2PasswordVersionEntropy: Password difference\n------------------------------");
		System.out.println("averageDifference:"+(averageDifference/(double)totalPasses));
		System.out.println("minDifference:"+minDifference);
		System.out.println("minDifferencePassPhrase:"+minDifferencePassPhrase);
		System.out.println("minDifferencePassName:"+minDifferencePassName);
		System.out.println("minDifferenceVersion1:"+minDifferenceVersion1);
		System.out.println("minDifferenceVersion2:"+minDifferenceVersion2);
		System.out.println("minDifferencePassword1:"+minDifferencePassword1);
		System.out.println("minDifferencePassword2:"+minDifferencePassword2);
		System.out.println("");
		System.out.println("test_v2PasswordVersionEntropy: Password distance\n------------------------------");
		System.out.println("averageDistance:"+(averageDistance/(double)totalPasses));
		System.out.println("worstDistance:"+worstDistance);
		System.out.println("worstDistancePassPhrase:"+worstDistancePassPhrase);
		System.out.println("worstDistancePassName:"+worstDistancePassName);
		System.out.println("worstDistanceVersion1:"+worstDistanceVersion1);
		System.out.println("worstDistanceVersion2:"+worstDistanceVersion2);
		System.out.println("worstDistancePassword1:"+worstDistancePassword1);
		System.out.println("worstDistancePassword2:"+worstDistancePassword2);
		System.out.println("");
	}

	@Test 
	public void test_v2PasswordNameEntropy(){
		int numPasses=100;
		int totalPasses=0;
		double minDifference=1.0d;
		String minDifferencePassword1="";
		String minDifferencePassword2="";
		String minDifferencePassPhrase="";
		String minDifferencePasswordName1="";
		String minDifferencePasswordName2="";
		double averageDifference=0.0d;
		double worstDistance=0.0d;
		String worstDistancePassword1="";
		String worstDistancePassword2="";
		String worstDistancePassPhrase="";
		String worstDifferencePasswordName1="";
		String worstDifferencePasswordName2="";
		double averageDistance=0.0d;
		Random r=new Random();
		for(int i=0;i<numPasses;i++){
			String passPhrase=UUID.randomUUID().toString();
			PasswordSetting settings=new PasswordSetting();
			settings.setAlgorithmVersion(2);
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(false);//going with most common setting
			settings.setOptionUseNumbers(true);
			settings.setPasswordLength(4);
			for(int length=4;length<=128;length++){
				try{
					settings.setPasswordLength(length);
					String passwordName1=UUID.randomUUID().toString();
					settings.setPasswordName(passwordName1);
					String password1=Narpas.generatePassword(passPhrase,settings);
					StringBuilder sb=new StringBuilder(passwordName1);
					int changeIndex=r.nextInt(passwordName1.length()-1);
					char c=sb.charAt(changeIndex);
					c++;
					sb.setCharAt(changeIndex,c);
					String passwordName2=sb.toString();
					settings.setPasswordName(passwordName2);
					String password2=Narpas.generatePassword(passPhrase,settings);
					int ld=StringDistance.levenshteinDistance(password1,password2);
					double difference=((double)ld/(double)length);
					averageDifference+=difference;
					if(difference<minDifference){
						minDifference=difference;
						minDifferencePassword1=password1;
						minDifferencePassword2=password2;
						minDifferencePassPhrase=passPhrase;
						minDifferencePasswordName1=passwordName1;
						minDifferencePasswordName2=passwordName2;
					}
					int distance=length-StringDistance.hammingDistance(password1,password2);
					averageDistance+=(double)distance;
					if(distance>worstDistance){
						worstDistance=distance;
						worstDistancePassword1=password1;
						worstDistancePassword2=password2;
						worstDistancePassPhrase=passPhrase;
						worstDifferencePasswordName1=passwordName1;
						worstDifferencePasswordName2=passwordName2;
					}					
					totalPasses++;
				}catch(Exception x){
					x.printStackTrace();
					fail(x.getMessage());
				}
			}
		}
		//print results
		System.out.println("test_v2PasswordNameEntropy: Password difference\n------------------------------");
		System.out.println("averageDifference:"+(averageDifference/(double)totalPasses));
		System.out.println("minDifference:"+minDifference);
		System.out.println("minDifferencePassPhrase:"+minDifferencePassPhrase);
		System.out.println("minDifferencePasswordName1:"+minDifferencePasswordName1);
		System.out.println("minDifferencePasswordName2:"+minDifferencePasswordName2);
		System.out.println("minDifferencePassword1:"+minDifferencePassword1);
		System.out.println("minDifferencePassword2:"+minDifferencePassword2);
		System.out.println("");
		System.out.println("test_v2PasswordNameEntropy: Password distance\n------------------------------");
		System.out.println("averageDistance:"+(averageDistance/(double)totalPasses));
		System.out.println("worstDistance:"+worstDistance);
		System.out.println("worstDistancePassPhrase:"+worstDistancePassPhrase);
		System.out.println("worstDifferencePasswordName1:"+worstDifferencePasswordName1);
		System.out.println("worstDifferencePasswordName2:"+worstDifferencePasswordName2);
		System.out.println("worstDistancePassword1:"+worstDistancePassword1);
		System.out.println("worstDistancePassword2:"+worstDistancePassword2);
		System.out.println("");
	}	

	@Test 
	public void test_v2WeightEntropy(){
		Random r=new Random();
		int numPasses=100;
		int totalPasses=0;
		double minDifference=1.0d;
		String minDifferencePassword1="";
		String minDifferencePassword2="";
		String minDifferencePassPhrase="";
		String minDifferencePassName="";
		double averageDifference=0.0d;
		for(int i=0;i<numPasses;i++){
			String passPhrase=UUID.randomUUID().toString();
			String passswordName=UUID.randomUUID().toString();
			PasswordSetting settings=new PasswordSetting();
			settings.setPasswordName(passswordName);
			settings.setAlgorithmVersion(2);
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(true);
			settings.setOptionUseNumbers(true);
			//weight changes have, as expected, negligible impact to short passwords
			for(int length=32;length<=128;length++){
				try{
					settings.setWeight(CharacterSetArrayIndex.EXTCHARS,1.0d);
					settings.setWeight(CharacterSetArrayIndex.LCHARS,1.0d);
					settings.setWeight(CharacterSetArrayIndex.NUMS,1.0d);
					settings.setWeight(CharacterSetArrayIndex.SCHARS,1.0d);
					settings.setWeight(CharacterSetArrayIndex.UCHARS,1.0d);
					settings.setPasswordLength(length);
					String password1=Narpas.generatePassword(passPhrase,settings);
				    int rando=r.nextInt(6);
				    CharacterSetArrayIndex weightToChange=CharacterSetArrayIndex.EXTCHARS.getCharacterSetArrayIndex(rando);
				    settings.setWeight(weightToChange,0.9d);
					String password2=Narpas.generatePassword(passPhrase,settings);
					int ld=StringDistance.levenshteinDistance(password1,password2);
					double difference=((double)ld/(double)length);
					averageDifference+=difference;
					if(difference<minDifference){
						minDifference=difference;
						minDifferencePassword1=password1;
						minDifferencePassword2=password2;
						minDifferencePassPhrase=passPhrase;
						minDifferencePassName=passswordName;
					}
					totalPasses++;
				}catch(Exception x){
					x.printStackTrace();
					fail(x.getMessage());
				}
			}
		}
		//print results
		System.out.println("test_v2WeightEntropy\n------------------------------");
		System.out.println("averageDifference:"+(averageDifference/(double)totalPasses));
		System.out.println("minDifference:"+minDifference);
		System.out.println("minDifferencePassPhrase:"+minDifferencePassPhrase);
		System.out.println("minDifferencePassName:"+minDifferencePassName);
		System.out.println("minDifferencePassword1:"+minDifferencePassword1);
		System.out.println("minDifferencePassword2:"+minDifferencePassword2);
		System.out.println("");
	}	
	
	
	@Test 
	public void test_v2CharacterDistributionRandomSettings(){
		int numPasses=1000;
		int charCount=0;
		int[] charDist=new int[256];
		//not really needed but whatever
		for(int i=0;i<256;i++){charDist[i]=0;}
		Random r=new Random();
		for(int i=0;i<numPasses;i++){
			try{
				PasswordSetting settings=new PasswordSetting();
				settings.setAlgorithmVersion(2);
				settings.setOptionUseLCase(r.nextBoolean());
				settings.setOptionUseUCase(r.nextBoolean());
				settings.setOptionUseSChars(r.nextBoolean());
				settings.setOptionUseExtChars(r.nextBoolean());
				settings.setOptionUseNumbers(r.nextBoolean());
				int length=r.nextInt((128-4)+1)+4;
				settings.setPasswordLength(length);
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				settings.setPasswordName(UUID.randomUUID().toString());
				String passphrase=UUID.randomUUID().toString();
				//possible that all options are false
				if(Narpas.validate(passphrase,settings)){
					String password=Narpas.generatePassword(passphrase,settings);
					for(int j=0;j<password.length();j++){
						int index=password.charAt(j);
						charCount++;
						charDist[index]++;
					}
				}
			}catch(Exception x){
				x.printStackTrace();
				fail(x.getMessage());
			}
		}
		//print results
		System.out.println("test_v2CharacterDistributionRandomSettings\n------------------------------");
		System.out.println("Total characters counted:"+charCount);
		for(int i=0;i<256;i++){
			if(charDist[i]>0){//intent is to skip non-printing characters
				char c=(char)i;
				System.out.println(i+"["+c+"]="+charDist[i]);
			}
		}
		System.out.println("");
	}		

	@Test 
	public void test_v2CharacterDistributionFixedSettings(){
		int numPasses=1000;
		int charCount=0;
		int[] charDist=new int[256];
		//not really needed but whatever
		for(int i=0;i<256;i++){charDist[i]=0;}
		for(int i=0;i<numPasses;i++){
			try{
				PasswordSetting settings=new PasswordSetting();
				settings.setAlgorithmVersion(2);
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(true);
				settings.setOptionUseExtChars(true);
				settings.setOptionUseNumbers(true);
				int length=128;
				settings.setPasswordLength(length);
				settings.setPasswordVersion(Integer.toString(0));
				settings.setPasswordName(UUID.randomUUID().toString());
				String passphrase=UUID.randomUUID().toString();
				//possible that all options are false
				if(Narpas.validate(passphrase,settings)){
					String password=Narpas.generatePassword(passphrase,settings);
					for(int j=0;j<password.length();j++){
						int index=password.charAt(j);
						charCount++;
						charDist[index]++;
					}
				}
			}catch(Exception x){
				x.printStackTrace();
				fail(x.getMessage());
			}
		}
		//print results
		System.out.println("test_v2CharacterDistributionFixedSettings\n------------------------------");
		System.out.println("Total characters counted:"+charCount);
		for(int i=0;i<256;i++){
			if(charDist[i]>0){//intent is to skip non-printing characters
				char c=(char)i;
				System.out.println(i+"["+c+"]="+charDist[i]);
			}
		}
		System.out.println("");
	}
	
	/* Very similar to functional test called test_v2CharacterSets.
	 * Trying many more settings combinations though
	 * Also trying all password lengths (>8 at least)
	 */
	@Test
	public void test_v2CharacterSetsWithRandomValuesAllLengths(){
		try{
			Random r=new Random();
			//setup arrays to lookup characters
	        ArrayList<Character> lowerCase=new ArrayList<Character>();
	        ArrayList<Character> upperCase=new ArrayList<Character>();
	        ArrayList<Character> numbers=new ArrayList<Character>();
	        ArrayList<Character> specialChars=new ArrayList<Character>();
	        ArrayList<Character> limitSpecialChars=new ArrayList<Character>();
	        ArrayList<Character> extChars=new ArrayList<Character>();
	        char[] lsc={'{','}','`','[',']'};
	        ArrayUtil.fillArrayList(lowerCase,Narpas.Constants.lchars);
	        ArrayUtil.fillArrayList(upperCase,Narpas.Constants.uchars);
	        ArrayUtil.fillArrayList(numbers,Narpas.Constants.nums);
	        ArrayUtil.fillArrayList(specialChars,Narpas.Constants.schars);
	        ArrayUtil.fillArrayList(limitSpecialChars,lsc);
	        ArrayUtil.fillArrayList(extChars,Narpas.Constants.extchars);
			PasswordSetting settings=new PasswordSetting();
			settings.setAlgorithmVersion(2);
			settings.setOptionAllowDuplicateCharacters(false);
			for(int length=8;length<=128;length++){
				settings.setPasswordLength(length);
				/*
				 * lcase only
				 */
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(false);
				settings.setOptionUseSChars(false);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(false);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				String password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					assertTrue(lowerCase.contains(password.charAt(i)));
				}		
				/*
				 * ucase only
				 */
				settings.setOptionUseLCase(false);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(false);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(false);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					assertTrue(upperCase.contains(password.charAt(i)));
				}					
				/*
				 * ucase only
				 */
				settings.setOptionUseLCase(false);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(false);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(false);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					assertTrue(upperCase.contains(password.charAt(i)));
				}				
				/*
				 * schars only
				 */
				settings.setOptionUseLCase(false);
				settings.setOptionUseUCase(false);
				settings.setOptionUseSChars(true);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(false);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					assertTrue(specialChars.contains(password.charAt(i)));
				}			
				/*
				 * limit special characters only
				 */
				settings.setOptionUseLCase(false);
				settings.setOptionUseUCase(false);
				settings.setOptionUseSChars(true);
				settings.setLimitSpecialChars(lsc);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(false);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					assertTrue(limitSpecialChars.contains(password.charAt(i)));
				}				
				/*
				 * extchars only
				 */
				settings.setOptionUseLCase(false);
				settings.setOptionUseUCase(false);
				settings.setOptionUseSChars(false);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(true);
				settings.setOptionUseNumbers(false);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					assertTrue(extChars.contains(password.charAt(i)));
				}	
				/*
				 * numbers only
				 */
				settings.setOptionUseLCase(false);
				settings.setOptionUseUCase(false);
				settings.setOptionUseSChars(false);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(true);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					assertTrue(numbers.contains(password.charAt(i)));
				}		
				/*
				 * variables for combination testing
				 */				
				boolean hasLCase=false;
				boolean hasUCase=false;
				boolean hasNumber=false;
				boolean hasSpecialChar=false;
				boolean hasExtChar=false;
				boolean selectedCharsOnly=false;
				/*
				 * everything
				 */					
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(true);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(true);
				settings.setOptionUseNumbers(true);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					selectedCharsOnly=false;
					if(lowerCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasLCase=true;
					}else if(upperCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasUCase=true;
					}else if(specialChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasSpecialChar=true;
					}else if(extChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasExtChar=true;
					}else if(numbers.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasNumber=true;
					}
					assertTrue(selectedCharsOnly);
				}
				if(!hasLCase||!hasUCase||!hasSpecialChar||!hasExtChar||!hasNumber){
					fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar+"\nhasExtChar="+hasExtChar);
				}	
				/*
				 * everything except lcase
				 */
				hasLCase=false;
				hasUCase=false;
				hasNumber=false;
				hasSpecialChar=false;
				hasExtChar=false;
				selectedCharsOnly=false;
				settings.setOptionUseLCase(false);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(true);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(true);
				settings.setOptionUseNumbers(true);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					selectedCharsOnly=false;
					if(upperCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasUCase=true;
					}else if(specialChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasSpecialChar=true;
					}else if(extChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasExtChar=true;
					}else if(numbers.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasNumber=true;
					}
					assertTrue(selectedCharsOnly);
				}
				if(!hasUCase||!hasSpecialChar||!hasExtChar||!hasNumber){
					fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar+"\nhasExtChar="+hasExtChar);
				}					
				/*
				 * everything except ucase
				 */
				hasLCase=false;
				hasUCase=false;
				hasNumber=false;
				hasSpecialChar=false;
				hasExtChar=false;
				selectedCharsOnly=false;
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(false);
				settings.setOptionUseSChars(true);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(true);
				settings.setOptionUseNumbers(true);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					selectedCharsOnly=false;
					if(lowerCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasLCase=true;
					}else if(specialChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasSpecialChar=true;
					}else if(extChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasExtChar=true;
					}else if(numbers.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasNumber=true;
					}
					assertTrue(selectedCharsOnly);
				}
				if(!hasLCase||!hasSpecialChar||!hasExtChar||!hasNumber){
					fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar+"\nhasExtChar="+hasExtChar);
				}					
				/*
				 * everything with limit special characters
				 */					
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(true);
				settings.setLimitSpecialChars(lsc);
				settings.setOptionUseExtChars(true);
				settings.setOptionUseNumbers(true);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					selectedCharsOnly=false;
					if(lowerCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasLCase=true;
					}else if(upperCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasUCase=true;
					}else if(limitSpecialChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasSpecialChar=true;
					}else if(extChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasExtChar=true;
					}else if(numbers.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasNumber=true;
					}
					assertTrue(selectedCharsOnly);
				}
				if(!hasLCase||!hasUCase||!hasSpecialChar||!hasExtChar||!hasNumber){
					fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar+"\nhasExtChar="+hasExtChar);
				}	
				/*
				 * everything except extChars
				 */					
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(true);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(true);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					selectedCharsOnly=false;
					if(lowerCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasLCase=true;
					}else if(upperCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasUCase=true;
					}else if(specialChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasSpecialChar=true;
					}else if(numbers.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasNumber=true;
					}
					assertTrue(selectedCharsOnly);
				}
				if(!hasLCase||!hasUCase||!hasSpecialChar||!hasNumber){
					fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar+"\nhasExtChar="+hasExtChar);
				}					
				/*
				 * everything except numbers
				 */					
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(true);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(true);
				settings.setOptionUseNumbers(false);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					selectedCharsOnly=false;
					if(lowerCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasLCase=true;
					}else if(upperCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasUCase=true;
					}else if(specialChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasSpecialChar=true;
					}else if(extChars.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasExtChar=true;
					}
					assertTrue(selectedCharsOnly);
				}
				if(!hasLCase||!hasUCase||!hasSpecialChar||!hasExtChar){
					fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar+"\nhasExtChar="+hasExtChar);
				}					
				/*
				 * lcase+ucase+numbers
				 */					
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(false);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(true);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					selectedCharsOnly=false;
					if(lowerCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasLCase=true;
					}else if(upperCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasUCase=true;
					}else if(numbers.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasNumber=true;
					}
					assertTrue(selectedCharsOnly);
				}
				if(!hasLCase||!hasUCase||!hasNumber){
					fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar+"\nhasExtChar="+hasExtChar);
				}
				/*
				 * lcase+ucase
				 */		
				hasLCase=false;
				hasUCase=false;
				hasNumber=false;
				hasSpecialChar=false;
				hasExtChar=false;
				selectedCharsOnly=false;				
				settings.setOptionUseLCase(true);
				settings.setOptionUseUCase(true);
				settings.setOptionUseSChars(false);
				settings.setLimitSpecialChars(null);
				settings.setOptionUseExtChars(false);
				settings.setOptionUseNumbers(false);
				settings.setPasswordName(UUID.randomUUID().toString());
				settings.setPasswordVersion(Integer.toString(r.nextInt()));
				password=Narpas.generatePassword(UUID.randomUUID().toString(),settings);			
				for(int i=0;i<length;i++){
					selectedCharsOnly=false;
					if(lowerCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasLCase=true;
					}else if(upperCase.contains(password.charAt(i))){
						selectedCharsOnly=true;
						hasUCase=true;
					}
					assertTrue(selectedCharsOnly);
				}
				if(!hasLCase||!hasUCase){
					fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar+"\nhasExtChar="+hasExtChar);
				}				
			}
		}catch(Exception x){
			x.printStackTrace();
			fail(x.getMessage());
		}
	}

}
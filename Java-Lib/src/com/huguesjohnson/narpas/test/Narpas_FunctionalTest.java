/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huguesjohnson.dubbel.util.ArrayUtil;
import com.huguesjohnson.narpas.CharacterSetArrayIndex;
import com.huguesjohnson.narpas.Narpas;
import com.huguesjohnson.narpas.NarpasUtil;
import com.huguesjohnson.narpas.PasswordSetting;
import com.huguesjohnson.narpas.PasswordSettingDateComparator;
import com.huguesjohnson.narpas.PasswordSettingNameComparator;
import com.huguesjohnson.narpas.StringEncryptDecrypt;

import junit.framework.TestCase;

public class Narpas_FunctionalTest extends TestCase{

	@SuppressWarnings("deprecation")
	@Test 
	public void test_computeHash(){
		String input="I like pie";
		int length=32;
		String hash=Narpas.computeHash(input,length);
		assertEquals("39d74f597a854b15819d46422e26879a",hash);
		
		input="Facebook";
		length=32;
		hash=Narpas.computeHash(input,length);
		assertEquals("d85544fce402c7a2a96a48078edaf203",hash);
	}

	@Test 
	public void test_v1generatePassword(){
		try{
			String passPhrase="I like pie";
			String passwordName="Facebook";
			boolean useLCase=true;
			boolean useUCase=true;
			boolean useNumbers=true;
			boolean useSpecialCharacters=true;
			
			int basePasswordLength=1;
			String password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("Io3!1+aW",password);
			PasswordSetting settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			String password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("S^l4$aA1f6%Ke2!C",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("a8!L6Uy@%Bf4O.2dl*M39xS$5#Ws?I1t",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("3Ie?$Ha9U^5ou.J04tA*2,GrlP1#!T8j6@Fk=7bL7h%S-Bi9:R6qCn0&+N3fv1;X",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("U-0oc:R45aG?1=NjbA6%*B8l2+Yq.3dM9w@S$Eg7;O7xFv4#n8,C!1IpD&r5T6e^%Z9tHu,0=3Xh@s2Lf5-J2iW.3mV!?K0y+Pk9zQ*4a1:XA6n#^F7ly8&W8Od;$Jb6",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			passPhrase="i like pie";
			passwordName="Facebook";
	
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("5K+m3Ca?",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("I$j0u:1L!6cDM&i2",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("8Ai$.L6a0U!ojK:91&Ypb5Z^4q%RN2*m",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("@I8p0H?beE,59#Zqg6A:1h&LR2;sc3+Oa4N!vY$7xU4=.d2BC5y^Dj%7F8*f3Gt-",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("kS%60&Reb1M.5j,JN8!uz9@Ha2O$qL^4vA3#*w7BK2s-Gc+4W7=d;1Tr0:nD3?fX8.Yx%g5U6hC#E$p9i5=VyQ3?F8@lI!0m7oP&4tZ*;Au6-kU1G2^e9Yi+:E0j7K,p",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			passPhrase="i like pie";
			passwordName="facebook";
	
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("l0;MW1k?",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("!J4lD:u7a;Q3jV0&",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals(",r0J9vD+iA1:2c-V3?eSp6Z.l^E7;X4w",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("0wA*eG4@1x,M2#tJa5B=q:T9Cu3?6k&D7-bY8W!ysH0^n%N41$vE+oQ7;K9c.d3L",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("6e,M3!aAd5O#g&V0Tp1?2t@R4.bS9U=cyB7-u;Y82^oC+fH86*jQw1$NPs0%:Er3hW4#9Dm,!F5x-Xk70iK@+n6G*3vJ5&qIlL$7%1Zz.2cG=V4o;A9d:u8Y4hS?aB2^",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
		
			passPhrase="I like pie";
			passwordName="Facebook";
			useLCase=true;
			useUCase=false;
			useNumbers=false;
			useSpecialCharacters=false;
		
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("aytlnfbq",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("eaopixcbdqfyltus",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("uaftrsmgwobjcxqelpdnyhvkizsvxhgr",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("wopmcfvtzxksnaqbdgiujrehylwaefjzxbcqtrdugkhysnipvlmogickahbdylvp",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("jalhygbpizfocsermuqdknxvtwcxhraslnoyjbdtpfqwguevikmzmkoecuvpltgynqhbjwrxsaifdzsvxhpenifjlaotkrmwbcqdyugzjahlfoqnbikmpwtscgrxzduy",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			passPhrase="I like pie";
			passwordName="Facebook";
			useLCase=false;
			useUCase=true;
			useNumbers=false;
			useSpecialCharacters=false;
	
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("EAOPIXCB",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("UAFTRSMGWOBJCXQE",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("TZSCJIVQLXAWGOBKDFMUNREHYPEAOPIX",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("JAKHESBNYURXTCDFVOGQPILZMWCEGUWPQSVIYODZHJFRTKLXMBANCGORYENQHXID",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("ABFQNUZVCOKWIRMPXYHEDGSJLTPDBCQMLTAJIEWHYOSXFUVRKNGZQAFMKJPVNEBCIDGZLHUYORWTSXYFAXIZDTBCJEGOQMHWNSPVKLRUIGMFZTBOUDQWEASCHYNRLPKJ",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			passPhrase="Please no more California songs";
			passwordName="orakio@mailinator.com";
			useLCase=true;
			useUCase=true;
			useNumbers=true;
			useSpecialCharacters=true;
	
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("V%v08sK+",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("8*aVt^M6n0,ARu1&",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("7=kFn?T5!a4Am.R3@Jh69o,Y0v&L8Wu-",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("r!I6;a9Dj?R4@Yd03y-T1p*H2A#bC7e:$t8K5%OvP^1uZ7o=,0mG&k2S8Js+6.cX",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
		
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("o:M7=Kb34a%C0g,N5Y$nE8u-&j6X2^UpT*9kO1f+?0lH@q1Ic4P!.2yR3rF#8v;AxS5%Z:7dL9e*6D;hw!J0B7z?i=1Qm2@G,sV5t3W#Ae$46z^B8Kq&9-dMa+U0.j8V",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			passPhrase="Please no more california songs";
			passwordName="orakio@mailinator.com";
	
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("Tm2%K?u0",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("-c4BSa+2U!i0N6$l",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("Or-6Uj?08i,CG=o4%5gSL#y1k2N!&3Eq",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("Pi-14v&GE:a8@0mJU!w2r3B#f5F+kO%6$s7TA9^ye*R0=Xb1cC;8?N2n,3VoKu.9",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("M#m7+6aUQ;h2n0X$q5Y%uH^4-b8AI1=ei*B9!Wy3cN?3:E0lr4.C@L7k,9FsdJ&1!xD8V:6ftP2?5,gZO@6vj3S*5oG$Kz+4Rp0&wT-12#iCt7S%^8QxXl.9Ay=04k;W",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			passPhrase="Please no more California songs";
			passwordName="orakio@mailinator.com";
			useLCase=false;
			useUCase=false;
			useNumbers=true;
			useSpecialCharacters=false;
	
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("27510698",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("6345021789081327",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("40187236954630125798275106983401",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("4057321689984562013787019523644107623859078461235904871325961709",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("80157429366301297548930152468704127683958641790325061374925825036198474520183679294015836716203457984705198326016234587920195364",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			passPhrase="Please no more California songs";
			passwordName="orakio@mailinator.com";
			useLCase=false;
			useUCase=false;
			useNumbers=true;
			useSpecialCharacters=true;
	
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals(";12?6=0#",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("5!3$4=0*6^.1%27-",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("4:9+0@1$.2*68^3?!75,2#-43%&60;1=",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("4#0:!3,61@9$+57%2-^88*.73&=1?49;-6!02@#5;6+3$08%^71=&2.45*9,1:0?",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("8!2@&73=4+?90%#15:.6,01*$6^85;-2!3*4@79:#45$,1?30-2+%96&8^7.2;=46%9&;0=8,51^3!#72-0@$75+8*.43:?9-16:4!7+*6?50%;12@3#8,9&2^0$=8.1",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			passPhrase="Please no more California songs";
			passwordName="orakio@mailinator.com";
			useLCase=false;
			useUCase=true;
			useNumbers=true;
			useSpecialCharacters=false;
	
			basePasswordLength=1;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("2CW4E5Q7",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=2;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("M1A0F5D8Y39K7NU6",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=4;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("R8U5D1X24M7ZO0L36QA9T12VG50EI4B8",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
			
			basePasswordLength=8;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("A7B80J1RO2F45PM6E93CS60YD37H8GT41I2QX59N6K2V1WL94UZ03W5VS7P8I1H0",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
	
			basePasswordLength=16;
			password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			assertEquals("A2C73IF4B01UN68XS95K4OM35Y7ET16J0D2Z8GL94HR30P6QV9W51TZ2Q7M8F60YE3I74J9A5KG2L81WN6C40BR1S79XO53H2DV8P2U30W9EA17QB4J8N5V6U0Z19G4C",password);
			settings=new PasswordSetting(passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,basePasswordLength);
			password2=Narpas.generatePassword(passPhrase,settings);
			assertEquals(password,password2);
		}catch(Exception x){
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	
	@Test 
	public void test_toFromJson(){
		Random r=new Random();
		//create list of password settings
		ArrayList<PasswordSetting> toList=new ArrayList<PasswordSetting>();
		PasswordSetting v1name1=new PasswordSetting(UUID.randomUUID().toString(),r.nextBoolean(),r.nextBoolean(),r.nextBoolean(),r.nextBoolean(),32,UUID.randomUUID().toString());
		toList.add(v1name1);
		PasswordSetting v1name2=new PasswordSetting(UUID.randomUUID().toString(),r.nextBoolean(),r.nextBoolean(),r.nextBoolean(),r.nextBoolean(),16);
		toList.add(v1name2);
		//v2 password settings
		long now=System.currentTimeMillis();
		char[] limitSpecialChars={'!','@','#','$','%'};
		for(int i=0;i<10;i++){
			PasswordSetting psv2=new PasswordSetting();
			psv2.setPasswordName(UUID.randomUUID().toString());
			psv2.setOptionUseLCase(r.nextBoolean());
			psv2.setOptionUseUCase(r.nextBoolean());
			psv2.setOptionUseNumbers(r.nextBoolean());
			psv2.setOptionUseSChars(r.nextBoolean());
			psv2.setPasswordLength(r.nextInt());
			psv2.setPasswordNotes(UUID.randomUUID().toString());
			psv2.setCategory(UUID.randomUUID().toString());
			psv2.setAlgorithmVersion(2);
			psv2.setPasswordVersion(Integer.toString(r.nextInt()));
			psv2.setLimitSpecialChars(limitSpecialChars);
			psv2.setLastUsed(now);
			toList.add(psv2);
		}
		//serialize the list to json
		String json=new Gson().toJson(toList);
		//read it back
		ArrayList<PasswordSetting> fromList=new Gson().fromJson(json,new TypeToken<List<PasswordSetting>>(){}.getType());
		//basic validation
		assertNotNull(fromList);
		int size=toList.size();
		assertEquals(fromList.size(),size);
		//see if the contents are the same
		for(int i=0;i<size;i++){
			PasswordSetting to=toList.get(i);
			PasswordSetting from=fromList.get(i);
			//settings for all password versions
			assertEquals(from.isOptionUseLCase(),to.isOptionUseLCase());
			assertEquals(from.isOptionUseNumbers(),to.isOptionUseNumbers());
			assertEquals(from.isOptionUseSChars(),to.isOptionUseSChars());
			assertEquals(from.isOptionUseUCase(),to.isOptionUseUCase());
			assertEquals(from.getPasswordLength(),to.getPasswordLength());
			assertEquals(from.getPasswordName(),to.getPasswordName());
			assertEquals(from.getPasswordNotes(),to.getPasswordNotes());
			//check version - if >1 then check fields added after version 1
			int av=from.getAlgorithmVersion();
			assertEquals(av,to.getAlgorithmVersion());
			if(av>1){
				assertEquals(from.getCategory(),to.getCategory());
				//this date nonsense is going to be an article someday...
				assertEquals(from.getLastUsed(),to.getLastUsed());
				assertEquals(from.getPasswordVersion(),to.getPasswordVersion());
				char[] fromchars=from.getLimitSpecialChars();
				char[] tochars=to.getLimitSpecialChars();
				if(fromchars==null){
					if(tochars!=null){
						fail("fromchars==null and tochars!=null");
					}
				}else{
					if(tochars==null){
						fail("fromchars!=null and tochars==null");
					}
					int l=fromchars.length;
					assertEquals(l,tochars.length);
					for(int j=0;j<l;j++){
						assertEquals(fromchars[j],tochars[j]);
					}
				}
			}
		}
	}

	@Test
	public void test_validate(){
		/*
		 * start with something that should pass
		 */
		String passPhrase="I like pie";
		PasswordSetting settings=new PasswordSetting();
		settings.setPasswordName("whatever");
		settings.setOptionUseLCase(true);
		settings.setOptionUseUCase(true);
		settings.setOptionUseSChars(true);
		settings.setOptionUseNumbers(true);
		settings.setOptionUseExtChars(true);		
		settings.setPasswordLength(Narpas.Constants.MIN_LENGTH);
		settings.setAlgorithmVersion(0);
		assertTrue(Narpas.validate(passPhrase,settings));
		/*
		 * test algorithm version
		 */
		settings.setAlgorithmVersion(-1);
		assertFalse(Narpas.validate(passPhrase,settings));
		settings.setAlgorithmVersion(3);
		assertFalse(Narpas.validate(passPhrase,settings));
		settings.setAlgorithmVersion(2);
		assertTrue(Narpas.validate(passPhrase,settings));
		/*
		 * test passwordname==passphrase
		 */
		settings.setPasswordName(passPhrase);
		assertFalse(Narpas.validate(passPhrase,settings));
		settings.setPasswordName("something else");
		assertTrue(Narpas.validate(passPhrase,settings));
		/*
		 * test no character sets selected
		 */
		settings.setPasswordName("something else");
		settings.setOptionUseLCase(false);
		settings.setOptionUseUCase(false);
		settings.setOptionUseSChars(false);
		settings.setOptionUseNumbers(false);
		settings.setOptionUseExtChars(false);
		assertFalse(Narpas.validate(passPhrase,settings));
		/*
		 * test v1 character sets 
		 */
		settings.setAlgorithmVersion(1);
		assertFalse(Narpas.validate(passPhrase,settings));
		settings.setOptionUseExtChars(true);
		assertFalse(Narpas.validate(passPhrase,settings));
		/*
		 * test null objects
		 */
		settings.setAlgorithmVersion(2);
		assertFalse(Narpas.validate(null,settings));
		assertFalse(Narpas.validate(passPhrase,null));
		settings.setPasswordName(null);
		assertFalse(Narpas.validate(passPhrase,settings));
		/*
		 * test blank strings
		 */
		settings.setPasswordName("");
		assertFalse(Narpas.validate(passPhrase,settings));
		settings.setPasswordName("whatever");
		assertFalse(Narpas.validate("",settings));
	}
	
	@Test
	public void test_v2limitSpecialChars(){
		try{
			String passPhrase="I like pie";
			String passwordName="test_limitSpecialChars";
			PasswordSetting settings=new PasswordSetting();
			char[] limitSpecialChars={'*'};
			settings.setPasswordName(passwordName);
			settings.setAlgorithmVersion(2);
			settings.setPasswordLength(8);
			settings.setOptionUseLCase(false);
			settings.setOptionUseUCase(false);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(false);
			settings.setOptionUseNumbers(false);
			settings.setLimitSpecialChars(limitSpecialChars);
			String password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("********",password);
			settings.setLimitSpecialChars(null);
			password=Narpas.generatePassword(passPhrase,settings);
			assertNotSame("********",password);
			char[] limitSpecialChars2={'*','@','$','%'};
			settings.setPasswordLength(32);
			settings.setLimitSpecialChars(limitSpecialChars2);
			password=Narpas.generatePassword(passPhrase,settings);
			for(int i=0;i<password.length();i++){
				char c=password.charAt(i);
				assertTrue((c=='*')||(c=='@')||(c=='$')||(c=='%'));
			}
		}catch(Exception x){
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	
	@Test
	public void test_passwordSettingHashString(){
		try{
			PasswordSetting settings=new PasswordSetting();
			settings.setPasswordName("example.com");
			settings.setAlgorithmVersion(2);
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(true);
			settings.setOptionUseNumbers(true);		
			settings.setPasswordVersion("1");
			String hs=Narpas.passwordSettingHashString(settings);
			assertEquals("exa5m1ple.com1",hs);
			settings.setPasswordVersion("2010");
			hs=Narpas.passwordSettingHashString(settings);
			assertEquals("25exam3ple.com2010",hs);
			settings.setPasswordVersion("-666");
			hs=Narpas.passwordSettingHashString(settings);
			assertEquals("e4xampl1e.co2m-666",hs);
		}catch(Exception x){
			x.printStackTrace();
			fail(x.getMessage());
		}
	}

	@Test
	public void test_v2CharacterSets(){
		//verify that only the expected character sets appear in passwords
		try{
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
			//test just lower case characters first
			int length=128;
			PasswordSetting settings=new PasswordSetting(
					"some arbitrary password name",
					true, 
					false,
					false, 
					false,
					false,
					length,
					"",
					"",
					2,
					"0",
					null,
					0L);
			String password=Narpas.generatePassword("lchar only test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(lowerCase.contains(password.charAt(i)));
			}
			//test just upper case characters
			settings.setOptionUseLCase(false);
			settings.setOptionUseUCase(true);
			password=Narpas.generatePassword("uchar only test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(upperCase.contains(password.charAt(i)));
			}
			//test just numbers
			settings.setOptionUseUCase(false);
			settings.setOptionUseNumbers(true);
			password=Narpas.generatePassword("numbers only test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(numbers.contains(password.charAt(i)));
			}		
			//test ext characters
			settings.setOptionUseNumbers(false);
			settings.setOptionUseExtChars(true);
			password=Narpas.generatePassword("extended characters only test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(extChars.contains(password.charAt(i)));
			}	
			//test special characters
			settings.setOptionUseExtChars(false);
			settings.setOptionUseSChars(true);
			password=Narpas.generatePassword("special characters only test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(specialChars.contains(password.charAt(i)));
			}
			//test limit characters
			settings.setLimitSpecialChars(lsc);
			password=Narpas.generatePassword("limit special characters only test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(limitSpecialChars.contains(password.charAt(i)));
			}			
			//test characters+numbers+limitspecial chars
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			password=Narpas.generatePassword("characters+numbers+limitspecial",settings);			
			boolean hasLCase=false;
			boolean hasUCase=false;
			boolean hasNumber=false;
			boolean hasSpecialChar=false;
			for(int i=0;i<length;i++){
				boolean selectedCharsOnly=false;
				if(limitSpecialChars.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasSpecialChar=true;
				}else if(numbers.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasNumber=true;
				}else if(lowerCase.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasLCase=true;
				}else if(upperCase.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasUCase=true;
				}
				assertTrue(selectedCharsOnly);
			}
			if(!hasLCase||!hasUCase||!hasNumber||!hasSpecialChar){
				fail("Missing expected character set. Password="+password);
			}
			//test characters+numbers+default special chars
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setLimitSpecialChars(null);
			hasLCase=false;
			hasUCase=false;
			hasNumber=false;
			hasSpecialChar=false;
			password=Narpas.generatePassword("characters+numbers+default special chars",settings);			
			for(int i=0;i<length;i++){
				boolean selectedCharsOnly=false;
				if(specialChars.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasSpecialChar=true;
				}else if(numbers.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasNumber=true;
				}else if(lowerCase.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasLCase=true;
				}else if(upperCase.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasUCase=true;
				}
				assertTrue(selectedCharsOnly);
			}	
			if(!hasLCase||!hasUCase||!hasNumber||!hasSpecialChar){
				fail("Missing expected character set. Password="+password);
			}			
			/*
			 * same tests but allowing duplicate characters
			 */
			settings.setOptionAllowDuplicateCharacters(true);
			settings.setOptionUseUCase(false);
			settings.setOptionUseNumbers(false);
			settings.setOptionUseSChars(false);
			password=Narpas.generatePassword("lchar+duplicate characters test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(lowerCase.contains(password.charAt(i)));
			}
			//test just upper case characters
			settings.setOptionUseLCase(false);
			settings.setOptionUseUCase(true);
			password=Narpas.generatePassword("uchar+duplicate characters test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(upperCase.contains(password.charAt(i)));
			}
			//test just numbers
			settings.setOptionUseUCase(false);
			settings.setOptionUseNumbers(true);
			password=Narpas.generatePassword("numbers+duplicate characters test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(numbers.contains(password.charAt(i)));
			}		
			//test ext characters
			settings.setOptionUseNumbers(false);
			settings.setOptionUseExtChars(true);
			password=Narpas.generatePassword("extended+duplicate characters test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(extChars.contains(password.charAt(i)));
			}	
			//test special characters
			settings.setOptionUseExtChars(false);
			settings.setOptionUseSChars(true);
			password=Narpas.generatePassword("special characters+duplicate characters test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(specialChars.contains(password.charAt(i)));
			}
			//test limit characters
			settings.setLimitSpecialChars(lsc);
			password=Narpas.generatePassword("limit special characters+duplicate characters test",settings);			
			for(int i=0;i<length;i++){
				assertTrue(limitSpecialChars.contains(password.charAt(i)));
			}			
			//test characters+numbers+limitspecial chars
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			password=Narpas.generatePassword("characters+numbers+limitspecial+duplicate characters test",settings);			
			hasLCase=false;
			hasUCase=false;
			hasNumber=false;
			hasSpecialChar=false;
			for(int i=0;i<length;i++){
				boolean selectedCharsOnly=false;
				if(limitSpecialChars.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasSpecialChar=true;
				}else if(numbers.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasNumber=true;
				}else if(lowerCase.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasLCase=true;
				}else if(upperCase.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasUCase=true;
				}
				assertTrue(selectedCharsOnly);
			}
			if(!hasLCase||!hasUCase||!hasNumber||!hasSpecialChar){
				fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar);
			}
			//test characters+numbers+default special chars
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setLimitSpecialChars(null);
			hasLCase=false;
			hasUCase=false;
			hasNumber=false;
			hasSpecialChar=false;
			password=Narpas.generatePassword("characters+numbers+default special chars",settings);			
			for(int i=0;i<length;i++){
				boolean selectedCharsOnly=false;
				if(specialChars.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasSpecialChar=true;
				}else if(numbers.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasNumber=true;
				}else if(lowerCase.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasLCase=true;
				}else if(upperCase.contains(password.charAt(i))){
					selectedCharsOnly=true;
					hasUCase=true;
				}
				assertTrue(selectedCharsOnly);
			}	
			if(!hasLCase||!hasUCase||!hasNumber||!hasSpecialChar){
				fail("Missing expected character set.\nPassword="+password+"\nhasLCase="+hasLCase+"\nhasUCase="+hasUCase+"\nhasNumber="+hasNumber+"\nhasSpecialChar="+hasSpecialChar);
			}	
		}catch(Exception x){
			x.printStackTrace();
			fail(x.getMessage());
		}		
	}
	
	@Test
	public void test_weights(){
		/* It would take some serious effort to break this but I know I'm capable of it. */
		PasswordSetting ps=new PasswordSetting();
		ps.setWeight(CharacterSetArrayIndex.EXTCHARS,0.9d);
		ps.setWeight(CharacterSetArrayIndex.LCHARS,0.8d);
		ps.setWeight(CharacterSetArrayIndex.NUMS,0.7d);
		ps.setWeight(CharacterSetArrayIndex.SCHARS,0.6d);
		ps.setWeight(CharacterSetArrayIndex.UCHARS,0.5d);
		assertEquals(0.9,ps.getWeight(CharacterSetArrayIndex.EXTCHARS));
		assertEquals(0.8,ps.getWeight(CharacterSetArrayIndex.LCHARS));
		assertEquals(0.7,ps.getWeight(CharacterSetArrayIndex.NUMS));
		assertEquals(0.6,ps.getWeight(CharacterSetArrayIndex.SCHARS));
		assertEquals(0.5,ps.getWeight(CharacterSetArrayIndex.UCHARS));
	}
	
	@Test
	public void test_v2GeneratePassword(){
		try{
			
			/* start with tests from V1 */
			
			String passPhrase="I like pie";
			String passwordName="Facebook";
			boolean useLCase=true;
			boolean useUCase=true;
			boolean useNumbers=true;
			boolean useSpecialCharacters=true;
			boolean useExtCharacters=false;
			int length=8;
			String passwordNotes="";
			String category="";
			int algorithmVersion=2;
			String passwordVersion="0";
			char[] limitSpecialChars=null;			
			long lastUsed=0L;
			
			PasswordSetting settings=new PasswordSetting(
					passwordName,
					useLCase, 
					useUCase,
					useNumbers, 
					useSpecialCharacters,
					useExtCharacters,
					length,
					passwordNotes,
					category,
					algorithmVersion,
					passwordVersion,
					limitSpecialChars,
					lastUsed);
			
			String password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("V4bGt6#%",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("A6!s#v?bgpX84DH=",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("IoFGg9.4XNCl-=H*dv6rueA!5mh8?Z0$",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("^rWQ,-*2Ko8%9i&qk6hGZV7bMOpStRYTw!E@I=m$f0?4aujNPCe#Fvx3Hsc51:L;",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("Ww%UgP2v834pD6XGV:&?m5qKYvea,szh7k=Br0#Qbnh1y-u*xsntOLdAlorNbo$lqcfESklqgbpzf+jcCaysdvI@pu.ifFnRxj9ermutikixcwT^egtyHo;dZz!JjmaM",password);

			settings.setPasswordName("facebook");

			settings.setPasswordLength(8);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("#2tB0,Fb",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("qXU*lZC@8!21,bft",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("jJ+pn5;NI1vLkX%8r7CH0&F,$wtSx6h:",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("?H0JdA^o:#P59+vlxS8XLY7i!&tkN4G=W1$qV,c%wMgnjU@abR-eTC2zOZ;I6pm3",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("R*s7UuhWyfd2D#;:,%oiM4K-.^L6NbXHw3vSl&rs?gke$@YBcn9AzlxPEhtd+jrTjvbqgoQuiqwluZeabfvmotxp1imrn0cqVmsJO=k8aG!pgzhFCcnyIpxyjdt5kfwa",password);
						
			passPhrase="i like pie";
			settings.setPasswordName("Facebook");

			settings.setPasswordLength(8);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("64NYf;k-",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("xrw*#6%5X;SKE4tp",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("G6&QIOx8sBK-Zq!Rp.3woY0?u4t^+hj1",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("lE,C12@u%-4z7=FpJ$Oim#nA0PK!8eZr6kB:5D^NvLWUVIwahQ9?Yxb+t3So&d.s",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("Hbl0j$BNGyZoO+#J=!tidgpr;n@2V6hK*aD1vXSQ^zsykMLWzAqUcc&T7lsYwd%ouhfz,gjsgtfirbajhxmPpwe8xqvRunvc.ey93me:bdmFwIn4fqiu-kE5klrp?Cta",password);
			
			settings.setPasswordName("facebook");
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("yLj7-R3+",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("aZ7vX6Sh.3E!o+u;",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("29g+esGKm;=NDCu*,7P0bQxT?pw%13Ek",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("YOVl=nw2hvQ%u.H6SB@q78#9dXjk^Eys-I1U;4!gG5MDmtWi$*x0Rr:p&cZTL3,C",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("aQJjnp-zuM*6VR4,h7bN=qy3OLiUXtf8?r$wYED#5xlBsmknwStZbkirvcaoxzf+HInsxdovd&qWkPjchcyesaC1!0z@%yvgmtq^p.Kg9F2ruAfe;lTo:Gugwmbdlejh",password);

			passPhrase="I like pie";
			settings.setPasswordName("Facebook");
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(false);
			settings.setOptionUseNumbers(false);
			settings.setOptionUseSChars(false);
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("kpsfzmud",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("bihltxksvmycjoqz",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("cygzvpfrqohjtldemvwxtuibjknsanbd",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("szaufmbckfgixhjrlvvqhrnyzgmnwjtmakloxoydsbjqwivwulefpdpxdutsnkce",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("hlxyofzmrabjlcjggszbhfecvnwqwmrqyungdbvsdoplxzvinyvpmbrmjuscwkztrlhqjifnbdoxueaekkhaepgpdwyczaikrtfnvmfackquxyijdopttitoxhssuegw",password);

			settings.setOptionUseLCase(false);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(false);
			settings.setOptionUseSChars(false);
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("DJZLPFEC",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("PLEYAFXIKMGOCZRB",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("OQYTKGLUJBSPFWZEVIAMKVZNXHFCDRYA",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("RDHAGBFYJSEJKDWRVCOXPHXQOITUULEVBWKFLZZCYQMEACPGSFMDWNITNINURATM",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("HMCHTEOGSLRACZBLPEMFQUDMNYFJBIRNIEVTDOCPGWOAKUPMEEHCBRGZFRJXOFTWQVBSBLIHQLYNZZQJAUIFJKUDSYUVKRGSDQMPIDXTLXNKPXXOYVYAWSWCNWAKJGZH",password);

			passPhrase="Please no more California songs";
			settings.setPasswordName("orakio@mailinator.com");
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(true);
			settings.setPasswordLength(8);
		
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("0goB9=V*",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("T7e9.uXhb:@V=p1L",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("2Hj!7B8MPncN4$mb0arI%C-.G?Dkx,1g",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("q6N?:FBIa,i3&X5lTgd1-K90cxG=+U8H%2Ce7sQmtD@!wJbA#pkjRyS^;u4ZYOr.",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("rU?Sc:dq-X@j9R%IB1KNl$YDQmoO7C4kaqw*tzgf6gns.,hp;fltWEnevVothuTyxbraiphjywGdbamxcj+lypJifAun8beHo&Fvv=sLd0^5x3qkzPkrs#iec2z!mZuM",password);

			passPhrase="Please no more california songs";
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(true);
			settings.setPasswordLength(8);

			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("I$4dn9#B",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("s8VTt*?Ly#p4;7Qd",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("-!8wnMR=Zm3?qx:56tESc42A%Ha^joQW",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("GK9qjv$!VZtN.62DcTg*w4UL@JCua+b#m8Y^7:1fWx5H?sXz%E3Q=0PBy&dReko,",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals(";vN%oCsZYdt6zk?y=P0Ou^+!8I3Tx#qSi$UKo:aaGJwnMvzHfphcVwxE&dAglkmfFrmiwxy.gljftblav@XQem-5ngq4cojpzRL7qrur*cnDe,9Bubhydkhsj1p2sWeb",password);

			passPhrase="Please no more California songs";
			settings.setPasswordName("orakio@mailinator.com");
			settings.setOptionUseLCase(false);
			settings.setOptionUseUCase(false);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(false);
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("61708592",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("8079649123250376",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("58751129039227474667688084533910",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("7570349890461267062314435198206271491602087995948708287335553611",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("16775301393447401492020768127347062986108753638949043041166527221575312899488201065628289671395815091578284636026785470439435593",password);

			settings.setOptionUseLCase(false);
			settings.setOptionUseUCase(false);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(true);
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("721!;?#8",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("71;^5.26:%#@8!0,",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("62%+40;=,-5!:^*8&9@#1.$77841263?",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals(":5!%?=037@64#;-23.5+8&379869$,2165042^57149125829007419*08167843",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("^=48122.67+92;4356089:3&75*85128873760959136243931018455086437652609948155006270203%4993141@97220$!?6181828,9474#7-5234606757310",password);

			settings.setOptionUseLCase(false);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(false);
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("XYE9N70S",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("34T8HB25FPK6OUAY",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("KL3P2A6G5ZRUIM0J7WXS1TDVNCE984OF",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("Q6TSJIKV1WR724IBHSKGER0XNC8ODGFTZUCJBYQL5DYACHMAZFMVOIU93PLEWNPX",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("J1W7KP2SC3Q8THR6OLZM5IUAKZGVRGDYXPGLEJVNXHCDQQCIOUKSNYKVWQRZOMTBJUIADEWJFNLHLXQZKHFP9WECUYTM0DAPCSRVVITBFXBIYE4FLASENMOUMDOGYGFB",password);

			/* tests with new attributes in v2 */

			passPhrase="None of these passwords would work with AT&T.";
			settings.setPasswordName("AT&T");
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(true);
			settings.setPasswordVersion("1");
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("Êò,PuG2k",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("kMrnJ2ð8%ö!Ôã&ÐV",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("7S2dônÀ+0Ú!GÍHtqèPuOäßí@s#wÔ8;üF",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("VCÙü-d!f#ôK8ëÛR2?0tûxplì5ÜEÁ&6êGZeP^=áöñqÇbõQTI,i4ÿÅßJ%FÍún7yhé@",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("o@AëZx-3è8ÎøECþ÷&ó7ça;VÃ4áÞýhyì$íp2å6ßÑ0.É5tãMRznsXd^ÍðÊúPfàTiÇ%BqSÝÿ,âÐÖGH?wÜùêvmYl1bÕeDIOKæÆg#ÁWÓ:L+9*!ïÚÀürÌNcQÈjkõUÂÅ=uÛFØJÄ",password);			
			
			passPhrase="None of these passwords would work with AT&T.";
			settings.setPasswordName("AT&T");
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(true);
			settings.setPasswordVersion("2");
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("8ÑwîG&Tl",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("ÆÒ8dnáWIZ#%k4$ÖÙ",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("ãJv&BÑiÅÖw@:W1ËQ0îVqäD-r*Çmbì27Æ",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals(";SÞsûÝ8ütQÿbk0?ÎÄzÂómBpIg@xßÜWè%ZKá4Àçå$-e6&3í!Ucà#Ìjl1îÉG2A,YJE",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("ÐdZÊD0ýû6^Æ2ëÝ.ègï5Cv4à-cLIÜ=U9õeaJÖêuÙ$@HlKÞ#öAô1Çf3%ÏßNÅbÃkhXÄwEÓrÀzPBìtò8QÉMâÁùËp+ÔjÛy:nîñáÕ7ÒðíRs,SOT&F;mxGÌqøÚV?*Îo!iåYüWÂÑ",password);
			
			passPhrase="None of these passwords would work with AT&T.";
			settings.setPasswordName("AT&T");
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(true);
			settings.setOptionUseExtChars(true);
			settings.setOptionAllowDuplicateCharacters(true);
			settings.setPasswordVersion("0");
			settings.setPasswordLength(8);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("Üg2=rO4Y",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("52B^#mfK;ôÒl2HkÞ",password);
			
			settings.setPasswordLength(32);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("=è7hq-#6Á6ðbDô^I!ÁB3çlsHO!2Gh4cH",password);

			settings.setPasswordLength(64);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("*HyDFÖ0FÐh0=3à!tjJdWéJ%jg3òIGüÊHy50Ý!Uqm$nG%%ÄR830,@,tèt=Ã2à507#",password);

			settings.setPasswordLength(128);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("8dTfgF.r38H%X4R!x8H=uboØÉ8H5Â;K2LaÜ!4Öxúz&Nz.ù0pH#D7Q$6ËÁdÂq;d0ÞÛC&ca+;bÜKB!2oÞù7óa8Â96!=ÖnlÞLS&8B#Júvv09È07,dðú%ì:Ö7-H%0P&CYJC0",password);
			
			passPhrase="These passwords would work with AT&T.";
			settings.setPasswordName("AT&T");
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(true);
			settings.setOptionAllowDuplicateCharacters(false);
			limitSpecialChars=new char[2];
			limitSpecialChars[0]='-';
			limitSpecialChars[1]='_';
			settings.setLimitSpecialChars(limitSpecialChars);
			settings.setOptionUseExtChars(false);
			settings.setPasswordLength(6);
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("-gPy2I",password);

			settings.setPasswordLength(16);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("e4tFb86AzLO-arNB",password);			
			
			settings.setPasswordLength(24);
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("FAaL_4sGhPC2m-fYz1Jb50yq",password);

			passPhrase="This is the strongest password settings paypal supports, wtf.";
			settings.setPasswordName("paypal");
			settings.setOptionUseLCase(true);
			settings.setOptionUseUCase(true);
			settings.setOptionUseNumbers(true);
			settings.setOptionUseSChars(true);
			limitSpecialChars=new char[6];
			limitSpecialChars[0]='!';
			limitSpecialChars[1]='@';
			limitSpecialChars[2]='#';
			limitSpecialChars[3]='$';
			limitSpecialChars[4]='%';
			limitSpecialChars[5]='^';
			settings.setLimitSpecialChars(limitSpecialChars);
			settings.setOptionUseExtChars(false);
			settings.setPasswordLength(20);			
			
			password=Narpas.generatePassword(passPhrase,settings);
			assertEquals("jMgGrV8!1Ths@xSeK9^7",password);
		}catch(Exception x){
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
	
	@Test 
	public void test_EncryptDecrypt(){
		try{
			String testString=UUID.randomUUID().toString();
			String passphrase=UUID.randomUUID().toString();
			String encryptedString=StringEncryptDecrypt.encrypt(passphrase,testString);
			String decryptedString=StringEncryptDecrypt.decryptString(passphrase,encryptedString);
			assertEquals(testString,decryptedString);
			testString=UUID.randomUUID().toString();
			passphrase="test-passphrase-1";
			String encryptedString1=StringEncryptDecrypt.encrypt(passphrase,testString);
			passphrase="test-passphrase-2";
			String encryptedString2=StringEncryptDecrypt.encrypt(passphrase,testString);
			assertFalse(encryptedString1.equals(encryptedString2));
		}catch(Exception x){
			fail(x.getMessage());
		}
	}
	
	@Test
	public void test_Compare(){
		/*
		 * Setup identical passwords
		 */
		char[] limitSpecialChars=new char[]{'!','@'};
		PasswordSetting ps1=new PasswordSetting();
		PasswordSetting ps2=new PasswordSetting();
		ps1.setPasswordName("password-name");
		ps2.setPasswordName("password-name");
		ps1.setPasswordNotes("password-notes");
		ps2.setPasswordNotes("password-notes");
		ps1.setCategory("category");
		ps2.setCategory("category");
		ps1.setPasswordVersion("password-version");
		ps2.setPasswordVersion("password-version");
		ps1.setPasswordLength(32);
		ps2.setPasswordLength(32);
		ps1.setAlgorithmVersion(2);
		ps2.setAlgorithmVersion(2);
		ps1.setOptionAllowDuplicateCharacters(true);
		ps2.setOptionAllowDuplicateCharacters(true);
		ps1.setOptionUseExtChars(true);
		ps2.setOptionUseExtChars(true);
		ps1.setOptionUseLCase(true);
		ps2.setOptionUseLCase(true);
		ps1.setOptionUseUCase(true);
		ps2.setOptionUseUCase(true);
		ps1.setOptionUseNumbers(true);
		ps2.setOptionUseNumbers(true);
		ps1.setOptionUseSChars(true);
		ps2.setOptionUseSChars(true);
		ps1.setLimitSpecialChars(limitSpecialChars);
		ps2.setLimitSpecialChars(limitSpecialChars);
		/*
		 * Should start off identical
		 */		
		assertTrue(ps1.compare(ps2));
		/*
		 * Null object should return false
		 */		
		assertFalse(ps1.compare(null));
		/*
		 * Password name
		 */			
		ps1.setPasswordName(null);
		assertFalse(ps1.compare(ps2));
		ps1.setPasswordName("password-name");
		assertTrue(ps1.compare(ps2));
		ps2.setPasswordName(null);
		assertFalse(ps1.compare(ps2));
		ps2.setPasswordName("password-name");
		assertTrue(ps1.compare(ps2));
		ps2.setPasswordName("different");
		assertFalse(ps1.compare(ps2));
		ps2.setPasswordName("password-name");
		assertTrue(ps1.compare(ps2));
		ps1.setPasswordName(null);
		ps2.setPasswordName(null);
		assertTrue(ps1.compare(ps2));
		ps1.setPasswordName("password-name");
		ps2.setPasswordName("password-name");
		assertTrue(ps1.compare(ps2));
		/*
		 * Password notes
		 */			
		ps1.setPasswordNotes(null);
		assertFalse(ps1.compare(ps2));
		ps1.setPasswordNotes("password-notes");
		assertTrue(ps1.compare(ps2));
		ps2.setPasswordNotes(null);
		assertFalse(ps1.compare(ps2));
		ps2.setPasswordNotes("password-notes");
		assertTrue(ps1.compare(ps2));
		ps2.setPasswordNotes("different");
		assertFalse(ps1.compare(ps2));
		ps2.setPasswordNotes("password-notes");
		assertTrue(ps1.compare(ps2));
		ps1.setPasswordNotes(null);
		ps2.setPasswordNotes(null);
		assertTrue(ps1.compare(ps2));
		ps1.setPasswordNotes("password-notes");
		ps2.setPasswordNotes("password-notes");
		assertTrue(ps1.compare(ps2));		
		/*
		 * Category
		 */			
		ps1.setCategory(null);
		assertFalse(ps1.compare(ps2));
		ps1.setCategory("category");
		assertTrue(ps1.compare(ps2));
		ps2.setCategory(null);
		assertFalse(ps1.compare(ps2));
		ps2.setCategory("category");
		assertTrue(ps1.compare(ps2));
		ps2.setCategory("different");
		assertFalse(ps1.compare(ps2));
		ps2.setCategory("category");
		assertTrue(ps1.compare(ps2));
		ps1.setCategory(null);
		ps2.setCategory(null);
		assertTrue(ps1.compare(ps2));
		ps1.setCategory("category");
		ps2.setCategory("category");
		assertTrue(ps1.compare(ps2));		
		/*
		 * Password version
		 */			
		ps1.setPasswordVersion(null);
		assertFalse(ps1.compare(ps2));
		ps1.setPasswordVersion("password-version");
		assertTrue(ps1.compare(ps2));
		ps2.setPasswordVersion(null);
		assertFalse(ps1.compare(ps2));
		ps2.setPasswordVersion("password-version");
		assertTrue(ps1.compare(ps2));
		ps2.setPasswordVersion("different");
		assertFalse(ps1.compare(ps2));
		ps2.setPasswordVersion("password-version");
		assertTrue(ps1.compare(ps2));
		ps1.setPasswordVersion(null);
		ps2.setPasswordVersion(null);
		assertTrue(ps1.compare(ps2));
		ps1.setPasswordVersion("password-version");
		ps2.setPasswordVersion("password-version");
		assertTrue(ps1.compare(ps2));		
		/*
		 * Password length
		 */			
		ps2.setPasswordLength(8);
		assertFalse(ps1.compare(ps2));
		ps2.setPasswordLength(32);
		assertTrue(ps1.compare(ps2));
		/*
		 * Algorithm version
		 */			
		ps2.setAlgorithmVersion(1);
		assertFalse(ps1.compare(ps2));
		ps2.setAlgorithmVersion(2);
		assertTrue(ps1.compare(ps2));
		/*
		 * Allow duplicate characters
		 */			
		ps2.setOptionAllowDuplicateCharacters(false);
		assertFalse(ps1.compare(ps2));
		ps2.setOptionAllowDuplicateCharacters(true);
		assertTrue(ps1.compare(ps2));
		/*
		 * Use extended characters
		 */			
		ps2.setOptionUseExtChars(false);
		assertFalse(ps1.compare(ps2));
		ps2.setOptionUseExtChars(true);
		assertTrue(ps1.compare(ps2));
		/*
		 * Use lower case
		 */			
		ps2.setOptionUseLCase(false);
		assertFalse(ps1.compare(ps2));
		ps2.setOptionUseLCase(true);
		assertTrue(ps1.compare(ps2));
		/*
		 * Use upper case
		 */			
		ps2.setOptionUseUCase(false);
		assertFalse(ps1.compare(ps2));
		ps2.setOptionUseUCase(true);
		assertTrue(ps1.compare(ps2));
		/*
		 * Use numbers
		 */			
		ps2.setOptionUseNumbers(false);
		assertFalse(ps1.compare(ps2));
		ps2.setOptionUseNumbers(true);
		assertTrue(ps1.compare(ps2));
		/*
		 * Use special characters
		 */			
		ps2.setOptionUseSChars(false);
		assertFalse(ps1.compare(ps2));
		ps2.setOptionUseSChars(true);
		assertTrue(ps1.compare(ps2));
		/*
		 * Limit special characters
		 */			
		ps1.setLimitSpecialChars(null);		
		assertFalse(ps1.compare(ps2));
		ps1.setLimitSpecialChars(limitSpecialChars);
		assertTrue(ps1.compare(ps2));
		ps2.setLimitSpecialChars(null);		
		assertFalse(ps1.compare(ps2));
		ps1.setLimitSpecialChars(null);		
		ps2.setLimitSpecialChars(null);		
		assertTrue(ps1.compare(ps2));
		ps1.setLimitSpecialChars(limitSpecialChars);
		ps2.setLimitSpecialChars(new char[]{'!'});		
		assertFalse(ps1.compare(ps2));
		ps2.setLimitSpecialChars(new char[]{'!','-'});		
		assertFalse(ps1.compare(ps2));
		ps2.setLimitSpecialChars(new char[]{'!','@','&'});		
		assertFalse(ps1.compare(ps2));
		ps2.setLimitSpecialChars(limitSpecialChars);
		assertTrue(ps1.compare(ps2));
		/*
		 * Weights
		 */			
		ps2.setWeight(CharacterSetArrayIndex.LCHARS,Double.MAX_VALUE);
		assertFalse(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.LCHARS,1.0d);
		assertTrue(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.UCHARS,Double.MAX_VALUE);
		assertFalse(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.UCHARS,1.0d);
		assertTrue(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.NUMS,Double.MAX_VALUE);
		assertFalse(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.NUMS,1.0d);
		assertTrue(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.SCHARS,Double.MAX_VALUE);
		assertFalse(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.SCHARS,1.0d);
		assertTrue(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.EXTCHARS,Double.MAX_VALUE);
		assertFalse(ps1.compare(ps2));
		ps2.setWeight(CharacterSetArrayIndex.EXTCHARS,1.0d);
		assertTrue(ps1.compare(ps2));
	}
	
	@Test
	public void test_UtilGetAllCategories(){
		ArrayList<PasswordSetting> list=new ArrayList<PasswordSetting>();
		PasswordSetting ps=new PasswordSetting();
		ps.setPasswordName("name1");
		ps.setCategory("q_category");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("name2");
		ps.setCategory("w_category");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("name3");
		ps.setCategory("e_category");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("name4");
		ps.setCategory("r_category");
		list.add(ps);
		List<String> categories=NarpasUtil.getAllCategories(list);
		assertEquals(4,categories.size());
		assertEquals(categories.get(0),"e_category");
		assertEquals(categories.get(1),"q_category");
		assertEquals(categories.get(2),"r_category");
		assertEquals(categories.get(3),"w_category");
		ps=new PasswordSetting();
		ps.setPasswordName("name5");
		ps.setCategory(null);
		list.add(ps);
		categories=NarpasUtil.getAllCategories(list);
		assertEquals(5,categories.size());
		assertEquals(categories.get(0),NarpasUtil.DEFAULT_NO_CATEGORY);
		assertEquals(categories.get(1),"e_category");
		assertEquals(categories.get(2),"q_category");
		assertEquals(categories.get(3),"r_category");
		assertEquals(categories.get(4),"w_category");
	}
	
	@Test
	public void test_PasswordSettingDateComparator(){
		ArrayList<PasswordSetting> list=new ArrayList<PasswordSetting>();
		//add some password settings
		PasswordSetting ps=new PasswordSetting();
		ps.setPasswordName("password_6");
		ps.setLastUsed(6);
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("password_4");
		ps.setLastUsed(4);
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("password_89");
		ps.setLastUsed(89);
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("password_6489");
		ps.setLastUsed(6489);
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("password_4689");
		ps.setLastUsed(4689);
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("password_666");
		ps.setLastUsed(666);
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("password_13");
		ps.setLastUsed(13);
		list.add(ps);
		//sort
		list.sort(new PasswordSettingDateComparator(false));
		//expected order=6489, 4689, 666, 89, 13, 6, 4
		ps=list.get(0);
		assertEquals(ps.getPasswordName(),"password_6489");
		assertEquals(ps.getLastUsed(),6489);
		ps=list.get(1);
		assertEquals(ps.getPasswordName(),"password_4689");
		assertEquals(ps.getLastUsed(),4689);
		ps=list.get(2);
		assertEquals(ps.getPasswordName(),"password_666");
		assertEquals(ps.getLastUsed(),666);
		ps=list.get(3);
		assertEquals(ps.getPasswordName(),"password_89");
		assertEquals(ps.getLastUsed(),89);
		ps=list.get(4);
		assertEquals(ps.getPasswordName(),"password_13");
		assertEquals(ps.getLastUsed(),13);
		ps=list.get(5);
		assertEquals(ps.getPasswordName(),"password_6");
		assertEquals(ps.getLastUsed(),6);
		ps=list.get(6);
		assertEquals(ps.getPasswordName(),"password_4");
		assertEquals(ps.getLastUsed(),4);
		//reverse sort
		list.sort(new PasswordSettingDateComparator(true));
		ps=list.get(6);
		assertEquals(ps.getPasswordName(),"password_6489");
		assertEquals(ps.getLastUsed(),6489);
		ps=list.get(5);
		assertEquals(ps.getPasswordName(),"password_4689");
		assertEquals(ps.getLastUsed(),4689);
		ps=list.get(4);
		assertEquals(ps.getPasswordName(),"password_666");
		assertEquals(ps.getLastUsed(),666);
		ps=list.get(3);
		assertEquals(ps.getPasswordName(),"password_89");
		assertEquals(ps.getLastUsed(),89);
		ps=list.get(2);
		assertEquals(ps.getPasswordName(),"password_13");
		assertEquals(ps.getLastUsed(),13);
		ps=list.get(1);
		assertEquals(ps.getPasswordName(),"password_6");
		assertEquals(ps.getLastUsed(),6);
		ps=list.get(0);
		assertEquals(ps.getPasswordName(),"password_4");
		assertEquals(ps.getLastUsed(),4);
	}
	
	@Test
	public void test_PasswordSettingNameComparator(){
		ArrayList<PasswordSetting> list=new ArrayList<PasswordSetting>();
		//add some password settings
		PasswordSetting ps=new PasswordSetting();
		ps.setPasswordName("qassword");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("wassword");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("eassword");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("rassword");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("tassword");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("yassword");
		list.add(ps);
		ps=new PasswordSetting();
		ps.setPasswordName("uassword");
		list.add(ps);
		//sort
		list.sort(new PasswordSettingNameComparator(false));
		ps=list.get(0);
		assertEquals(ps.getPasswordName(),"eassword");
		ps=list.get(1);
		assertEquals(ps.getPasswordName(),"qassword");
		ps=list.get(2);
		assertEquals(ps.getPasswordName(),"rassword");
		ps=list.get(3);
		assertEquals(ps.getPasswordName(),"tassword");
		ps=list.get(4);
		assertEquals(ps.getPasswordName(),"uassword");
		ps=list.get(5);
		assertEquals(ps.getPasswordName(),"wassword");
		ps=list.get(6);
		assertEquals(ps.getPasswordName(),"yassword");
		//reverse sort
		list.sort(new PasswordSettingNameComparator(true));
		ps=list.get(6);
		assertEquals(ps.getPasswordName(),"eassword");
		ps=list.get(5);
		assertEquals(ps.getPasswordName(),"qassword");
		ps=list.get(4);
		assertEquals(ps.getPasswordName(),"rassword");
		ps=list.get(3);
		assertEquals(ps.getPasswordName(),"tassword");
		ps=list.get(2);
		assertEquals(ps.getPasswordName(),"uassword");
		ps=list.get(1);
		assertEquals(ps.getPasswordName(),"wassword");
		ps=list.get(0);
		assertEquals(ps.getPasswordName(),"yassword");
	}
}
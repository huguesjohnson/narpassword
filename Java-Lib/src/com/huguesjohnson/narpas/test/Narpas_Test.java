/* https://github.com/huguesjohnson/narpassword/blob/main/LICENSE */

package com.huguesjohnson.narpas.test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huguesjohnson.narpas.Narpas;
import com.huguesjohnson.narpas.PasswordSetting;
import com.huguesjohnson.narpas.StringEncryptDecrypt;
import com.sun.tools.javac.util.List;

import junit.framework.TestCase;

public class Narpas_Test extends TestCase{
	@Test 
	public void test_computeHash()
	{
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
	public void test_computeHashSHA3512(){
		try{
			assertEquals(Narpas.computeHashSHA3512(""),"a69f73cca23a9ac5c8b567dc185a756e97c982164fe25859e0d1dcc1475c80a615b2123af1f5f94c11e3e9402c3ac558f500199d95b6d3e301758586281dcd26");
			assertEquals(Narpas.computeHashSHA3512("I like pie"),"c203ba331a876b2e1d6b9d174d54b82dd29317cd0e132675e7a037387311edaa8ee0ed210849f5f5100744a115bdbb5fe7b04c4ffaf28ec42df1a239fa452372");
			assertEquals(Narpas.computeHashSHA3512("correct horse battery staple"),"4b65d7b7acc886f9add07db3a5d42bf0032fe0109a1fd56f623c7093e8a59689f9246918a4f388034ddf393231eaba0742b3dc1840e4556270a729ce56098f35");
		}catch(NoSuchAlgorithmException x){
			fail(x.getMessage());
		}
	}
	
	@Test
	public void test_xorBytes(){
		try{
			byte a[]=new byte[]{0,1,2,3,4};
			byte b[]=new byte[]{1,1,1,1,1};
			byte xor[]=Narpas.xorBytes(a,b);
			assertEquals(xor[0],1);
			assertEquals(xor[1],0);
			assertEquals(xor[2],3);
			assertEquals(xor[3],2);
			assertEquals(xor[4],5);
		}catch(Exception x){
			fail(x.getMessage());
		}
		try{
			byte a[]=new byte[]{0,1,2,3,4};
			byte b[]=new byte[]{1,1,1,1};
			Narpas.xorBytes(a,b);
			fail("This test should have failed.");
		}catch(Exception x){/*expected*/}		
		try{
			byte a[]=new byte[]{0,1,2,3};
			byte b[]=new byte[]{1,1,1,1,1};
			Narpas.xorBytes(a,b);
			fail("This test should have failed.");
		}catch(Exception x){/*expected*/}		
	}

	@Test 
	public void test_computeChecksum(){
		assertEquals(62,Narpas.computeChecksum("I like pie"));
		assertEquals(40,Narpas.computeChecksum("Facebook"));
	}	
	
	@Test 
	public void test_generatePassword()
	{
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
	}
	
	@Test 
	public void test_toJson()
	{
		 ArrayList<PasswordSetting> list=new ArrayList<PasswordSetting>();
		 list.add(new PasswordSetting("passwordName1",true,true,true,true,32,"passwordNotes1"));
		 list.add(new PasswordSetting("passwordName2",false,false,false,false,16));
		 String json=new Gson().toJson(list);
		 assertEquals("[{\"passwordName\":\"passwordName1\",\"optionUseLCase\":true,\"optionUseUCase\":true,\"optionUseNumbers\":true,\"optionUseSChars\":true,\"passwordLength\":32,\"passwordNotes\":\"passwordNotes1\",\"version\":1},{\"passwordName\":\"passwordName2\",\"optionUseLCase\":false,\"optionUseUCase\":false,\"optionUseNumbers\":false,\"optionUseSChars\":false,\"passwordLength\":16,\"version\":1}]",json);		 
	}
	
	@Test 
	public void test_fromJson()
	{
		String json="[{\"passwordName\":\"passwordName1\",\"optionUseLCase\":true,\"optionUseUCase\":true,\"optionUseNumbers\":true,\"optionUseSChars\":true,\"passwordLength\":32,\"passwordNotes\":\"passwordNotes1\"},{\"passwordName\":\"passwordName2\",\"optionUseLCase\":false,\"optionUseUCase\":false,\"optionUseNumbers\":false,\"optionUseSChars\":false,\"passwordLength\":16}]";
		ArrayList<PasswordSetting> list=new Gson().fromJson(json,new TypeToken<List<PasswordSetting>>(){}.getType());
		assertEquals(2,list.size());	
		PasswordSetting setting=list.get(0);
		assertEquals(setting.getPasswordName(),"passwordName1");
		assertEquals(setting.isOptionUseLCase(),true);
		assertEquals(setting.isOptionUseUCase(),true);
		assertEquals(setting.isOptionUseNumbers(),true);
		assertEquals(setting.isOptionUseSChars(),true);
		assertEquals(setting.getPasswordLength(),32);
		assertEquals(setting.getPasswordNotes(),"passwordNotes1");
		setting=list.get(1);
		assertEquals(setting.getPasswordName(),"passwordName2");
		assertEquals(setting.isOptionUseLCase(),false);
		assertEquals(setting.isOptionUseUCase(),false);
		assertEquals(setting.isOptionUseNumbers(),false);
		assertEquals(setting.isOptionUseSChars(),false);
		assertEquals(setting.getPasswordLength(),16);
		assertEquals(setting.getPasswordNotes(),null);
	}
	
	@Test 
	public void test_EncryptDecrypt()
	{
		ArrayList<PasswordSetting> list=new ArrayList<PasswordSetting>();
		list.add(new PasswordSetting("passwordName1",true,true,true,true,32,"passwordNotes1"));
		list.add(new PasswordSetting("passwordName2",false,false,false,false,16));
		String json=new Gson().toJson(list);
		try
		{
			String encryptedString=StringEncryptDecrypt.encrypt("I like pie",json);
			String decryptedString=StringEncryptDecrypt.decryptString("I like pie",encryptedString);
			assertEquals("[{\"passwordName\":\"passwordName1\",\"optionUseLCase\":true,\"optionUseUCase\":true,\"optionUseNumbers\":true,\"optionUseSChars\":true,\"passwordLength\":32,\"passwordNotes\":\"passwordNotes1\",\"version\":1},{\"passwordName\":\"passwordName2\",\"optionUseLCase\":false,\"optionUseUCase\":false,\"optionUseNumbers\":false,\"optionUseSChars\":false,\"passwordLength\":16,\"version\":1}]",decryptedString);
		}
		catch(Exception x)
		{
			fail(x.getMessage());
		}
	}

}
/*
NARPassword for Windows - Application to generate a non-random password
Copyright (C) 2011-2012 Hugues Johnson

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; version 2.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software 
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace narpaswin
{
    abstract class Narpas
    {
        public static class Constants
        {
            public static readonly string[] lchars=new string[] { "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z" };
            public static readonly string[] uchars=new string[] { "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z" };
            public static readonly string[] nums=new string[] { "0","1","2","3","4","5","6","7","8","9" };
            public static readonly string[] schars=new string[] { "!","@","#","$","%","^","&","*","-","=","+",":",";","?",",","." };
        }

        private static int computeChecksum(string s)
        {
            byte[] b=System.Text.Encoding.ASCII.GetBytes(s);
            int checksum=0;
            for(int i=0;i<b.Length;i++)
            {
                checksum=checksum^b[i];
            }
            return (checksum);
        }

        static MD5 md5Hash=null;
        private static string computeHash(string input,int minLength)
        {
            if(md5Hash==null)
            {
                md5Hash=MD5.Create();
            }
            StringBuilder sBuilder=new StringBuilder();
            while(sBuilder.Length<minLength)
            {
                byte[] data;
                if(sBuilder.Length==0)
                {
                    data=md5Hash.ComputeHash(Encoding.ASCII.GetBytes(input));
                }
                else
                {
                    data=md5Hash.ComputeHash(Encoding.ASCII.GetBytes(sBuilder.ToString()));
                }
                for(int i=0;i<data.Length;i++)
                {
                    sBuilder.Append(data[i].ToString("x2"));
                }
            }
            return(sBuilder.ToString());
        }

        private static int pickIndex(int i,int max)
        {
            if(i>=max)
            {
                i=i-((int)Math.Floor((decimal)i/(decimal)max)*max);
            }
            return (i);
        }

        public static string generatePassword(string passPhrase,string passwordName,bool useLCase,bool useUCase,bool useNumbers,bool useSpecialCharacters,int basePasswordLength)
        {
            //validations
            if((passPhrase.Length<1)||(passwordName.Length<1))
            {
                return("");
            }
            if(passPhrase.Equals(passwordName))
            {
                return("");
            }
            if((!useLCase)&&(!useUCase)&&(!useNumbers)&&(!useSpecialCharacters))
            {
                return("");
            }

            int passwordLength=8*basePasswordLength;

            //compute hashcodes
            string phraseHash=computeHash(passPhrase,passwordLength);
            byte[] phraseBytes=Encoding.ASCII.GetBytes(phraseHash);
            string nameHash=computeHash(passwordName,passwordLength);
            byte[] nameBytes=Encoding.ASCII.GetBytes(nameHash);

            //compute array order
            byte[] xorBytes=new byte[phraseBytes.Length];
            for(int i=0;i<phraseBytes.Length;i++)
            {
                xorBytes[i]=(byte)((int)phraseBytes[i]^(int)nameBytes[i]);
            }

            //compute checksums which translate to starting points in hashCodes
            int selectedCharOffset=0;
            if(useLCase) { selectedCharOffset=selectedCharOffset^1; }
            if(useUCase) { selectedCharOffset=selectedCharOffset^2; }
            if(useNumbers) { selectedCharOffset=selectedCharOffset^4; }
            if(useSpecialCharacters) { selectedCharOffset=selectedCharOffset^8; }
            int startOffset=basePasswordLength*selectedCharOffset;
            int phraseStart=computeChecksum(passPhrase)+startOffset;
            int phraseIndex=pickIndex(phraseStart,phraseHash.Length);
            int nameStart=computeChecksum(passwordName)+startOffset;
            int nameIndex=pickIndex(nameStart,phraseHash.Length);

            //other stuff used to build the password
            int arrayIndex=basePasswordLength;
            ArrayList lowerCase=new ArrayList();
            ArrayList upperCase=new ArrayList();
            ArrayList numbers=new ArrayList();
            ArrayList specialChars=new ArrayList();
            ArrayList arrayOrder=new ArrayList();

            StringBuilder password=new StringBuilder();
            while(password.Length<passwordLength)
            {
                //pick the next array to select characters from
                if(arrayOrder.Count==0)
                {
                    if(useLCase) { arrayOrder.Add(0); }
                    if(useUCase) { arrayOrder.Add(1); }
                    if(useNumbers) { arrayOrder.Add(2); }
                    if(useSpecialCharacters) { arrayOrder.Add(3); }
                }
                int pickArray=pickIndex(xorBytes[arrayIndex],arrayOrder.Count);
                int nextArray=(int)arrayOrder[pickArray];
                arrayOrder.RemoveAt(pickArray);

                //pick the next character for the password
                int nextIndex=phraseBytes[phraseIndex]*nameBytes[nameIndex];
                if(nextArray==0) //lower case
                {
                    if(lowerCase.Count==0)
                    {
                        lowerCase.AddRange(Narpas.Constants.lchars);
                    }
                    int lcharIndex=pickIndex(nextIndex,lowerCase.Count);
                    password.Append(lowerCase[lcharIndex]);
                    lowerCase.RemoveAt(lcharIndex);
                }
                else if(nextArray==1) //upper case
                {
                    if(upperCase.Count==0)
                    {
                        upperCase.AddRange(Narpas.Constants.uchars);
                    }
                    int ucharIndex=pickIndex(nextIndex,upperCase.Count);
                    password.Append(upperCase[ucharIndex]);
                    upperCase.RemoveAt(ucharIndex);
                }
                else if(nextArray==2) //numbers
                {
                    if(numbers.Count==0)
                    {
                        numbers.AddRange(Narpas.Constants.nums);
                    }
                    int numIndex=pickIndex(nextIndex,numbers.Count);
                    password.Append(numbers[numIndex]);
                    numbers.RemoveAt(numIndex);
                }
                else //special characters
                {
                    if(specialChars.Count==0)
                    {
                        specialChars.AddRange(Narpas.Constants.schars);
                    }
                    int scharIndex=pickIndex(nextIndex,specialChars.Count);
                    password.Append(specialChars[scharIndex]);
                    specialChars.RemoveAt(scharIndex);
                }

                //increment indexes
                phraseIndex++;
                if(phraseIndex==phraseBytes.Length) { phraseIndex=0; }
                nameIndex++;
                if(nameIndex==nameBytes.Length) { nameIndex=0; }
                arrayIndex++;
                if(arrayIndex==xorBytes.Length) { arrayIndex=0; }
            }

            return(password.ToString());
        }
    }
}

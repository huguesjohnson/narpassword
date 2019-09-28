/*
NARPassword for Windows - Application to generate a non-random password
Copyright (C) 2011-2012 Hugues Johnson

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

using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.Text;

namespace narpaswin
{
    [Serializable]
    [KnownType(typeof(string))]
    [DataContract]
    public class PasswordOptions : IComparable, IEquatable<PasswordOptions>
    {
        [DataMember]
        public string Name { get; set; }

        [DataMember]
        public bool UseLowerCase { get; set; }
        
        [DataMember]
        public bool UseUpperCase { get; set; }

        [DataMember]
        public bool UseNumbers { get; set; }

        [DataMember]
        public bool UseSpecialCharacters { get; set; }

        [DataMember]
        public int Length { get; set; }

        public int CompareTo(object obj)
        {
            if(obj==null) { return(-1); }
            return (this.Name.CompareTo(((PasswordOptions)obj).Name));
        }

        public override bool Equals(object obj)
        {
            if(obj==null) { return(false); }
            if(!(obj is PasswordOptions)){ return(false); }
            return(this.Name.Equals(((PasswordOptions)obj).Name));
        }

        public bool Equals(PasswordOptions obj)
        {
            if(obj==null) { return (false); }
            return (this.Name.Equals(obj.Name));
        }

        public override String ToString() 
        {
            return(this.Name);        
        }

        public override int GetHashCode()
        {
            return(this.Name.GetHashCode());
        }
    }
}

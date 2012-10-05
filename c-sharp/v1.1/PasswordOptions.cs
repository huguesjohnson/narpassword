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

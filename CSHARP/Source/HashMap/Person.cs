/*	Author:	Dustin Anderson
*	Title:	Person.cs
*	Desc:	Lab3 Person class
*/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HashTable
{
    class Person : IKeyed
    {
        private string ssn;
        private string firstName;
        private string lastName;
        private string dob;

        public Person(string inLastName, string inFirstName, string inDob, string inSsn)
        {
            lastName = inLastName;
            firstName = inFirstName;
            dob = inDob;
            ssn = inSsn;
        }

         uint IKeyed.getKey()
        {
            string[] ssnStrings;
            ssnStrings = ssn.Split(new char[] { '-', ' ' });
            string convertString;
            convertString = string.Concat(ssnStrings);
            uint intSsn;
            intSsn = (uint)Int32.Parse(convertString);
            return intSsn;
        }
    }


    interface IKeyed
    {
        uint getKey();
    }
}

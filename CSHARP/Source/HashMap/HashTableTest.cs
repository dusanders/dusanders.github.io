/*	Author:	Dustin Anderson
*	Title:	Test.cs
*	Desc:	Lab3 test class;
*/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HashTable
{
    class Test
    {
        public static void Main()
        {
            String[] firstNames = {"Dustin", "Angie", "Robert", "Sarah"};
            string[] lastNames = {"Oslund", "Anderson", "Johnson", "Colbert"};
            string[] ssns = {"123-45-1234", "543-34-3453" , "234-54-2367" , "657-17-8907"};
            string[] dobs = {"8/13/86" , "01/13/87" , "12/13/22", "02/23/12"};


            Person person1 = new Person(lastNames[0], firstNames[0], dobs[0], ssns[0]);
            Person person2 = new Person(lastNames[1], firstNames[1], dobs[1], ssns[1]);
            Person person3 = new Person(lastNames[2], firstNames[2], dobs[2], ssns[2]);
            Person person4 = new Person(lastNames[3], firstNames[3], dobs[3], ssns[3]);
            Person[] people = { person1, person2, person3, person4 };
            HashTable table = new HashTable(15, false);

            table.addItem(people[0]);
            table.addItem(people[1]);
            table.addItem(people[2]);
            table.addItem(people[3]);

            for (int i = 0; i < 10; i++)
            {
                table.addItem(person1);
            }
           table.retrieveItem(ref person3);
           HashTable table2 = (HashTable)table.Clone();
           table2.retrieveItem(ref person4);
		   table.deleteItem(ref person2);
        }
    }
}

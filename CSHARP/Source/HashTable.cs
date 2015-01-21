/*	Author:	Dustin Anderson
*	Title:	HashTable.cs
*	Desc:	Lab3
*/


using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace HashTable
{
    class HashTable : ICloneable
    {
        private object[] items;
        private bool linearProbing;

        private uint maxSize;
        private uint currentItems;

        public HashTable(uint requestedSize, bool useLinearProbing = false)
        {
            maxSize = requestedSize;
            linearProbing = useLinearProbing;
            currentItems = 0;
            uint size = requestedSize;
            if(!useLinearProbing)
            {
                size = checkSize(maxSize);
            }
            items = new object[size];
        }

        public void addItem<T>(T passedItem)
            where T : IKeyed
        {
            currentItems++;
            if (currentItems <= maxSize)
            {
                uint key = passedItem.getKey();
                uint hashCode = hashFunction(key);
                if (items[hashCode] != null)
                {
                    if (linearProbing)
                    {
                        int prober = (int)hashCode++;
                        while (items[prober] != null)
                        {
                            if (prober == hashCode)
                            {
                                throw new Exception("Hash table full!");
                            }
                            if (prober == items.Length -1)
                            {
                                prober = -1;
                            }
                            prober++;
                        }
                        items[prober] = passedItem;
                        return;
                    }//end if(linearProbing)
                    else if (!linearProbing)
                    {
						int quadState = 0;
						int quadOffset = 1;
                        bool stored = false;
                        while (!stored)
                        {
                            switch (quadState)
                            {
                                case 0:
                                    int posPointer = (int)hashCode;
                                    for (int i = 0; i < quadOffset * quadOffset; i++)
                                    {
                                        if (posPointer == items.Length - 1)
                                        {
                                            posPointer = -1;
                                        }
                                        posPointer++;
                                    }
                                    if (items[posPointer] != null)
                                    {
                                        quadState = 1;
                                        break;
                                    }
                                    items[posPointer] = passedItem;
                                    quadState = 1;
                                    stored = true;
                                    break;

                                case 1:
                                    uint negPointer = hashCode;
                                    for (int i = 0; i < quadOffset * quadOffset; i++)
                                    {
                                        if (negPointer == 0)
                                        {
                                            negPointer = (uint)items.Length;
                                        }
                                        negPointer--;
                                    }
                                    if (items[negPointer] != null)
                                    {
                                        quadOffset++;
                                        quadState = 0;
                                        break;
                                    }
                                    items[negPointer] = passedItem;
                                    quadOffset++;
                                    if (quadOffset == items.Length - 1)
                                    {
                                        quadOffset = 1;
                                    }
                                    quadState = 0;
                                    stored = true;
                                    break;
                            }//end switch  
                        }//end while
                        return;
                    }//end elseif
                }//end if items[hashCode] != null
                items[hashCode] = passedItem;
            }//end if current < maxsize
            else
            {
                throw new Exception("HashTable full!");
            }
        }

        public bool retrieveItem<T>(ref T passedItem)
            where T : IKeyed
        {
            uint key = passedItem.getKey();
            uint hashCode = hashFunction(key);
            if(linearProbing)
            { 
                int prober = (int)hashCode;
                T tableItem = (T)items[prober];      
                while (tableItem.getKey() != passedItem.getKey())
                {
					prober++;
                    if (prober == hashCode)
                    {
                        throw new Exception("Item not found!");
                    }
                    else if (prober == items.Length - 1)
                    {
                        prober = 0;
                    }
                    tableItem = (T) items[prober];
                }
                if (items[prober].GetType().Equals(passedItem.GetType()))
                {
                    passedItem = (T)items[prober];
                    return true;
                }
                return false;
            }//end if(linearProbing)
            else
            {
                uint state = 0;
                uint offset = 1;
                uint prober = hashCode;
                T tableItem = (T) items[prober];
                while(tableItem.getKey() != passedItem.getKey())
                {
                    switch(state)
                    {
                        case 0:
                            uint posPointer = hashCode;
                            for (int i = 0; i < offset * offset; i++)
                            {
                                posPointer++;
                                if(posPointer == items.Length)
                                {
                                    posPointer = 0;
                                }
                            }
                            tableItem = (T)items[posPointer];
                            if(tableItem.getKey() == passedItem.getKey() && items[posPointer].GetType().Equals(passedItem.GetType()))
                            {
                                passedItem = (T) items[posPointer];
                                return true;
                            }
                           state = 1;
                            break;
                        case 1:
                            int negPointer = (int) hashCode;             
                            for (int i = 0; i < offset * offset; i++)
                            {
                                negPointer--;
                                if(negPointer == 0)
                                {
                                    negPointer = items.Length - 1;
                                }
                            }
                            tableItem = (T)items[negPointer];
                            if (tableItem.getKey() == passedItem.getKey() && items[negPointer].GetType().Equals(passedItem.GetType()))
                            {
                                passedItem = (T)items[negPointer];
                                return true;
                            }
                            offset++;
                            if(offset == items.Length - 1)
                            {
                                throw new Exception("Item not found!");
                            }
                            state = 0;
                            break;
                    }//end switch
                }//end while
                passedItem = (T)items[prober];
                return true;
            }
        }

		public bool deleteItem<T>(ref T passedItem) where T : IKeyed
        {
            uint key = passedItem.getKey();
            uint hashCode = hashFunction(key);
            if(linearProbing)
            { 
                int prober = (int)hashCode;
                T tableItem = (T)items[prober];      
                while (tableItem.getKey() != passedItem.getKey())
                {
                    if (prober++ == hashCode)
                    {
                        throw new Exception("Item not found!");
                    }
                    else if (prober == items.Length - 1)
                    {
                        prober = -1;
                    }
                    tableItem = (T) items[prober];
                }
                if (items[prober].GetType().Equals(passedItem.GetType()))
                {
                    items[prober] = null;
					passedItem =(T) items[prober];
					currentItems--;
                    return true;
                }
                return false;
            }//end if(linearProbing)
            else
            {
                uint state = 0;
                uint offset = 1;
                uint prober = hashCode;
                T tableItem = (T) items[prober];
                while(tableItem.getKey() != passedItem.getKey())
                {
                    switch(state)
                    {
                        case 0:
                            uint posPointer = hashCode;
                            for (int i = 0; i < offset * offset; i++)
                            {
                                posPointer++;
                                if(posPointer == items.Length)
                                {
                                    posPointer = 0;
                                }
                            }
                            tableItem = (T)items[posPointer];
                            if(tableItem.getKey() == passedItem.getKey() && items[posPointer].GetType().Equals(passedItem.GetType()))
                            {
								items[prober] = null;
								passedItem = (T)items[prober];
								currentItems--;
                                return true;
                            }
                           state = 1;
                            break;
                        case 1:
                            int negPointer = (int) hashCode;             
                            for (int i = 0; i < offset * offset; i++)
                            {
                                negPointer--;
                                if(negPointer == 0)
                                {
                                    negPointer = items.Length - 1;
                                }
                            }
                            tableItem = (T)items[negPointer];
                            if (tableItem.getKey() == passedItem.getKey() && items[negPointer].GetType().Equals(passedItem.GetType()))
                            {
								items[prober] = null;
								passedItem = (T)items[prober];
								currentItems--;
                                return true;
                            }
                            offset++;
                            if(offset == items.Length - 1)
                            {
                                throw new Exception("Item not found!");
                            }
                            state = 0;
                            break;
                    }//end switch
                }//end while
				items[prober] = null;
				passedItem = (T)items[prober];
				currentItems--;
                return true;
            }
        }

        public uint hashFunction(uint keyValue)
        {
            uint result = keyValue;
            for (uint i = 0; i < 3; i ++ )
            {
                result = (result + 9) * (result * 3);
            }
                return result % maxSize;
        }

        public object Clone()
        {            
            HashTable result = new HashTable(maxSize, linearProbing);
			uint itemCount = checkSize(maxSize);
            for (int i = 0; i <itemCount ; i++ )
            {
                if (items[i] != null)
                {
					dynamic item = items[i];
                    result.addItem(item);
                }
            }
            return result;
        }



        private uint checkSize(uint passedSize)
        {
            uint result = passedSize;
            bool found = false;
            while (!found)
            {
                if (isPrime(result) && result % 4 == 3)
                {
                    found = true;
                }
                else if((result % 2 == 0) && (isPrime(result / 2)) && ((result / 2) % 4 == 3))
                {
                    found = true;
                }
                else
                {
                    result++;
                }
            }
            return result;
        }


        private bool isPrime(uint passedInt)
        {
            bool result = true;
            for(uint i=2; i*i<=passedInt; i++)
            {
                if(passedInt % i == 0)
                {
                    result = false;
                    break;
                }
            }
            return result;
        }
    }//end class HashTable
}

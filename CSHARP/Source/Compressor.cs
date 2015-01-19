/*Author:		Dustin Anderson
 * Title:			Compressor.cs
 * Desc:		This is intended to act as the Compressor/Decompressor class for compressing
 *						files. RootedBinaryTree class is also required.
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace FileCompression
{
	public class Compressor
	{
		private struct Word
		{
			public string plainWord;
			public double probability;
			public string codeWord;
		};

		public static void compress(string inputFileName, string outputFileName)
		{
			List<char> characterList = new List<char>();
			List<double> characterCount = new List<double>();
			double totalCharacters = 0;
			StreamReader inputReader = new StreamReader(inputFileName);	
			string inputString;
			while((inputString = inputReader.ReadLine()) != null)
			{
				inputString += Environment.NewLine;
				for(int i=0; i < inputString.Length; i++)
				{
					char currentChar = inputString[i];
					totalCharacters++;
					if (!(characterList.Contains(currentChar)))
					{
						characterList.Add(currentChar);
						characterCount.Insert(characterList.IndexOf(currentChar), 1);
					}
					else
					{
						double newCount = characterCount[characterList.IndexOf(currentChar)];
						newCount++;
						characterCount.RemoveAt(characterList.IndexOf(currentChar));
						characterCount.Insert(characterList.IndexOf(currentChar), newCount);
					}
				}
			}
			inputReader.Dispose();
			inputReader.Close();

			List<Word> wordList = new List<Word>();
			Word newWord = new Word();
			//initialize the List with a first element
			newWord.plainWord = characterList[0].ToString();
			newWord.probability = characterCount[0] / totalCharacters;
			wordList.Add(newWord);
			for(int i = 1; i < characterList.Count; i++)
			{
				newWord = new Word();
				newWord.plainWord = characterList[i].ToString();
				newWord.probability = characterCount[i] / totalCharacters;
				//sort Words as we build the List
				for (int j = 0; j < wordList.Count; j++)
				{
					if (newWord.probability < wordList[j].probability)
					{
						wordList.Insert(j, newWord);
						break;
					}
					else if (j == wordList.Count - 1)
					{
						wordList.Add(newWord);
						break;
					}
				}
			}
			Word[] theWords = wordList.ToArray<Word>();
			//form the huffman tree
			huffman(theWords);
			//get the output file
			FileStream outputFile = File.OpenWrite(outputFileName);

			//print the dictionary to binary file
			byte writeByte = 0;
			int counter = 0;
			int stringPointer = 0;
			//write total pairs
			outputFile.WriteByte((byte)theWords.Length);
			for (int i = 0; i < theWords.Length; i++)
			{
				//write code word
				byte testByte = (byte)theWords[i].plainWord[0];
				char testChar = (char)testByte;
				outputFile.WriteByte((byte)theWords[i].plainWord[0]);
				//write code word length
				outputFile.WriteByte((byte)theWords[i].codeWord.Length);
				//write the code word
				for (int j = 0; j < theWords[i].codeWord.Length; j++)
				{
					writeByte *= 2;
					counter++;
					writeByte += (byte)(theWords[i].codeWord[j] - '0');
					if(counter ==8)
					{
						outputFile.WriteByte(writeByte);
						counter = 0;
						writeByte = 0;
					}
				}
				//pad the (end) of the last byte
				while (counter < 8)
				{
					writeByte *= 2;
					counter++;
				}
				outputFile.WriteByte(writeByte);
				writeByte = 0;
				counter = 0;
			}

			//print the document to binary file
			inputReader = new StreamReader(inputFileName);
			inputString = "";
			//while we have a string to read
			while((inputString = inputReader.ReadLine()) != null)
			{	//for every character in inputString
				inputString += Environment.NewLine;
				for(int i=0; i<inputString.Length; i++)
				{	//find the correct Word object
					for(int j=0; j<theWords.Length; j++)
					{	//once we find the correct Word object
						if(inputString[i] == theWords[j].plainWord[0])
						{	//print the code word of the Word object
							for (int w = 0; w < theWords[j].codeWord.Length; w++)
							{	//shift our byte
								writeByte *= 2;
								counter++;
								//add our value
								writeByte += (byte)(theWords[j].codeWord[w] - '0');
								if (counter == 8)
								{	//write the byte once we hit 8 bits;
									outputFile.WriteByte(writeByte);
									counter = 0;
									writeByte = 0;
								}
							}
						}
					}
				}
			}
			//print the last byte - pad right to make full byte
			while (counter < 8)
			{
				writeByte *= 2;
				counter++;
			}
			outputFile.WriteByte(writeByte);
			outputFile.Close();
		}//end compress()

		public static void decompress(string inputFileName, string outputFileName)
		{
			FileInfo outputFile = new FileInfo(outputFileName);
			FileInfo inputFile = new FileInfo(inputFileName);
			FileStream inputStream = inputFile.OpenRead();
			FileStream outputStream = outputFile.OpenWrite();
			StreamWriter outputWriter = new StreamWriter(outputStream);
			BinaryReader binaryReader = new BinaryReader(inputStream);
			List<Word> theWords = new List<Word>();
			
			//inputString used later for reading inputFile
			string inputString = "";
			int bitPadding = 0;
			int numOfPairs = binaryReader.ReadByte();
			int totalPairs = numOfPairs;
			string plainWordString="";
			string binaryString = "";
			string codeString = "";
			while (numOfPairs > 0)
			{
				plainWordString = Convert.ToString((char)binaryReader.Read());
				int codeWordLength = 0;
				codeWordLength = binaryReader.ReadByte();
				//find amount of bytes to read for codeword
				int bytesToRead = codeWordLength / 8;
				//round up
				bytesToRead++;
				while (bytesToRead > 0)
				{
					string buf = Convert.ToString(binaryReader.ReadByte(), 2);
					if (buf.Length < 8)
					{
						buf = buf.PadLeft(8, '0');
					}
						binaryString += buf;
						bytesToRead--;
				}
				for (int i = 0; i < codeWordLength; i++)
				{
					codeString += binaryString[i];
				}
				binaryString = binaryString.Remove(0, codeWordLength);
				//create and add our new Word to theWord List
				Word newWord = new Word();
				newWord.plainWord = plainWordString;
				newWord.codeWord = codeString;
				theWords.Add(newWord);
				numOfPairs--;
				codeString = "";
				binaryString = "";
			}//done while(numpairs > 0) -done getting dictionary
			int minCodeWordLength = theWords[totalPairs - 1].codeWord.Length;

			//begin reading compressed file.
			inputString = "";
			string buffer = "";
			bool matched;
			//while our stream has elements to read...
			while(binaryReader.BaseStream.Position != binaryReader.BaseStream.Length)
			{	//setup 'matched' variable for matching strings
				matched = false;
				while(!matched)
				{	//need to 'try' this to catch unexpected EOF/ end of stream exceptions
					try
					{	//get a byte
						buffer = Convert.ToString(binaryReader.ReadByte(), 2);
					}
					catch (Exception e)
					{	//CATCH EOF
						break;
					}
					//'normalize' our byte, we need leading zeros back for the code words
					if (buffer.Length < 8)
					{
						buffer = buffer.PadLeft(8, '0');
					}
					//add to inputString
					inputString += buffer;
					//setup string for matching the codewords
					string matcherString = "";
					//set to -1 for looping
					int matchPointer = -1;
					//while !matched AND our inputString is longer than the min codeword length...
					while (!matched && inputString.Length >= minCodeWordLength)
					{
						matchPointer++;
						try
						{	//we may 'run out' of characters in the inputString, catch this exception.....
							matcherString += inputString[matchPointer];
						}	//we simply break and get more characters for the 'inputString'
						catch (Exception e) { break; }
						//match the read strings to the dictionary string
						for (int i = 0; i < theWords.Count; i++)
						{
							if (matcherString == theWords[i].codeWord)
							{
								//write the found character
								outputWriter.Write(theWords[i].plainWord);
								//remove this from the 'inpurString'
								inputString = inputString.Remove(0, theWords[i].codeWord.Length);
								//reset for looping
								matchPointer = -1;
								matcherString = "";
								break;
							}
						}
						if(inputString.Length == 0)
						{
							matched = true;
						}
					}//end while (!matched && inputString.Length >= minCodeWordLength)
				}//end while(!matched)
			}//end while(binaryReader.BaseStream.Position != binaryReader.BaseStream.Length)
			//close our streams
			outputWriter.Dispose();
			outputStream.Dispose();
		}//end decompress()

		private static void huffman(Word[] theWords)
		{
			List<RootedBinaryTree<Word>> treeArray = new List<RootedBinaryTree<Word>>();
			//make our initial list of binary trees
			for (int i = 0; i < theWords.Length; i++)
			{
				treeArray.Add(new RootedBinaryTree<Word>(theWords[i]));
			}
			//combine trees
			while (treeArray.Count > 1)
			{
				Word newWord = new Word();
				newWord.plainWord = null;
				newWord.codeWord = null;
				//combine the probabilities
				newWord.probability = treeArray.ElementAt(0).getData().probability + treeArray.ElementAt(1).getData().probability;
				//make a new tree for the combined trees.
				RootedBinaryTree<Word> combinedTrees = new RootedBinaryTree<Word>(newWord);
				combinedTrees.combineTrees(treeArray.ElementAt(0), treeArray.ElementAt(1));
				//make sure we are at root
				combinedTrees.toRoot();
				int newTreeIndex = 0;
				//insert tree
				while ((combinedTrees.getData().probability >= treeArray.ElementAt(newTreeIndex).getData().probability))
				{
					//did we reach the end?
					if (newTreeIndex == treeArray.Count - 1)
					{
						newTreeIndex++;
						break;
					}
					//if not, keep looping until we find our insert point
					newTreeIndex++;
				}
				//insert tree
				treeArray.Insert(newTreeIndex, combinedTrees);
				treeArray.RemoveAt(0);
				treeArray.RemoveAt(0);
			}//end while( >1)
			treeArray.ElementAt(0).toRoot();
			RootedBinaryTree<Word> combinedTree = treeArray.ElementAt(0);
			string codeString = "";
			assignCodes(ref codeString, theWords, combinedTree);
		}//end huffman()

		private static void assignCodes(ref string codeString, Word[] theWords, RootedBinaryTree<Word> combinedTree)
		{	//if we can go left...
			if (combinedTree.moveLeft())
			{	//adjust codeword string accordingly...
				codeString += '0';
				if (combinedTree.getData().plainWord != null)
				{	//if we have a character here....
					for (int i = 0; i < theWords.Length; i++)
					{	//find the character in our 'theWords' array...
						if (combinedTree.getData().plainWord == theWords[i].plainWord)
						{	//label this character with a codeword string
							theWords[i].codeWord = codeString;
							break;
						}
					}
				}
				//recusive call
				assignCodes(ref codeString, theWords, combinedTree);
				//back from recursion, remove a character from our codeword string
				codeString = codeString.Remove(codeString.Length - 1);
			}//end if(combinedTree.moveLeft())
			//do the same for right sides....
			if (combinedTree.moveRight())
			{
				codeString += '1';
				if (combinedTree.getData().plainWord != null)
				{
					for (int i = 0; i < theWords.Length; i++)
					{
						if (combinedTree.getData().plainWord == theWords[i].plainWord)
						{
							theWords[i].codeWord = codeString;
							break;
						}
					}
				}
				assignCodes(ref codeString, theWords, combinedTree);
				codeString = codeString.Remove(codeString.Length-1);
			}
			//explored all possibilities, move back up....
			combinedTree.moveUp();
		}
	}
}
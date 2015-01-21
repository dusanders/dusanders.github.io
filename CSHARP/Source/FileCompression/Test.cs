/*	Author:		Dustin Anderson
 * Title:			Test.cs
 * Desc:		This is intended to act as a main class for testing the Compressor/Decompressor
 *						classes used for file compression.
*/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace FileCompression
{
	public class Test
	{
		static void Main(string[] args)
		{
			string dir = Directory.GetCurrentDirectory();
			Console.WriteLine(dir);
			Compressor.compress(dir + "\\TextFile1.txt", dir + "\\output.bin");
			Compressor.decompress(dir + "\\output.bin", dir + "\\TextFile2.txt");
		}
	}
}

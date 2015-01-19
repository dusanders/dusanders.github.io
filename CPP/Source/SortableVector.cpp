/*	Author:		Dustin Anderson
 * 	Title:		VectorSort.cpp
 * 	Desc:		This is intended to show a template 'Vector' class
 * 				that can be used with different data types. The sorting
 * 				relies on the data type to implement method CompareTo() 
 * 				which expects an int from -1 to 1 based on the comparision.
 * 				My implementation uses a struct which implements this method.
 * 				The vector also requires the data type have a ToString() method
 * 				for printing the object.
 * 
 * 				File redirection is required on the command line when launching.
 */



#include <iostream>
#include <sstream>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "SortableVector.h"
using namespace std;

//struct for book object
struct Book
{
	string	title;
	float	bookprice;
	int CompareTo(Book *otherBook)
	{
		if(bookprice < otherBook->bookprice)
		{
			return 1;
		}
		else if(bookprice > otherBook->bookprice)
		{
			return -1;
		}
		else
		{
			return 0;
		}
		
	}
	string ToString()
	{	
		ostringstream ostream;
		ostream << bookprice << " " << title << '\n';
		string result = ostream.str();
		return result;
	}		
};
//pointer for books
typedef Book* bookPtr;

int main()
{
	//create string for input
	string inputString;
	//create our custom vector class object
	SortableVector<bookPtr> bookList;
	//while we have a file via redirection
	while(getline(cin, inputString))
	{
		//create new Book struct
		bookPtr b = new Book;
		//create C-string for tokenizer
		char Cinput [inputString.length()+1];
		//copy our string into C-string
		strcpy(Cinput, inputString.c_str());
		//get a token pointer
		char* token;
		//tokenize the string
		token = strtok(Cinput, " ");
		//pull the price
		float price = atof(token);
		while(token != NULL)
		{
			token = strtok(NULL, "\n");
			if(token != NULL)
			{
				//set the name of the 'Book' struct
				string titlestr(token);
				b->title = titlestr;
			}
		}//end tokenize
		//set the price of the 'Book' struct
		b->bookprice = price;
		//add the 'Book' to our vector object
		bookList.push_back(b);
	}//end read input
	//print unsorted list
	bookList.print();
	cout << endl;
	bookList.sortVectorAscending();
	bookList.print();
	cout << endl;
	bookList.sortVectorDescending();
	bookList.print();	
	cout << endl;
	bookPtr min = bookList.min();
	cout << min->ToString();
	return 0;
}


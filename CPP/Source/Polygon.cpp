/*	Author:		Dustin Anderson
*	Title:		Polygon.cpp
*	Problems:	
*	Desc:		This program is intended to read an input text file.
*				 The text file will contain segments in the form; (x1,y1,x2,y2).
*				 The program will take these segments and arrange the output
*				 to form a polygon. File read is expected as file re-direction 
* 				 on command line (./Polygon < <file>.dat).
*/

#include <iostream>
#include <cstring>
#include <stdlib.h>
#include <vector>
using namespace std;

//struct to hold the points
struct point2D
{
	//x-value
	int x;
	//y-value
	int y;
};//end point struct


//struct to hold the segments
struct segment2D
{
	point2D p1;
	point2D p2;
};//end segment struct


//class polygon to hold list of segments
class Polygon
{
	private:
		//struct for linked list
		struct listNode
		{
			segment2D seg;
			listNode* next;
			listNode* prev;
		};//end struct
		//head for list
		listNode* head;
	public:
	
		//default constructor
		Polygon()
		{
			head = 0;
		}//end default constructor
		
		~Polygon()
		{
			//while we have elements
			while(head!=0)
			{
				listNode* tmp = head;
				head = head->next;
				//delete element
				delete tmp;
			}//end while
		};//end destructor
		
		//method to insert elements into list
		void insert(segment2D &segment)
		{
			listNode *tmp = new listNode;
			//first element to list?
			if(head==0)
			{
				(*tmp).seg = segment;
				tmp->next = head;
				tmp->prev = head;
				head = tmp;
			}//end if first element
			else
			{
				//find the head
				tmp = head;
				//get to the tail
				while (tmp->next)
					tmp = tmp->next;
				//create new node for tail
				listNode *newNode = new listNode;
				//set data
				(*newNode).seg = segment;
				//place new node
				tmp->next = newNode;
				newNode->prev = tmp;
			}//end else
		}//end insert()
		
		//method to sort the segments into a polygon
		void sort()
		{
			//starting point
			listNode *ptr = head;
			listNode *temp = ptr->next;
			while((ptr->seg.p2.x != temp->seg.p1.x || ptr->seg.p2.x != temp->seg.p1.x) && temp->next)
			{
				temp = temp->next;
			}			
			(temp->prev)->next = 0;
			temp->next = ptr->next;
			temp->prev = ptr;
			ptr->next = temp;
			swap();
		}//end sort()
		
		//method to swap the points within a segment object
		void swap()
		{
			//current position in list
			listNode *curr = head;
			//while we have a list
			while(curr->next)
			{
				//are we at head?
				if(!curr->prev)
				{	//move to next element
					curr = curr->next;
				}//end if at head
				//do we have a 'mixed up' element?
				if((curr->seg.p2.x == curr->prev->seg.p2.x) &&
					(curr->seg.p2.y == curr->prev->seg.p2.y))
				{
					//swap the values
					//get the values
					int x1 = curr->seg.p1.x;
					int y1 = curr->seg.p1.y;
					int x2 = curr->seg.p2.x;
					int y2 = curr->seg.p2.y;
					//swap the values
					curr->seg.p1.x = x2;
					curr->seg.p1.y = y2;
					curr->seg.p2.x = x1;
					curr->seg.p2.y = y1;
				}//end if mixed element
				//next element
				curr = curr->next;
			}//end while in list
		}//end swap()
		
		//method to print the polygon list
		void printPolygon()
		{
			//get the head
			listNode *ptr = head;
			//while we are in list
			while(ptr)
			{
				//output the points
				cout<< ptr->seg.p1.x << " " << ptr->seg.p1.y
					<< " " << ptr->seg.p2.x << " " << ptr->seg.p2.y << endl;
				ptr = ptr->next;
			}//end while in list
		}//end print()
};//end Polygon class declaration

int main()
{
	//new Polygon list
	Polygon polygon;
	//input string
	string inputStream;
	//vector to hold input integers
	vector<int> inputInt;
	//while we have input
	while(getline(cin, inputStream))
	{
		//c-string for tokenizer
		char CinputStream[inputStream.length()];
		//get the string
		strcpy(CinputStream, inputStream.c_str());
		//tokenize the string
		char* token = strtok(CinputStream, " ");
		//while we have tokens
		while(token)
		{
			//get the integer
			int x = atoi(token);
			inputInt.push_back(x);
			//get rest of string
			token = strtok(NULL," ");
		}//end while tokens
		//get the values from the vector
		int y2 = inputInt.back();
		inputInt.pop_back();
		int x2 = inputInt.back();
		inputInt.pop_back();
		int y1 = inputInt.back();
		inputInt.pop_back();
		int x1 = inputInt.back();
		inputInt.pop_back();
		//create new point objects
		point2D p1;
		p1.x = x1;
		p1.y = y1;
		point2D p2;
		p2.x = x2;
		p2.y = y2;
		//create new segment
		segment2D segment;
		segment.p1 = p1;
		segment.p2 = p2;
		polygon.insert(segment);
	}//end while input
	
	//print the polygon members
	cout << "Original List" << endl;
	polygon.printPolygon();
	//sort the list
	polygon.swap();
	polygon.sort();
	//print sorted list
	cout << endl;
	cout << "Sorted List" << endl;
	polygon.printPolygon();
	//exit program
	return 0;
}//end main

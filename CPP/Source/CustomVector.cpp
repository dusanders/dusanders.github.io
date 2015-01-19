/*  Author:     Dustin Anderson
*   Title:      CustomVector.cpp
*   Problems:   None.
*   Desc:       This program is intended to create template functions for sorting
*               a vector. The program will use a linked list as a 'custom' vector.
* 				The program will then sort the vector and print the resulting vector.
* 				The STL is not used.
*/

#include <iostream>
#include <cstring>
using namespace std;

//template declaration
template <class T>
//class declaration
class SortableVector
{
private:
	//struct for nodes within linked list
    struct Node
    {
		//store the data
        T data;
        //store pointer to next node
        Node *next;
    };//end struct
    //starting position
    Node *head;

public:
	//default constructor
    SortableVector()
    {
        head = 0;
    }//end default constructor
    ~SortableVector()
    {
        //delete the nodes
        while(head!=0)
        {
            Node *tmp = head;
            head = head->next;
            delete tmp;
        }
    }//end destructor
    //method to add elements to Vector
    void push_back(T &inData)
    {
        //if the vector is empty
        if(head==0)
        {
            Node *tmp = new Node;
            (*tmp).data = inData;
            tmp->next = head;
            head = tmp;
        }//end if vect empty
        //else...
        else
        {
            //create tmp Node
            Node *tmp = head;
            //find the end of list
            while(tmp->next!=0)
                tmp = tmp->next;
            Node *newNode = new Node;
            (*newNode).data = inData;
            tmp->next = newNode;
        }
    }//end push_back()
    //method to print the Vector
    void print()
    {
        //set the starting position
        Node *ptr = head;
        while(ptr != 0)
        {
            //print the data
            cout << ptr->data << ',';
            //get next node
            ptr = ptr->next;
        }
        //newline
        cout<<endl;
    }//end print()
    //method to sort the vector
    void sortVector()
    {
		//set the starting point
		Node *ptr = head;
		//temp node for swapping values
		Node *tmp = new Node;
		//while we are in the list
		while(ptr->next != 0)
		{
			//is our current data BIGGER than the next?
			if((ptr->data > ptr->next->data))
			{
				//temp. place value
				tmp->data = ptr->data;
				//swap current to next value
				ptr->data = ptr->next->data;
				//restore temp value to the next node
				ptr->next->data = tmp->data;
				//recall the function
				sortVector();
			}//end if current > next
			//else just forward the list
			else
			{
				ptr = ptr->next;
			}//end else
		}//end while we are in list
    }//end sortVector
};//end class Vector

int main()
{
    //open a new SortableVector for ints
    SortableVector<int> intVect;
    //open a new SortableVector for strings
    SortableVector<string> stringVect;
    //input storage for integers
    int inputInt;
    //prompt the user
    cout << "Enter integers, end with non-integer" << endl;
    //get the input
    while(cin >> inputInt)
		//push it into vector
        intVect.push_back(inputInt);
    //clear the input stream for next input
    cin.clear();
    //input storage for strings
    string inputString;
    //prompt user
    cout << "Enter a string, end with ctrl-D" << endl;
    //get the input
    while(getline(cin, inputString))
    {
		//token holder
		char* token;
		//c-conversion
		char CinputString[inputString.length()];
		//copy input into c conversion char array
		strcpy(CinputString, inputString.c_str());
		//tokenize the string
		token = strtok(CinputString, " ");
		//while we have tokens
		while(token)
		{		
			//convert back to c++ string
			string stringToken = token;
			//push it into vector
			stringVect.push_back(stringToken);
			//continue tokenizing
			token = strtok(NULL, " "); 
		}//end while we have tokens
	}//end string input while loop
	//print the string tokens
	stringVect.print();
	//sort the integer vector
	cout <<endl;
	intVect.sortVector();
	cout<<endl;
	//print the sorted vector
	cout << "sorted: "<<endl;
	intVect.print();
	cout << endl;
	//sort the string vector
	stringVect.sortVector();
	cout<<endl;
	//print the sorted strings
	cout << "Sorted alphabetically: "<<endl;
	stringVect.print();
	//exit program
    return 0;
}

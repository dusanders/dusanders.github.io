/*	Author:		Dustin Anderson
*	Title:		Duplicatetree.cpp
*	Problems:	None
*	Desc:		This program is intended to implement a tree-node list
*				list that allows for a 'middle' element. The middle
*				element will contain a duplicate input number. The
* 				program should delete one of these duplicate numbers.
*/

#include <iostream>
using namespace std;

//node struct
struct Node
{
	int data;
	Node *right;
	Node *left;
	Node *middle;
	Node *parent;
};//end node struct
//type def for Node objects
typedef Node *node;

void insert(node &N, int data)
{
	//open new node
	node newNode = new Node;
	newNode->data = data;
	//first tree element??
	if(N == 0)
	{
		//set the pointers
		newNode->right = newNode->left = newNode->middle = newNode->parent = 0;
		N = newNode;
	}//end if start of tree
	//else figure out where to place it.
	else
	{
		//open new node
		node t = N;
		//find correct place for node
		while (true)			
		{
			//if we have MIDDLE object
			if (t->data == data)		
			{
				//is it the last middle object?
				if(t->middle == 0)
				{	//place the object
					t->middle = newNode;
					newNode->parent = t;
					cout << "Middle" <<endl;
					return;
				}//end if last middle
				//else keep going down the middle
				else
				{
					t = t->middle;
					continue;
				}//end else middle tranverse
			}//end if MIDDLE object
			
			//if we have a RIGHT object
			if (t->data < data)		
			{
				//are we at the right-most object?
				if (t->right == 0)		
				{	//place new node
					t->right = newNode;		
					newNode->parent = t;		
					cout << "right" <<endl;
					return;
				}//end if end of right tree
				//else keep going right
				else 
				{
					t = t->right;		
					continue;			
				}//end else
			}//end if RIGHT object
			
			//do we need to go LEFT?
			if (t->data > data)		
			{	//did we find end of left?
				if (t->left == 0)		
				{	//place new node
					t->left = newNode;		
					newNode->parent = t;	
					cout << "left" << endl;
					return;
				}//end if
				//else keep going left
				else
					t = t->left;
			}//end if LEFT
		} // of while
	}//end else
}//end insert()

//method to return the maximum element.
node findMax(node n)
{
	//while there is a max..
	while(n->right)
		n = n->right;
	//return the max object
	return n;
}//end findMax()

//method to search the tree for an element
bool search(node N, int data)
{
	//if we have an empty tree, return false;
	if(N == 0)
		return false;
	//did we find the data?
	if(data == N->data)
		return true;
	//do we go right?
	if(data > N->data)
		return search(N->right, data);
	//do we go left?
	else if(data< N->data)
		return search(N->left, data);
}//end search()

//method to delete tree nodes
void deleteNode(node &N, int data)
{
	cout<<"Deleting..."<<endl;
	//find the node within the tree
	node tmp = N;
	while(tmp->data != data)
	{		
		//if LESS, go left
		if (data < tmp->data)
			tmp = tmp->left;
		//if GREATER, go right
		else if(data > tmp->data)
			tmp = tmp->right;
		//if EQUAL, go middle
		else if(data == tmp->data)
		{
			while(tmp->middle != 0)
			{
				tmp = tmp->middle;
			}
		}
	}//end finding while
	cout << "tmp " << (tmp->middle)->data << endl;
	//check for no child objects
	if((tmp->left == 0) && (tmp->right == 0) && (tmp->middle == 0))
	{
		//check for empty tree
		if(tmp == N)
			N = 0;
		else if (tmp->data < (tmp->parent)->data)
			(tmp->parent)->left = 0;
		else if (tmp->data > (tmp->parent)->data)
			(tmp->parent)->right = 0;
		else if (tmp->data == (tmp->parent)->data)
			(tmp->parent)->middle = 0;
		//delete node
		return;
	}//end if no child objects
	
	//check for one child
	if((tmp->left == 0) || (tmp->right == 0) || (tmp->middle == 0))
	{
		//if we are at the base
		if(tmp == N)
		{
			//check left
			if(N->left != 0)
			{
				N = N->left;
				tmp->left = 0;
			}
			//check right
			else if(N->right != 0)
			{
				N = N->right;
				tmp->right = 0;
			}
			//check middle
			else if(N->middle != 0)
			{
				N = N->middle;
				tmp->middle = 0;
			}
			//delete
			delete(tmp);
			return;
		}
		//not at base, check for child
		else
		{
			//if no right child
			if (tmp->right == 0)
			{
				//are we left of parent?
				if ((tmp->parent)->left == tmp)	
					//reset left
					(tmp->parent)->left = tmp->left; 	
				else
					//reset right
					(tmp->parent)->right = tmp->left;	
			}//end if no right
			//if no left
			if (tmp->left == 0)		
			{	//are we left of parent?
				if ((tmp->parent)->left == tmp)
					//reset
					(tmp->parent)->left = tmp->right;
				else
					//reset right
					(tmp->parent)->right = tmp->right;
			}//end if no left
			//delete the node
			delete(tmp);
			return;
		}//end else
	}//end if one child
	
	//two children
	node tmp2 = findMax(tmp->left);
	tmp->data = tmp2->data;
	//is there a left object?
	if (tmp2->left == 0)
	{
		if (tmp2 == tmp->left)
			//reset left
			tmp->left = 0;
		else
			//reset right
			(tmp2->parent)->right = 0;
	}//end if no left
	else
	{
		if (tmp2 == tmp->left)
			(tmp2->parent)->left = tmp2->left;
		else
			(tmp2->parent)->right = tmp2->left;
	}
	//delete
	delete(tmp2);
}//end deleteNode()



//method to print the tree
void printTree(node N)
{
	node temp = N;
	if(temp!=0)
	{
		//recursively print left elements
		printTree(temp->left);
		cout << temp->data << " ";
		//print the middle elements
		printTree(temp->middle);
		//print the right elements
		printTree(temp->right);
	}
}//end print tree()

//begin main()
int main()
{
	Node *tree = 0;
	int inputStream;
	//prompt user
	cout << "Enter integers, exit with non-integer input:" << endl;
	//while we have an input stream
	while(cin >> inputStream)
	{
		//insert the elements
		insert(tree, inputStream);
	}//end input while loop
	cin.clear();
	//print the tree
	printTree(tree);
	cout << endl;
	//prompt for delete
	cout << "Which node to delete?" << endl;
	//get the delete node
	cin >> inputStream;
	//make sure we have it, then, delete it
	if(search(tree, inputStream))
	{
		deleteNode(tree, inputStream);
	}
	else
		cout << "Element was not found!" << endl;
	//print altered tree
	printTree(tree);
	//exit program
	return 0;
}//end main()

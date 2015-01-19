/*	Dustin Anderson
 * 	SortableVector.h
 * 	This file is intended to act as the header file for the SortableVector class.
*/

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <iostream>
using namespace std;

template <class T>
class SortableVector
{
private:
    struct Node
    {
        T data;
        Node *next;
	};
    Node *head;

public:
    SortableVector(){head = 0;};
    ~SortableVector();
    T min();
    void push_back(T &inData);
    void print();
    void sortVectorAscending();
    void sortVectorDescending();
};

template <class T>
SortableVector<T>::~SortableVector()
{	//delete all the nodes
	while(head != 0)
	{
		Node *tmp = head;
		head = head->next;
		delete tmp;
	}
};//end ~SortableVector()

template <class T>
T SortableVector<T>::min()
{
	Node *currentMin = head;
	Node *tmp = head;
	while(tmp != 0)
	{
		if(tmp->data->CompareTo(currentMin->data) == 1)
		{
			currentMin->data = tmp->data;
		}
		else
		{
			tmp = tmp->next;
		}
	}
	return currentMin->data;
}

template <class T>
void SortableVector<T>::push_back(T &inData)
{
	if(head == 0)
	{
		Node *tmp = new Node;
		(*tmp).data = inData;
		tmp->next = head;
		head = tmp;
	}
	else
	{
		Node *tmp = head;
		while(tmp->next != 0)
		{
			tmp = tmp->next;
		}
		Node *newNode = new Node;
		(*newNode).data = inData;
		tmp->next = newNode;
	}
}

template <class T>
void SortableVector<T>::print()
{
	Node *ptr = head;
	while(ptr != 0)
	{
		cout << ptr->data->ToString();
		ptr = ptr->next;
	}
}

template <class T>
void SortableVector<T>::sortVectorAscending()
{
	Node *ptr = head;
	Node *tmp = new Node;
	while(ptr->next != 0)
	{
		if(ptr->data->CompareTo(ptr->next->data) == -1)
		{
			tmp->data = ptr->data;
			ptr->data = ptr->next->data;
			ptr->next->data = tmp->data;
			sortVectorAscending();
		}
		else
		{
			ptr = ptr->next;
		}
	}
}

template <class T>
void SortableVector<T>::sortVectorDescending()
{
	Node *ptr = head;
	Node *tmp = new Node;
	while(ptr->next != 0)
	{
		if(ptr->data->CompareTo(ptr->next->data) == 1)
		{
			tmp->data = ptr->data;
			ptr->data = ptr->next->data;
			ptr->next->data = tmp->data;
			sortVectorDescending();
		}
		else
		{
			ptr = ptr->next;
		}
	}
}
			
	


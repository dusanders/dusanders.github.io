/*	Author:		Dustin Anderson
 * Title:			RootedBinaryTree.cs
 * Desc:		This is intended to act as rooted binary trees for the Compressor/Decompressor
 *						class used for file compression.
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FileCompression
{
	public class RootedBinaryTree<T>
	{
		private class Node<T>
		{
			public T nodeData;
			public Node<T> leftChild;
			public Node<T> rightChild;
			public Node<T> parent;
		};
		private Node<T> root;
		private Node<T> currentPosition;

		public RootedBinaryTree(T rootData)
		{
			root = new Node<T>();
			root.nodeData = rootData;
			currentPosition = root;
		}

		public void toRoot()
		{
			currentPosition = root;
		}

		public bool moveLeft()
		{
			if(currentPosition.leftChild != null)
			{
				currentPosition = currentPosition.leftChild;
				return true;
			}
			return false;
		}

		public bool moveRight()
		{
			if (currentPosition.rightChild != null)
			{
				currentPosition = currentPosition.rightChild;
				return true;
			}
			return false;
		}

		public bool moveUp()
		{
			if(currentPosition.parent != null)
			{
				currentPosition = currentPosition.parent;
				return true;
			}
			return false;
		}

		public T getData()
		{
			return currentPosition.nodeData;
		}

		public void combineTrees(RootedBinaryTree<T> leftTree, RootedBinaryTree<T> rightTree)
		{
			root.leftChild = leftTree.root;
			root.rightChild = rightTree.root;
			leftTree.root.parent = root;
			rightTree.root.parent = root;
			root.parent = null;
			leftTree.currentPosition = root;
			rightTree.currentPosition = root;
		}

		public void setNodeData(T nodeData)
		{
			currentPosition.nodeData = nodeData;
		}
	}
}

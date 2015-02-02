/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaFileCompress;


public class RootedBinaryTree<T> {
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

    public boolean moveLeft()
    {
	if(currentPosition.leftChild != null)
	{
            currentPosition = currentPosition.leftChild;
            return true;
        }
            return false;
        }

        public boolean moveRight()
        {
            if (currentPosition.rightChild != null)
            {
                currentPosition = currentPosition.rightChild;
                return true;
            }
            return false;
        }

        public boolean moveUp()
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

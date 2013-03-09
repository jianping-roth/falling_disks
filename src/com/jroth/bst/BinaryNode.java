package com.jroth.bst;


public class BinaryNode<T> {
    BinaryNode<T> left;
    BinaryNode<T> right;
    final T userObject;
    public BinaryNode(BinaryNode<T> left, BinaryNode<T> right, T userObject) {
        this.left = left;
        this.right = right;
        this.userObject = userObject;
    }
    
    @Override
    public String toString() {
        return userObject.toString();
    }
}

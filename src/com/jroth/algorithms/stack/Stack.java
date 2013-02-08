package com.jroth.algorithms.stack;

public interface Stack {
    void push(int i);
    int pop();
    int minimum(); //should return the minimum element in the stack in O(1) steps
}

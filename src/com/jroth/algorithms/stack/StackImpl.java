package com.jroth.algorithms.stack;

public class StackImpl implements Stack {
    
    private final java.util.Stack<Integer> data = new java.util.Stack<Integer>();
    private final java.util.Stack<Integer> minStack = new java.util.Stack<Integer>();
    
    @Override
    public void push(int i) {
        data.push(i);

        if(minStack.size() == 0 || i < minStack.peek()) {
            minStack.push(i);
        } else {
            // duplicate previous min value
            minStack.push(minStack.peek());
        }
    }

    @Override
    public int pop() {
        if (!data.isEmpty()) {
            data.pop();
            minStack.pop();
        }
        
        throw new IllegalStateException("The stack is empty.");
    }

    @Override
    public int minimum() {
        if (!minStack.isEmpty()) {
            return minStack.peek();
        }
        
        throw new IllegalStateException("The stack is empty.");
    }
}

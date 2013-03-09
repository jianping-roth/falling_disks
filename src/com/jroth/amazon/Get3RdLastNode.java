package com.jroth.amazon;

import java.util.Stack;

/**
 * This is a test from my interview with Raghav Phadke <raghavp@amazon.com>
 */
public class Get3RdLastNode {
    static class Node {
        Node right;    
    }
    
    Node get3rdLastNode(Node root) {
        Node node1 = root;
        Node node2 = node1 != null ? node1.right : null;
        Node node3 = node2 != null ? node2.right : null;
       
        if (node3 == null) {
            return null;
        }
        
        while (true) {
            if (node3.right == null) {
                return node3;
            }
            
            node1 = node2;
            node2 = node3;
            node3 = node3.right;
        }
    }
    
    Node get3rdLastNodeStack(Node root) {
        Stack<Node> stack = new Stack<Node>();
        while (true) {
            if (root == null) {
                break;
            }
            stack.add(root);
            root = root.right;
        }
        
        for (int i = 0 ; i < 2; i++) {
            if (stack.pop() == null) {
                return null;
            }
        }
        
        return stack.pop();
    }
}

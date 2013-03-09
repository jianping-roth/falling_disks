package com.jroth.bst;

import java.util.Stack;

import com.jroth.bst.FindNode.NodeInfo;

public class TreeBuilder<T extends Comparable<T>> {
    BinaryNode<T> root = null;
    int subLeft = 0;
    int subRight = 0;
    
    public BinaryNode<T> addNode(T object) {
        if (root == null) {
            root = new BinaryNode<T>(null, null, object);
            return root;
        }
        
        addNode(root, object);
        if (object.compareTo(root.userObject) < 0) {
            subLeft++; 
        } else if (object.compareTo(root.userObject) > 0) {
            subRight++;
        }
        if (subLeft - subRight >= 2) {
            BinaryNode<T> rootBefore = root;
            BinaryNode<T> parent = findLargestParent(root.left);
            root = parent == null ? root.left : parent.right;
            if (parent != null) {
                parent.right = null;
                root.left = parent;
            }
            root.right = rootBefore;
            rootBefore.left = null;
            subLeft--;
            subRight++;
        } else if (subRight - subLeft >= 2) {
            // right is heavier: moving to the left
            BinaryNode<T> rootBefore = root;
            BinaryNode<T> parent = findSmallestParent(root.right);
            root = parent == null ? root.right : parent.left;
            if (parent != null) {
                parent.left = null;
                root.right = rootBefore.right;
            }
            root.left = rootBefore;
            rootBefore.right = null;
            subLeft++;
            subRight--;
        }
                
        return root;
    }
    
    private BinaryNode<T> findSmallestParent(BinaryNode<T> root) {
        BinaryNode<T> parent = null;
        while (root.left != null) {
            parent = root;
            root = root.left;            
        }
        return parent;
    }

    private BinaryNode<T> findLargestParent(BinaryNode<T> left) {
        BinaryNode<T> parent = null;
        while (root.right != null) {
            parent = root;
            root = root.right;            
        }
        return parent;
    }

    private void addNode(BinaryNode<T> parent, T object) {        
        int compare = object.compareTo(parent.userObject);
        if (compare == 0) {
            return;
        }
        
        if (compare < 0) {
            if (parent.left == null) {
                parent.left = new BinaryNode<T>(null, null, object);
                return;
            }
            addNode(parent.left, object);
            return;
        } 
        
        if (parent.right == null) {
            parent.right = new BinaryNode<T>(null, null, object);
            return;
        }
        
        addNode(parent.right, object);
    }

    /**
     * Ensure the tree can shift to right with min as the root.right
     */
    static void test1() {
        TreeBuilder<Integer> builder = new TreeBuilder<Integer>();

        builder.addNode(1);
        builder.addNode(2);
        builder.addNode(3);
        if (builder.root.left == null ) {
            System.out.println("wrong");
        }
        if (builder.root.right == null) {
            System.out.println("wrong");
        }
        if (builder.root.userObject != 2) {
            System.out.println("wrong");
        }

        if (builder.root.left.userObject != 1) {
            System.out.println("wrong");
        }

        if (builder.root.right.userObject != 3) {
            System.out.println("wrong");
        }

        builder.addNode(4);

        if (builder.root.right.right.userObject != 4) {
            System.out.println("wrong");
        }

        builder.addNode(5);

        if (builder.root.userObject != 3) {
            System.out.println("wrong");
        }

        if (builder.root.left.userObject != 2) {
            System.out.println("wrong");
        }
        if (builder.root.left.right != null) {
            System.out.println("wrong");
        }
        if (builder.root.right.userObject != 4) {
            System.out.println("wrong");
        }
    }

    /**
     * Ensure the tree can shift to right with min as the root.right
     */
    static void test2() {
        TreeBuilder<Integer> builder = new TreeBuilder<Integer>();

        builder.addNode(4);
        builder.addNode(6);
        builder.addNode(9);
        builder.addNode(8);
        builder.addNode(10);
        if (builder.root.userObject != 8 ) {
            System.out.println("wrong");
        }
        if (builder.root.left.userObject != 6) {
            System.out.println("wrong");
        }
        if (builder.root.right.userObject != 9) {
            System.out.println("wrong");
        }
        if (builder.root.left.left.userObject != 4) {
            System.out.println("wrong");
        }
        if (builder.root.left.right != null) {
            System.out.println("wrong");
        }
        if (builder.root.left.left.right != null) {
            System.out.println("wrong");
        }
        if (builder.root.left.left.userObject != 4) {
            System.out.println("wrong");
        }
        if (builder.root.right.left != null) {
            System.out.println("wrong");
        }
        if (builder.root.right.right.left != null) {
            System.out.println("wrong");
        }
    }

    public static void main(String[] args) {
        test1();
        test2();
    }
    
    public static void printTree(BinaryNode<Integer> tree) {
        System.out.println("Printing tree");
        Stack<NodeInfo> stack = new Stack<NodeInfo>();
        stack.push(new NodeInfo(tree, 0));
        while (!stack.isEmpty()) {
            NodeInfo curr = stack.pop();
            
            for (int i = 0; i < Math.abs(curr.level); i++) {
                System.out.print(curr.level < 0 ? "-" : "+");  
            }
            System.out.println(curr.node.userObject);
            if (curr.node.left != null) {
                stack.push(new NodeInfo(curr.node.left, -(curr.level+1)));
            }
            if (curr.node.right != null) {
                stack.push(new NodeInfo(curr.node.right, curr.level+1));
            }
        }
    }
}

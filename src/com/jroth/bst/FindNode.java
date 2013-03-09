package com.jroth.bst;


public class FindNode {

    public static void main(String[] args) {
        BinaryNode<Integer> tree = buildTree(10);
        TreeBuilder.printTree(tree);
        int target = tree.left.left.userObject;

        BinaryNode<Integer> found = findNode(tree, target);
        System.out.println ("Search for: " +  target);
        System.out.println (found);
    }

    private static int getRandomInt() {
        return (int) (Math.random() * 100000);
    }


    private static BinaryNode<Integer> buildTree(int size) {
        TreeBuilder<Integer> builder = new TreeBuilder<Integer>();
        for (int i = 0; i < size; i++) {
            builder.addNode(getRandomInt());
        }
        return builder.root;
    }

    private static BinaryNode<Integer> findNode( BinaryNode<Integer> root, Integer target) {
        if (root == null || target == null) {
            return null;
        }
        
        if (target.equals(root.userObject)) {
            return root;
        }
        
        if (target < root.userObject) {
            return findNode(root.left, target);
        }
        
        return findNode(root.right, target);
    }
    
    static class NodeInfo {
        final BinaryNode<Integer> node;
        final int level;
        NodeInfo(BinaryNode<Integer> node, int level) {
            this.node = node;
            this.level = level;
        }
    }
}

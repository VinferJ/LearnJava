package tree.multiple;

import tree.binary.AbstractBinTree;
import tree.binary.AvlTree;
import tree.binary.TreeNode;

/**
 * @author Vinfer
 * @date 2021-01-12    15:03
 **/
public class Test {

    public static void main(String[] args) {
        MultiTreeNode root = new MultiTreeNode();
        int[] r = {1};
        root.setElements(r);
        MultiTreeNode level11 = new MultiTreeNode();
        int[] l11 = {3,8};
        level11.setElements(l11);
        MultiTreeNode level12 = new MultiTreeNode();
        int[] l12 = {23,28};
        level12.setElements(l12);
        MultiTreeNode level13 = new MultiTreeNode();
        int[] l13 = {39,50};
        level13.setElements(l13);
        root.setPointers(new MultiTreeNode[]{level11, level12, level13});
        IndexTree indexTree = new IndexTree(root);
        System.out.println(indexTree);
    }

}

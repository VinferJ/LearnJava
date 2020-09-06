import tree.BinTree;
import tree.BinaryTree;
import tree.TreeNode;

/**
 * @author Vinfer
 * @date 2020-09-05    11:10
 **/
public class TestBinTree {

    public static void main(String[] args) {
        String[] elements = {"v","i","n","f","e","r","j"};
        BinTree<String> binTree = new BinaryTree<>(elements);
        binTree.levelTraversal();
        binTree.preorderTraversal(binTree.getRoot());
        System.out.println();
        binTree.inorderTraversal(binTree.getRoot());
        System.out.println();
        binTree.postorderTraversal(binTree.getRoot());
        System.out.println();
        System.out.println(binTree.size());;
        TreeNode<String> root = binTree.getRoot();
        TreeNode<String> firstLeft = binTree.getLeftChild(root);
        TreeNode<String> secondRight = binTree.getRightChild(firstLeft);
        System.out.println(firstLeft.getEle());
        System.out.println(secondRight.getEle());
        System.out.println(binTree.getParent(secondRight) == firstLeft);
        binTree.addLeftChild(secondRight, "jeve");
        binTree.levelTraversal();
        System.out.println(binTree.size());
    }

}

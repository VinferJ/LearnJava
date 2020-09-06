package tree;

/**
 * @author Vinfer
 * @date 2020-09-05    10:32
 **/
public interface BinTree<T> {

    int treeHeight();

    int size();

    TreeNode<T> getRoot();

    TreeNode<T> getParent(TreeNode<T> treeNode);

    TreeNode<T> getLeftChild(TreeNode<T> treeNode);

    TreeNode<T> getRightChild(TreeNode<T> treeNode);

    void addLeftChild(TreeNode<T> currNode,T leftChildEle);

    void addRightChild(TreeNode<T> currNode,T rightChildEle);

    void deleteNode(TreeNode<T> treeNode);

    void updateNode(TreeNode<T> currNode,T ele);

    /**
     * 层序遍历二叉树
     */
    void levelTraversal();

    /**
     * 前序遍历二叉树
     * 先输出父节点，在遍历左子树，最后遍历右子树
     * @param treeNode      当前遍历的树节点
     */
    void preorderTraversal(TreeNode<T> treeNode);

    /**
     * 中序遍历二叉树
     * 先遍历左子树，再输出父节点，最后遍历右子树
     * @param treeNode      当前遍历的树节点
     */
    void inorderTraversal(TreeNode<T> treeNode);

    /**
     * 后序遍历二叉树
     * 先遍历左子树，在遍历右子树，最后输出父节点
     * @param treeNode      当前遍历的树节点
     */
    void postorderTraversal(TreeNode<T> treeNode);

}

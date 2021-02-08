package tree.binary;

/**
 * @author Vinfer
 * @date 2020-09-05    10:32
 **/
public interface BinTree<T> {

    /**
     * 获取树高
     * @return  返回二叉树的树高
     */
    int treeHeight();

    /**
     * 获取二叉树值不为null的节点个数
     * @return      返回不为null的节点数
     */
    int size();

    /**
     * 获取二叉树的根结点
     * @return      返回根结点
     */
    TreeNode<T> getRoot();

    /**
     * 获取指定树节点的的父节点
     * @param treeNode      指定的树节点
     * @return              返回该树节点的父节点
     */
    TreeNode<T> getParent(TreeNode<T> treeNode);

    /**
     * 获取指定树节点的左子树节点
     * @param treeNode      指定的树节点
     * @return              返回该树节点的左子树节点
     */
    TreeNode<T> getLeftChild(TreeNode<T> treeNode);

    /**
     * 获取指定树节点的右子树节点
     * @param treeNode      指定的树节点
     * @return              返回该树节点的右子树节点
     */
    TreeNode<T> getRightChild(TreeNode<T> treeNode);

    /**
     * 添加一个树节点
     * @param ele               树节点值
     */
    void addChild(T ele);

    /**
     * 删除指定的树节点
     * @param treeNode      需要删除的树节点
     */
    void deleteNode(TreeNode<T> treeNode);

    /**
     * 更新当前节点的节点值
     * @param currNode      当前节点
     * @param ele           新的节点值
     */
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

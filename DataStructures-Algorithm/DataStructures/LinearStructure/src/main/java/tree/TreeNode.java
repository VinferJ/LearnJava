package tree;

/**
 * 普通二叉树节点对象
 * 对象对外开放，但是关闭节点对象属性的直接操作
 * 包括setter和getter的操作，只开放对元素的值的操作
 * 操作TreeNode只能通过二叉树对象
 *
 * @author Vinfer
 * @date 2020-09-05    10:48
 **/
public class TreeNode<T> {

    protected T ele;
    protected TreeNode<T> parent;
    protected TreeNode<T> leftChild;
    protected TreeNode<T> rightChild;

    public TreeNode(T ele, TreeNode<T> parent, TreeNode<T> leftChild, TreeNode<T> rightChild) {
        this.ele = ele;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public TreeNode(){}

    public T getEle() {
        return ele;
    }

    public void setEle(T ele) {
        this.ele = ele;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "ele=" + ele +
                ", parent=" + parent +
                ", leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                '}';
    }
}

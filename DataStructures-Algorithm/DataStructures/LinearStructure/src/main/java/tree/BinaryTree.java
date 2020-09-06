package tree;

import queue.ArrayQueue;

/**
 * 普通的二叉树
 * @author Vinfer
 * @date 2020-09-05    15:54
 **/
public class BinaryTree<T> extends AbstractBinTree<T>{

    @SafeVarargs
    public BinaryTree(T...args){
        super(args);
    }

    @Override
    protected void buildBinTree(T[] elements) {
        /*
        * 按照层序遍历的方式创建一个普通的二叉树
        * */
        ArrayQueue<TreeNode<T>> queue = new ArrayQueue<>(elements.length * 2);
        queue.add(root);
        for (T element : elements) {
            //取出队头节点
            TreeNode<T> treeNode = queue.poll();

            /*初始化两个新的左右子树节点*/
            TreeNode<T> leftChild = new TreeNode<>();
            TreeNode<T> rightChild = new TreeNode<>();

            /*初始化子树节点的父节点*/
            leftChild.parent = treeNode;
            rightChild.parent = treeNode;

            /*将当前遍历的父节点的左右子树指向初始化的左右子树节点*/
            treeNode.leftChild = leftChild;
            treeNode.rightChild = rightChild;
            //节点值赋值
            treeNode.ele = element;

            /*将左右节点放入队列中，准备下一轮遍历*/
            queue.add(leftChild);
            queue.add(rightChild);
            //元素计数自增
            eleCount++;
        }
    }
}

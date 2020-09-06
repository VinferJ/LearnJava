package tree;

/**
 * 平衡二叉树：当新增树节点时，对树结构会进行自平衡
 * 平衡树：树的任意节点的两个子树的高度差<=1
 *
 * @author Vinfer
 * @date 2020-09-05    11:28
 **/
public class AvlTree<T> extends AbstractBinTree<T>{

    @Override
    protected void buildBinTree(T[] elements) {

    }

    @Override
    public void addLeftChild(TreeNode<T> currNode, T leftChildEle) {
        super.addLeftChild(currNode, leftChildEle);
        if(!isBalanced()){
            buildBalance();
        }
    }

    private void buildBalance(){}

    private boolean isBalanced(){
        //检查树是否平衡
        return true;
    }

}

package tree;

import queue.ArrayQueue;

/**
 * 抽象二叉树：拥有二叉树的基本特性和结构
 *
 * @author Vinfer
 * @date 2020-09-05    10:20
 **/
public abstract class AbstractBinTree<T> implements BinTree<T>{

    /*static class TreeNode<T>{
         T ele;
         TreeNode<T> parent;
         TreeNode<T> leftChild;
         TreeNode<T> rightChild;
         public TreeNode(){}
         public TreeNode(T ele,TreeNode<T> parent,TreeNode<T> leftChild,TreeNode<T> rightChild){
            this.ele = ele;
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }*/

    TreeNode<T> root;

    /**记录二叉树的树高*/
    int treeHeight;

    /**记录二叉树节点值不为null的节点个数*/
    int eleCount;

    /**
     * 构建二叉树对象
     * @param args  二叉树创建所需要的树节点值
     */
    @SafeVarargs
    public AbstractBinTree(T...args){
        this(new TreeNode<>(null,null,null,null),args);
    }

    /**
     * 构建二叉树对象
     * @param root  树的根结点
     * @param args  树节点值
     */
    @SafeVarargs
    public AbstractBinTree(TreeNode<T> root, T...args){
        if(args != null && args.length > 0){
            this.root = root;
            treeHeight = 1;
            buildBinTree(args);
        }else {
            throw new RuntimeException("Unable to build binary tree by null args");
        }

    }

    /**
     * 创建/初始化二叉树
     * @param elements      二叉树节点值
     */
    protected abstract void buildBinTree(T[] elements);

    public void setRoot(T ele){
        root.ele = ele;
    }

    @Override
    public TreeNode<T> getRoot(){
        return root;
    }

    @Override
    public int treeHeight() {
        return treeHeight;
    }

    @Override
    public int size() {
        return eleCount;
    }

    @Override
    public TreeNode<T> getParent(TreeNode<T> treeNode) {
        checkNull(treeNode);
        return treeNode.parent;
    }

    @Override
    public TreeNode<T> getLeftChild(TreeNode<T> treeNode) {
        checkNull(treeNode);
        return treeNode.leftChild;
    }

    @Override
    public TreeNode<T> getRightChild(TreeNode<T> treeNode) {
        checkNull(treeNode);
        return treeNode.rightChild;
    }

    @Override
    public void addLeftChild(TreeNode<T> currNode, T leftChildEle) {
        checkNull(currNode);
        TreeNode<T> leftChild = new TreeNode<>();
        leftChild.ele = leftChildEle;
        currNode.leftChild = leftChild;
        eleCount++;
        updateTreeHeight();
    }

    @Override
    public void addRightChild(TreeNode<T> currNode, T rightChildEle) {
        checkNull(currNode);
        TreeNode<T> rightChild = new TreeNode<>();
        rightChild.ele= rightChildEle;
        currNode.rightChild = rightChild;
        eleCount++;
        updateTreeHeight();
    }

    @Override
    public void deleteNode(TreeNode<T> treeNode) {
        checkNull(treeNode);
        //拿出父节点
        TreeNode<T> parent = treeNode.parent;
        /*
        * 如果删除的不是根结点，那么treeNode.parent一定不为空
        * */
        if(treeNode != root){
            if(treeNode.leftChild != null){
                if(parent.leftChild == treeNode){
                    //将被删除节点上浮成为自己的父节点
                    parent.leftChild = treeNode.leftChild;
                }else {
                    parent.rightChild = treeNode.leftChild;
                }
                /*
                * 处理被删除节点的右子树节点:
                *   1. 对上浮后的节点优先进行右子树的深度遍历，找到最深的一个节点
                *   2. 将遍历得到的节点的右子树节点指向该右子树节点
                * */
                TreeNode<T> left = treeNode.leftChild;
                left = getRightDeepestNode(left);
                left.rightChild = treeNode.rightChild;
            }else if (treeNode.rightChild != null){
                if(parent.leftChild == treeNode){
                    parent.leftChild = treeNode.rightChild;
                }else {
                    parent.rightChild = treeNode.rightChild;
                }
                /*
                * 由于进入该循环必定满足treeNode.leftChild == null
                * 因此这里不需要在考虑对该左子树节点进行连接
                * */
            }
        }else {
            if(root.leftChild == null){
                root = root.rightChild;
            }else if(root.rightChild == null){
                root = root.leftChild;
            }else {
                /*
                * 根结点的左右节点都不为空时：
                *   1. 对该左子树节点进行最深的右子树遍历
                *   2. 将遍历得到的节点的右子树执行初始根节点的右子树节点
                *   3. 根的左子树上浮成为根节点
                * */
                TreeNode<T> left = root.leftChild;
                left = getRightDeepestNode(left);
                left.rightChild = root.rightChild;
                root = root.leftChild;
            }
        }
        treeNode = null;
        eleCount--;
        updateTreeHeight();
    }

    TreeNode<T> getRightDeepestNode(TreeNode<T> treeNode){
        TreeNode<T> node = treeNode;
        while (node.rightChild != null){
            node = node.rightChild;
        }
        return node;
    }

    TreeNode<T> getLeftDeepestNode(TreeNode<T> treeNode){
        TreeNode<T> node = treeNode;
        while (node.leftChild != null){
            node = node.leftChild;
        }
        return node;
    }

    @Override
    public void updateNode(TreeNode<T> currNode, T ele) {
        checkNull(currNode);
        currNode.ele = ele;
    }

    @Override
    public void levelTraversal() {
        /*
        * 使用队列先进先出的特点来层序遍历二叉树
        * */
        ArrayQueue<TreeNode<T>> queue = new ArrayQueue<>(eleCount * 2);
        if(root != null){
            //先把根结点放到队列首位中
            queue.add(root);

            /*
            * 遍历二叉树
            * */
            while (!queue.queueIsEmpty()){
                /*
                * 现将当前队列中的头元素取出
                * */
                TreeNode<T> treeNode = queue.poll();
                /*
                * 如果该节点存在左子树或右子树
                * 那么将其添加到队列中，顺序是先左后右
                * 那么被入队孩子节点会根据入队顺序在下一轮遍历中出队
                * 每遍历出一个节点，都会将其左右子树节点顺序入队，那么可以保证
                * 所有节点肯定会被一层一层地入队，再一层一层地出队
                * */
                if(treeNode.leftChild != null){
                    queue.add(treeNode.leftChild);
                }
                if(treeNode.rightChild != null) {
                    queue.add(treeNode.rightChild);
                }
                //输出当前遍历节点的元素
                T ele = treeNode.ele;
                System.out.print(ele + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void preorderTraversal(TreeNode<T> treeNode) {
        if(treeNode != null){
            /*
            * 先根再左后右
            * */
            System.out.print(treeNode.ele==null?"":treeNode.ele+" ");
            preorderTraversal(treeNode.leftChild);
            preorderTraversal(treeNode.rightChild);
        }
    }


    @Override
    public void inorderTraversal(TreeNode<T> treeNode) {
        if(treeNode != null){
            /*
            * 先左再根后右
            * */
            inorderTraversal(treeNode.leftChild);
            System.out.print(treeNode.ele==null?"":treeNode.ele+" ");
            inorderTraversal(treeNode.rightChild);
        }
    }

    @Override
    public void postorderTraversal(TreeNode<T> treeNode) {
        if(treeNode != null){
            /*
            * 先左再右后根
            * */
            postorderTraversal(treeNode.leftChild);
            postorderTraversal(treeNode.rightChild);
            System.out.print(treeNode.ele==null?"":treeNode.ele+" ");
        }
    }

    private void checkNull(TreeNode<T> treeNode){
        if(treeNode == null){
            throw new RuntimeException("TreeNode is null");
        }
    }

    private void updateTreeHeight(){
        //TODO  普通二叉树的树高计算：遍历树节点
        treeHeight++;
    }



}

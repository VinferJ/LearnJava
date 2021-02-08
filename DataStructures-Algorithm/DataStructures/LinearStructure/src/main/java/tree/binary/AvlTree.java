package tree.binary;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 平衡二叉树：当新增树节点时，对树结构会进行自平衡
 * 平衡树：树的任意节点的两个子树的高度差<=1（平衡因子只能是-1,0,1）
 * 目前已实现插入时自平衡，删除时自平衡待实现
 *
 * @author Vinfer
 * @date 2020-09-05    11:28
 **/
public class AvlTree<T> extends AbstractBinTree<T>{

    private List<T> elements;

    private int leftTreeHeight = 0;

    private int rightTreeHeight = 0;

    public static AvlTree<Integer> build(Integer[] elements){
        AvlTree<Integer> integerAvlTree = new AvlTree<>();
        integerAvlTree.buildBinTree(elements);
        return integerAvlTree;
    }

    public static AvlTree<String> build(String[] elements){
        AvlTree<String> stringAvlTree = new AvlTree<>();
        stringAvlTree.buildBinTree(elements);
        return stringAvlTree;
    }

    public AvlTree(){
        this.elements = new ArrayList<>();
    }

    @Override
    protected void buildBinTree(T[] elements) {
        //每次构造树都是重新构建，因此需要将root和elements重置
        clear();
        //先排序再进行插入构建
        this.elements = Arrays.asList(elements.clone());
        sortElements();
        this.elements.forEach(this::insertTreeNode);
    }

    public void clear(){
        root = null;
        this.elements = null;
    }

    @Override
    public List<T> getElements() {
        return this.elements;
    }

    @Override
    public void addChild(T ele) {
        insertTreeNode(ele);
        addEleAndSort(ele);
    }

    @Override
    public void deleteNode(TreeNode<T> treeNode) {
        throw new RuntimeException("Can't remove a specified tree node in avl tree");
    }

    public boolean isExist(T ele){
        TreeNode<T> targetNode = getTargetNode(ele);
        return targetNode != null;
    }

    private TreeNode<T> getTargetNode(T ele){
        TreeNode<T> node = root;
        while (node != null && node.ele.hashCode() != ele.hashCode()){
            if (ele.hashCode() < node.ele.hashCode()){
                node = node.leftChild;
            }else {
                node = node.rightChild;
            }
        }
        return node;
    }
    
    public void deleteNode(T ele){
        TreeNode<T> delNode = getTargetNode(ele);
        if (delNode != null){
            this.elements.remove(ele);
            sortElements();
            if (ele != root.ele){
                removeNode(delNode);
            }else {
                //删除root的处理：找到新的root节点值 -> 删除替换节点的原始树节点 -> 替换root节点值
                T newRootEle = this.elements.get(((this.elements.size() - 1) / 2));
                delNode = getTargetNode(newRootEle);
                removeNode(delNode);
                //替换根结点值
                root.ele = newRootEle;
            }
        }
        /*
        * TODO  删除后如何检查树平衡？
        *       插入节点时检查树平衡可以通过插入节点进行回溯来判断平衡因子是否满足条件
        *       但是删除节点时无法使用回溯
        * */
    }

    private void removeNode(TreeNode<T> node){
        if (node.leftChild == null && node.rightChild == null){
            //是一个孤立的子树节点，直接从其父节点断开该节点
            TreeNode<T> parent = node.parent;
            if (parent.leftChild.ele.hashCode() == node.ele.hashCode()){
                parent.leftChild = null;
            }else if (parent.rightChild.ele.hashCode() == node.ele.hashCode()){
                parent.rightChild = null;
            }
        }else {
            //如果不是孤立的子树节点，那么总是优先用该节点的左子树进行替换
            if (node.leftChild != null){
                node.ele = node.leftChild.ele;
                node.leftChild = null;
            }else {
                node.ele = node.rightChild.ele;
                node.rightChild = null;
            }
        }
    }

    private void insertTreeNode(T ele){
        TreeNode<T> insertNode;
        if (root != null){
            insertNode = doTreeNodeInsert(ele);
        }else {
            root = new TreeNode<>(ele,null,null,null);
            treeHeight = 1;
            leftTreeHeight = treeHeight;
            rightTreeHeight = treeHeight;
            insertNode = root;
        }
        //每次插入新节点都需要检查当前的树结构是否平衡
        checkBalanced(insertNode);
    }

    private TreeNode<T> doTreeNodeInsert(T ele){
        TreeNode<T> insertedNode = root;
        TreeNode<T> parentNode = null;
        //最终插入节点是否为右子树
        boolean insertRight = true;
        //找到插入位置,初始插入的数据都是升序排序的，因此默认从右子树开始遍历寻找
        while (insertedNode != null){
            parentNode = insertedNode;
            if (ele.hashCode() >= insertedNode.ele.hashCode()){
                insertRight = true;
                insertedNode = insertedNode.rightChild;
            }else {
                insertRight = false;
                insertedNode = insertedNode.leftChild;
            }
        }
        //生成插入节点
        insertedNode = new TreeNode<>(ele, parentNode, null, null);
        //确定是插入为左子树还是右子树
        if (insertRight){
            //如果当前插入节点值大于等于根结点值说明插入位置在右侧子树，此时才可以增加右子树高度
            if (ele.hashCode() >= root.ele.hashCode()){
                rightTreeHeight++;
            }
            if (parentNode != null){
                parentNode.rightChild = insertedNode;
            }
        }else {
            //插入值小于根结点值才在左侧子树插入
            if (ele.hashCode() < root.ele.hashCode()){
                leftTreeHeight++;
            }
            parentNode.leftChild  = insertedNode;
        }
        return insertedNode;
    }

    private void buildBalance(boolean balancedRight,String balanceType,TreeNode<T> flipNode){
        int levelTwoDepth = 2;
        int levelThreeDepth = 3;
        //先取出翻折点的父树节点
        TreeNode<T> flipNodeParent = flipNode.parent;
        TreeNode<T> nodeAfterFlip = null;
        switch (balanceType){
            case "left":
                if (balancedRight && flipNode.rightChild.leftChild != null){
                    /*
                    * 这种情况属于RL型，需要先进行右翻折并替换然后进行左翻折：
                    *   a -> (b <- c )  a是turnoverNode，并满足c>b>a
                    *   这里需要进行右翻折并替换：
                    *       1. a -> c -> b  将b右翻折成为c的右子树
                    *       2. a -> b -> c  由于c>b，因此将b和c进行替换
                    *   右翻折并替换后的树节点再进行下面的左翻折即可完成平衡
                    * */
                    TreeNode<T> switchNode = new TreeNode<>(flipNode.rightChild.leftChild.ele,flipNode,null, null);
                    switchNode.rightChild = new TreeNode<>(flipNode.rightChild.ele, switchNode, null, null);
                    //help GC
                    flipNode.rightChild = null;
                    flipNode.rightChild = switchNode;
                }
                //进行左翻折(RR型)
                nodeAfterFlip = flipLeft(flipNode);
                break;
            case "right":
                if (!balancedRight && flipNode.leftChild.rightChild != null){
                    TreeNode<T> switchNode = new TreeNode<>(flipNode.leftChild.rightChild.ele,flipNode,null,null);
                    switchNode.leftChild = new TreeNode<>(flipNode.leftChild.ele, switchNode, null, null);
                    flipNode.leftChild = null;
                    flipNode.leftChild = switchNode;
                }
                //进行右翻折（LL型）
                nodeAfterFlip = flipRight(flipNode);
            default:break;
        }
        if (nodeAfterFlip != null && flipNodeParent != null){
            int nodeRightDepth = balancedRight? getNodeRightDepth(nodeAfterFlip) : getNodeLeftDepth(nodeAfterFlip);
            // TODO: 2021/1/14 这里直接用值来判断会导致添加重复值时有大概率会出现翻折bug
            if (nodeAfterFlip.ele.hashCode() != flipNode.ele.hashCode()){
                if (nodeRightDepth == levelTwoDepth){
                    if (balancedRight){
                        //这种情况是返回的树节点成为根结点的右子树节点
                        //help GC
                        flipNodeParent.rightChild = null;
                        flipNodeParent.rightChild = nodeAfterFlip;
                    }else {
                        flipNodeParent.leftChild = null;
                        flipNodeParent.leftChild = nodeAfterFlip;
                    }
                    nodeAfterFlip.parent = flipNodeParent;
                }
            }else {
                if (nodeRightDepth == levelTwoDepth){
                    //help GC
                    root = null;
                    //根结点没有左子树，可以直接成为新的根结点
                    root = nodeAfterFlip;
                }else if (nodeRightDepth == levelThreeDepth){
                    TreeNode<T> tempRoot = root;
                    if (balancedRight){
                        nodeAfterFlip.leftChild.parent = tempRoot;
                        //根结点有左子树，那么成为新的根结点后，需要连接该左子树
                        tempRoot.rightChild = nodeAfterFlip.leftChild;
                        nodeAfterFlip.parent = null;
                        nodeAfterFlip.leftChild = tempRoot;
                    }else {
                        nodeAfterFlip.rightChild.parent = tempRoot;
                        //连接旧根节点的右子树
                        tempRoot.leftChild = nodeAfterFlip.rightChild;
                        nodeAfterFlip.parent = null;
                        nodeAfterFlip.rightChild = tempRoot;
                    }
                    tempRoot.parent = nodeAfterFlip;
                    root = nodeAfterFlip;
                }
            }
        }else {
            root = nodeAfterFlip;
        }
        //重新计算树高
        updateTreeHeight();
    }

    /**
     * 树节点左翻折
     * @param flipNode      翻折树节点
     * @return              翻折完成的树节点
     */
    private TreeNode<T> flipLeft(TreeNode<T> flipNode){
        if (flipNode.leftChild == null && flipNode.parent != null){
            /*
            * 判断当前的右侧子树情况，下面的a为flipNode
            *   1. root -> a -> b -> c
            *   2. root -> a -> b
            * */
            TreeNode<T> centerNode;
            TreeNode<T> left;
            TreeNode<T> right;
            if (flipNode.rightChild.rightChild != null){
                //符合第一种情况，选取b为翻折中心点
                centerNode = flipNode.rightChild;
            }else {
                //符合第二种情况，选取a为翻折中心点
                centerNode = flipNode;
            }
            left = new TreeNode<>(centerNode.parent.ele,null,null, null);
            right = new TreeNode<>(centerNode.rightChild.ele, null, null, null);
            //进行左翻折，生成新的node
            TreeNode<T> node = new TreeNode<>(centerNode.ele, null, left, right);
            left.parent = node;
            right.parent = node;
            return node;
        }else {
            /*
            * 如果翻折点的父节点不为空，那么翻折时，翻折点会成为新的根结点
            * 此时直接返回即可
            * */
            return flipNode;
        }
    }

    /**
     * 树节点右翻折
     * @param flipNode      翻折的树节点
     * @return              翻折完成的树节点
     */
    private TreeNode<T> flipRight(TreeNode<T> flipNode){
        if (flipNode.rightChild == null && flipNode.parent != null){
            TreeNode<T> centerNode;
            TreeNode<T> left;
            TreeNode<T> right;
            if (flipNode.leftChild.leftChild != null){
                centerNode = flipNode.leftChild;
            }else {
                centerNode = flipNode;
            }
            left = new TreeNode<>(centerNode.leftChild.ele,null,null, null);
            right = new TreeNode<>(centerNode.parent.ele, null, null, null);
            //进行右翻折并生成新的node
            TreeNode<T> node = new TreeNode<>(centerNode.ele, null, left, right);
            left.parent = node;
            right.parent = node;
            return node;
        }else {
            return flipNode;
        }
    }

    /**
     * 树结构平衡性检查
     * @param insertedNode      当前插入的树节点
     */
    private void checkBalanced(TreeNode<T> insertedNode){
        /*
        * 树平衡的条件是：平衡因子只能是-1，0和1
        * 因此可以通过插入的树节点进行回溯来查看是否满足平衡条件
        * */
        int levelFactor = 1;
        int balancedBorder = 3;
        //插入的节点是否在根部右侧
        boolean insertedRight = insertedNode.ele.hashCode() >= root.ele.hashCode();
        while (insertedNode.parent != null){
            insertedNode = insertedNode.parent;
            levelFactor++;
            if (levelFactor == balancedBorder){
                break;
            }
        }
        if (levelFactor < balancedBorder){
            return;
        }
        if (insertedNode.leftChild == null){
            //翻折类型是向左翻折
            buildBalance(insertedRight,"left",insertedNode == root? insertedNode.rightChild : insertedNode);
            return;
        }else {
            //如果左子树不为空，需要判断根部左侧子树的高度是否大于2/大于等于3（因此此时右侧子树高度为4）
            if (insertedNode.rightChild != null &&(leftTreeHeight - rightTreeHeight) > 1){
                buildBalance(insertedRight,"left", insertedNode);
                return;
            }
        }
        if (insertedNode.rightChild == null){
            //翻折类型是向右翻折
            buildBalance( insertedRight,"right",insertedNode == root? insertedNode.leftChild : insertedNode);
        }else {
            if ((rightTreeHeight - leftTreeHeight) > 1){
                buildBalance(insertedRight,"right", insertedNode);
            }
        }
    }

    /**
     * 向保存树节点值集合新增元素并排序
     * @param ele       新增的元素
     */
    private void addEleAndSort(T ele){
        this.elements.add(ele);
        sortElements();
    }

    /**
     * 对保存树节点值的集合进行排序
     */
    private void sortElements(){
        if (this.elements != null && this.elements.size() > 1){
            this.elements = this.elements.stream().sorted((ele1,ele2) -> {
                if (ele1.hashCode() == ele2.hashCode()){
                    return ele1.hashCode();
                }else {
                    return Integer.compare(ele1.hashCode(), ele2.hashCode());
                }
            }).collect(Collectors.toList());
        }
    }

    /**
     * 获取指定节点向右子树递进的最大深度
     * @param node      指定的/开始递进的树节点
     * @return          向右递进的最大深度
     */
    private int getNodeRightDepth(TreeNode<T> node){
        TreeNode<T> temp = node;
        int depth = 0;
        while (temp != null){
            temp = temp.rightChild;
            depth++;
        }
        return depth;
    }

    /**
     * 获取指定节点向左子树递进的最大深度
     * @param node      指定的/开始递进的树节点
     * @return          向左递进的最大深度
     */
    private int getNodeLeftDepth(TreeNode<T> node){
        TreeNode<T> temp = node;
        int depth = 0;
        while (temp != null){
            temp = temp.leftChild;
            depth++;
        }
        return depth;
    }

    /**
     * 更新树高
     */
    private void updateTreeHeight(){
        try {
            //计算两侧树高需要分别克隆两个根节点进行计算
            TreeNode<T> cloneForRight = root.clone();
            TreeNode<T> cloneForLeft = root.clone();
            this.rightTreeHeight = updateRightTreeHeight(cloneForRight,0);
            this.leftTreeHeight = updateLeftTreeHeight(cloneForLeft,0 );
            treeHeight = Math.max(this.leftTreeHeight, this.rightTreeHeight);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新根部右侧子树的树高
     * @param clonedRootNode        克隆的树根结点
     * @param rightTreeHeight       初始右侧树高，一般为0
     * @return                      右侧子树树高
     */
    private int updateRightTreeHeight(TreeNode<T> clonedRootNode,int rightTreeHeight){

        // TODO: 2021/1/14 优化树高的更新算法，此处由于存在节点递归，因此当树节点数过多时，会出现栈溢出导致无法计算树高（目前最高只能支持5w数据）

        if (clonedRootNode != null){
            if (clonedRootNode.rightChild != null){
                //计算右侧深度，必须先从右侧开始
                return updateRightTreeHeight(clonedRootNode.rightChild,++rightTreeHeight);
            }
            //回溯，计算左子树深度
            return updateRightTreeHeight(clonedRootNode.leftChild, ++rightTreeHeight);
        }else {
            return rightTreeHeight;
        }
    }

    /**
     * 更新根部左侧子树树高
     * @param clonedRootNode        克隆的树根节点
     * @param leftTreeHeight        初始左侧树高，一般为0
     * @return                      左侧子树树高
     */
    private int updateLeftTreeHeight(TreeNode<T> clonedRootNode,int leftTreeHeight){
        if (clonedRootNode != null){
            if (clonedRootNode.leftChild != null){
                //计算左侧深度，必须先从左侧开始
                return updateLeftTreeHeight(clonedRootNode.leftChild,++leftTreeHeight);
            }
            //回溯，计算右子树深度
            return updateLeftTreeHeight(clonedRootNode.rightChild, ++leftTreeHeight);
        }else {
            return leftTreeHeight;
        }
    }

}

package tree.multiple;

/**
 * B树 / B-Tree
 *
 * @author Vinfer
 * @date 2021-01-12    14:55
 **/
public class IndexTree {

    public IndexTree(int[] elements){
        this.root = new MultiTreeNode();
        this.root.setElements(elements);
    }

    public IndexTree(MultiTreeNode treeNode){
        this.root = treeNode;
    }

    private final MultiTreeNode root;

    private int rank;

    private int treeHeight;

    @Override
    public String toString() {
        return "IndexTree{" +
                "root=" + root +
                ", rank=" + rank +
                ", treeHeight=" + treeHeight +
                '}';
    }
}

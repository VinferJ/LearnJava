package tree.multiple;

import java.util.Arrays;

/**
 * @author Vinfer
 * @date 2021-01-12    15:00
 **/
public class MultiTreeNode {

    private MultiTreeNode[] pointers;

    private int[] elements;

    public void setElements(int[] elements) {
        this.elements = elements;
    }

    public void setPointers(MultiTreeNode[] pointers) {
        this.pointers = pointers;
    }

    public int[] getElements() {
        return elements;
    }

    public MultiTreeNode[] getPointers() {
        return pointers;
    }

    @Override
    public String toString() {
        return "MultiTreeNode{" +
                "pointers=" + Arrays.toString(pointers) +
                ", elements=" + Arrays.toString(elements) +
                '}';
    }
}

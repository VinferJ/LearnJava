package dianchu.approve;

/**
 * @author vinfer
 * @date 2021-03-10 15:48
 */
public class ProcessingChain {

    Node root;

    static class Node{
        ProcessingPoint point;
        ProcessingPoint next;
        ProcessingPoint prev;
    }

    public ProcessingPoint getRoot() {
        return root.point;
    }

    public ProcessingPoint getNext(ProcessingPoint point){
        return null;
    }

    public ProcessingPoint getPrev(ProcessingPoint point){
        return null;
    }

}

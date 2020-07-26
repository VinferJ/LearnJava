package queue;


import list.BidirectionalLinkedList;

/**
 * @author Vinfer
 * @description         队列-基于链表实现
 * @date 2020-07-20  23:01
 **/
public class LinkedQueue<E> extends BidirectionalLinkedList {

    static class Node<E>{
        Node<E> front;
        Node<E> rear;

    }

}

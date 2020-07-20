package list;

import javax.xml.soap.Node;

/**
 * @author Vinfer
 * @description     双向链表
 * @date 2020-07-20  03:23
 **/
public class BidirectionalLinkedList<E>{


    static class Node<E>{
        Node<E> prev;
        Node<E> next;
        E element;
        Node(Node<E>prev,E ele,Node<E>next){
            this.prev=prev;
            this.element=ele;
            this.next=next;
        }
        Node(E ele){
            element=ele;
        }
        Node(){}
    }

    Node<E> head;

    Node<E> tail;

    int size;

    public void add(E ele){
        linkLast(ele);
    }

    public void addFirst(E ele){
        linkFirst(ele);
    }


    private void linkFirst(E ele){
        Node<E> newNode = new Node<>(null,ele,null);
        if(head==null){
            head = newNode;
            tail = head;
        }else{
            Node<E> firstNode = head;
            head=newNode;
            head.next=firstNode;
            firstNode.prev=head;
        }
        size++;
    }

    void linkLast(E ele){
        Node<E> newNode = new Node<>(null,ele,null);
        if(head==null){
            head = newNode;
            tail = head;
        }else{
            //获取尾结点
            Node<E> lastNode = tail;
            /*
            * 更新尾结点：将尾结节点指向新节点
            * 并且将新的尾结点的prev指向旧的尾节点
            * */
            tail = newNode;
            tail.prev=lastNode;
            //将旧的尾结点的next指向新的尾结点
            lastNode.next=tail;
        }
        size++;
    }

    /**
     * 链表变量，默认遍历顺序为：head->tail
     */
    public void iterate(){
        if(!isEmpty()){
            Node<E> node=head;
            while (true){
                System.out.println(node.element);
                if(hasNext(node)){
                    node=node.next;
                }else{
                    break;
                }
            }
        }else{
            throw new RuntimeException("LinkedList is empty");
        }
    }

    public void iterateByDesc(){
        if(!isEmpty()){
            Node<E> node = tail;
            while (true){
                System.out.println(node.element);
                if(hasPrev(node)){
                    node=node.prev;
                }else{
                    break;
                }
            }
        }
    }

    private boolean isEmpty(){
        return size==0;
    }

    private boolean hasNext(Node<E> node){
        return node.next!=null;
    }

    private boolean hasPrev(Node<E> node){
        return node.prev!=null;
    }

}

package linkedlist;

import list.SingleLinkedList;

import java.util.Stack;

/**
 * @author Vinfer
 * @date 2020-07-23  20:15
 **/
public class ListReverse {


    public static void main(String[] args) {

        /*
        * 1. 求链表节点个数
        * 2. 查找链表中倒数第k个节点
        * 3. 单链表的翻转
        * 4. 逆序打印单链表（尾部遍历/使用栈）
        * 5. 合并两个有序链表，合并后依然是有序的
        *
        * */


        GenericListNode head = new GenericListNode(1);
        GenericListNode listNode = head;
        for (int i = 2; i <= 6 ; i++) {
            listNode.next = new GenericListNode(i);
            listNode = listNode.next;
        }
        GenericListNode reverseListNode = reverseList(head);
        if (reverseListNode != null){
            System.out.println(reverseListNode.val);
        }
    }

    public static GenericListNode reverseList(GenericListNode head){
        if (head == null){
            return null;
        }
        // 逆序都可以借助Stack来完成
        Stack<GenericListNode> nodeStack = new Stack<>();
        GenericListNode node = head;
        while (node != null){
            nodeStack.add(node);
            node = node.next;
        }
        GenericListNode newHeadNode = nodeStack.pop();
        GenericListNode temp = newHeadNode;
        while (!nodeStack.empty()){
            temp.next = nodeStack.pop();
            temp = temp.next;
        }
        temp.next = null;
        return newHeadNode;
    }


}

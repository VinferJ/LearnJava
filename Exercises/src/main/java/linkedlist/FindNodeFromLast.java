package linkedlist;

/**
 * 寻找倒数第k个节点
 * 例如有链表：1 -> 2 -> 3 -> 4 -> 5 -> 6 k=2
 * 那么应该返回 4 -> 5
 *
 * 最简单粗暴的解题思路：
 *  先遍历一遍链表，拿到链表的真实长度，再计算出倒数节点开始的位置然后拿到倒数的节点
 *
 * @author Vinfer
 * @date 2021-02-24    16:24
 **/
public class FindNodeFromLast {

    public static void main(String[] args) {
        GenericListNode head = new GenericListNode(1);
        GenericListNode listNode = head;
        for (int i = 2; i <= 5; i++) {
            listNode.next = new GenericListNode(i);
            listNode = listNode.next;
        }
        GenericListNode targetNodes = solution(head, 2);
        if (targetNodes != null){
            System.out.println(targetNodes.val);
        }
    }

    public static GenericListNode solution(GenericListNode head, int k){
        int len = 0;
        GenericListNode node = head;
        // 遍历计算链表长度
        while (node != null){
            node = node.next;
            len++;
        }
        GenericListNode targetNode = head;
        // 遍历找到倒数起点的位置
        for (int i = 0; i < len - k; i++) {
            targetNode = targetNode.next;
        }
        return targetNode;
    }

}

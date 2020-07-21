import list.BidirectionalLinkedList;
import list.UnidirectionalLinkedList;
import org.junit.Test;

import java.util.LinkedList;

/**
 * @author Vinfer
 * @date 2020-07-14  02:03
 **/
public class TestLinkedList {

    @Test
    public void testBidList(){
        BidirectionalLinkedList<Integer> bidList = new BidirectionalLinkedList<>();
        BidirectionalLinkedList<Integer> bidList2 = new BidirectionalLinkedList<>();
        for (int i = 0; i < 5; i++) {
            bidList.add(i);
        }
        //0 1 2 3 4
        bidList.iterate();
        //4 3 2 1 0
        bidList.iterateByDesc();
        for (int i = 90; i <95 ; i++) {
            bidList2.addFirst(i);
        }
        //94 93 92 91 90
        bidList2.iterate();
        //90 91 92 93 94
        bidList2.iterateByDesc();
        bidList.delete(0);
        //1 2 3 4
        bidList.iterate();
        bidList.addFirst(999);
        bidList.delete(4);
        //999 1 2 3
        bidList.iterate();
        bidList.add(5656);
        bidList.delete(2);
        //999 1 3 5656
        bidList.iterate();
        bidList.add(0,996);
        //996 999 1 3 5656
        bidList.iterate();
        bidList.add(4, 777);
        //996 999 1 3 777 5656
        bidList.iterate();
        //996 999 8989 1 3 777 5656
        bidList.add(2,8989);
        bidList.iterate();
    }

    @Test
    public void testUniList(){
        UnidirectionalLinkedList<Integer> uList = new UnidirectionalLinkedList<>();
        for (int i = 5; i >0; i--) {
            uList.addFirst(i);
        }
        uList.reverse();
        //5 4 3 2 1
        uList.iterate();
        System.out.println(uList.get(0));
        uList.delete(0);
        //4 3 2 1
        uList.iterate();
        uList.delete(3);
        //4 3 2
        uList.iterate();
        uList.set(0,666);
        //666 3 2
        uList.iterate();
        uList.add(2, 998);
        //666 3 998 2
        uList.iterate();
        uList.add(0, 996);
        //996 666 3 998 2
        uList.iterate();
        uList.delete(2);
        //996 666 998 2
        uList.iterate();
    }

    @Test
    public void testLinkedList(){
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(0, 666);
        linkedList.add(0, 667);
        linkedList.add(1, 668);
        System.out.println(linkedList);
    }

}

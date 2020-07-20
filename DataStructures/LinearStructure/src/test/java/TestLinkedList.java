import list.BidirectionalLinkedList;
import list.UnidirectionalLinkedList;
import org.junit.Test;

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
        bidList.iterate();
        bidList.iterateByDesc();
        for (int i = 90; i <95 ; i++) {
            bidList2.addFirst(i);
        }
        bidList2.iterate();
        bidList2.iterateByDesc();
    }

    @Test
    public void testUniList(){
        UnidirectionalLinkedList<Integer> uList = new UnidirectionalLinkedList<>();
        for (int i = 5; i >0; i--) {
            uList.addFirst(i);
        }
        uList.reverse();
        uList.iterate();
        System.out.println(uList.get(0));
    }

}

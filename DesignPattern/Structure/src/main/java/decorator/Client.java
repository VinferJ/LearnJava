package decorator;

import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    16:24
 * @description
 **/
public class Client {


    public static void main(String[] args) {
        AbstractComponent component = new OriginalComponent();
        /*
        * 通过装饰器进行组件装饰
        * 可以动态的增加或者减少装饰器，不同的装饰器都可以动态扩展定制
        * 装饰的顺序是不断从里向外向外包装，因此下面方法执行的顺序应该是：tow -> one -> original
        * 相比较传统的直接通过继承实现特定的装饰，传统方式很容易就让原始组件产生类膨胀（当继承类变得很多后）
        * 而使用装饰器模式则很好地解决了类膨胀问题，可以动态选择增加或减少装饰器（本质是继承的另一次更好的解决方案，实现的仍是is-a的关系）
        * 并且装饰器类和被装饰的组件类可以相互独立发展，不会受彼此影响（因为不会耦合），双方都无需知道彼此
        *
        * 但是装饰模式也是有缺点的：
        *   使用装饰模式一定不要进行过多层数的装饰，即需要控制好装饰的数量
        *   因为如果后面出现问题时排查会非常困难，就像剥洋葱一样，一层又一层，最后发现是最里层出现了问题，徒增工作量
        * Tip：尽可能降低装饰的数量，以降低系统的复杂度
        *
        * */
        //第一层装饰（最内层）
        component = new DecoratorOne(component);

        //...中间还可以选择动态增加N层装饰

        //第二层装饰（最外层）
        component = new DecoratorTow(component);
        component.operate();

        MyList<String> list = new MyList<String>();
        list.add("has");
        list.add("jack");
        list.add("john");
        Iterator<String> iterator = list.iterator();
        //iterator can be only iterated once...
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }


    static class MyList<E>{

        static class Node<E>{
            Node<E> next;
            E ele;
            public Node(Node<E> next,E ele){
                this.next = next;
                this.ele = ele;
            }
        }

        Node<E> head;

        int size = 0;

        private Node<E> findNode(int p){
            Node<E> temp = head;
            for (int i = 0; i < p; i++) {
                temp = temp.next;
            }
            return temp;
        }

        public void add(E ele){
            if (head != null){
                Node<E> last = findNode(size - 1);
                last.next = new Node<E>(null, ele);
            }else {
                head = new Node<E>(null, ele);
            }
            size++;
        }

        public Iterator<E> iterator(){
            return new Itr();
        }

        private class Itr implements Iterator<E>{

            int position = 0;

            Itr(){}

            public boolean hasNext() {
                //iterator can only be iterated once, so when the position is equals with the size, it can't be iterated again
                return size > position;
            }

            public E next() {
                E ele = findNode(position).ele;
                //move position when one element is benn taken
                position++;
                return ele;
            }

            public void remove() {
                //remove the last node
                Node<E> beforeLast = findNode(size - 1);
                beforeLast.next.next = beforeLast.next;
                beforeLast.next = null;
                size--;
            }
        }

    }


}

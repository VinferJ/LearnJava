package prototype;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    09:36
 * @description
 **/
public class Client {

    public static void main(String[] args) {
        shallowCopy();
        System.out.println("==============================");
        deepCopy();
    }

    static void shallowCopy(){
        ShallowCopy shallowCopy = new ShallowCopy();
        shallowCopy.setValue("张三");
        shallowCopy.setNum(100);
        shallowCopy.setStr("prototype");
        ShallowCopy shallowCopyByCloning = null;
        try {
            shallowCopyByCloning = shallowCopy.clone();
            shallowCopyByCloning.setValue("李四");
            shallowCopyByCloning.setNum(999);
            /*
             * String本身没有clone方法，因为保存值的数组是被final所修饰的，也无法被clone
             * 但是String类型在拷贝时的处理与基本类型一致，会产生新的副本（常量池保存）
             * */
            shallowCopyByCloning.setStr("clone");
            shallowCopy.printInfo();
            shallowCopyByCloning.printInfo();
            System.out.println(shallowCopy+"\n"+shallowCopyByCloning);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    static void deepCopy(){
        DeepCopy deepCopy = new DeepCopy();
        deepCopy.setValue("张三");
        try {
            DeepCopy clone1 = deepCopy.clone();
            DeepCopy clone2 = deepCopy.clone();
            clone1.setValue("李四");
            clone2.setValue("王五");
            /*
            * 深拷贝后的对象内部的数组或引用对象也会被拷贝
            * 被拷贝的成员变量会指向新的内存地址，也会带上原型对象中包含的值（数组或集合或队列、栈）
            * */
            print(deepCopy,clone1,clone2);
            clone1.clearValue();
            print(deepCopy,clone1,clone2);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    static void print(DeepCopy...objs){
        for (DeepCopy obj : objs) {
            obj.printInfo();
        }
    }

}

package javase;

/**
 * @author vinfer
 * @date 2021-03-16 17:17
 */
public class ParamTransfer {


    static class Person{
        private String name;
        public int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) {
        String str = "abc";
        appendStr1(str);
        //MY-NOTE 在java中实际上没有引用传递这一说法，因为java没有指针，所以java中只有值传递
        // 方法参数中的引用类型本质也是值传递，只不过传递的是该变量引用地址（对应C++中的指针）的值
        // 并且不管是那种语言，值传递都是传递对变量拷贝的副本，也就是不同方法中的同一个参数，是相互隔离的
        // 因此下面的str变量虽然在append方法中修改了引用指向，但是append方法中的str其实是main方法中的str的一个副本
        // 因此在main中输出str还是"abc"，而在append方法中输出str则是修改后的字符串"abcdef"
        // 除了传递的是拷贝的副本还有一个很重要的原因：String存储值的char数组是由final修饰的，是不可变的
        // 所以在main中如果不直接对str进行修改，那么是不会影响到main中的str的
        System.out.println("main: "+str);
        appendStr2(str);
        System.out.println("main: "+str);
        //MY-NOTE 这里已经对main中的str做出修改，也就是将str对象的引用修改为了方法的返回值
        // 所以这里再输出str那么值自然就是"abcdef"
        str = appendStr3(str);
        System.out.println("main: "+str);
        Person person = new Person();
        person.setName("jack");
        person.age = 10;
        System.out.println("main: "+person);
        changeValue(person);
        //MY-NOTE 通过一个自定义的对象可以在此印证java中只有值传递以及传递的是拷贝值了
        // 这里虽然将person引用传了过去，并且副本里的引用指向了新的person对象，也设置了新的值
        // 但是最终在main方法中的person对象的引用并不会被改变，这就是因为传递的是拷贝的变量
        // 拷贝变量被修改值（引用类型对应的就是被修改引用）是不会影响到原变量的值的
        System.out.println("main: "+person);
    }

    public static void changeValue(Person person){
        person = new Person();
        person.setName("andy");
        person.age = 105;
    }

    static void appendStr1(String str){
        str += "def";
    }

    static void appendStr2(String str){
        str += "def";
        System.out.println("func: "+str);
    }

    static String appendStr3(String str){
        return str + "def";
    }

}

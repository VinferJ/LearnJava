package javase;

/**
 * @author Vinfer
 * @date 2021-02-24    02:04
 **/
public class Test2 {

    public static void main(String[] args) {
        System.out.println(new B().getValue());
        String str = "111";
    }
    static class A {
        protected int value;
        public A (int v) {
            setValue(v);
        }
        public void setValue(int value) {
            this.value= value;
        }
        public int getValue() {
            try {
                value ++;
                return value;
            } finally {
                this.setValue(value);
                System.out.println(value);
            }
        }
    }
    static class B extends A {
        public B () {
            super(5);
            setValue(getValue()- 3);
        }
        @Override
        public void setValue(int value) {
            super.setValue(2 * value);
        }
    }

}

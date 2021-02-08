package prototype;

import java.util.ArrayList;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    09:37
 * @description
 **/
public class ShallowCopy implements Cloneable{

    private final ArrayList<String> nameList = new ArrayList<>();

    private int num;

    private String str;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setValue(String name){
        nameList.add(name);
    }

    public void printInfo(){
        System.out.println(nameList);
        System.out.println(num);
        System.out.println(str);
    }

    @Override
    protected ShallowCopy clone() throws CloneNotSupportedException {
        ShallowCopy shallowCopy = null;
        shallowCopy = (ShallowCopy) super.clone();
        return shallowCopy;
    }
}

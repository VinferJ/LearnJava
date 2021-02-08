package prototype;

import java.util.ArrayList;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    10:02
 * @description
 **/
public class DeepCopy implements Cloneable{

    private ArrayList<String> nameList = new ArrayList<>();

    public void setValue(String name){
        nameList.add(name);
    }

    public void clearValue(){
        nameList.clear();
    }

    public void printInfo(){
        System.out.println(nameList);
    }

    @Override
    protected DeepCopy clone() throws CloneNotSupportedException {
        /*
        * 要进行clone，就要记住一定不要用new
        * */
        DeepCopy deepCopy = null;
        deepCopy = (DeepCopy) super.clone();
        this.nameList = (ArrayList<String>) this.nameList.clone();
        return deepCopy;
    }
}

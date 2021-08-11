package dutychain;

/**
 * @author Jiang wenfa
 * @date 2021-07-27 20:57
 */
public class Param {

    private String val;

    public Param(){
        this.val = "param";
    }

    public void setVal(String val) {
        this.val += val;
    }

    public String getVal() {
        return val;
    }
}

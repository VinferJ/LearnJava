package builder.model;

import java.util.ArrayList;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    15:36
 * @description
 **/
public abstract class AbstractCarModel {

    private ArrayList<String> sequence;
    private static final String START = "start";
    private static final String STOP = "stop";
    private static final String ALARM = "alarm";

    /**
     * 汽车启动
     */
    public abstract void start();

    /**
     * 汽车停车
     */
    public abstract void stop();

    /**
     * 汽车鸣笛
     */
    public abstract void alarm();

    public void setSequence(ArrayList<String> sequence){
        this.sequence = sequence;
    }

    public void run(){
        //通过加入的顺序来run一个carModel
        if (sequence != null && !sequence.isEmpty()){
            for (String sq : sequence) {
                switch (sq){
                    case START:
                        this.start();break;
                    case STOP:
                        this.stop();break;
                    case ALARM:
                        this.alarm();break;
                    default:break;
                }
            }
        }else {
            defaultRun();
        }
    }

    public void defaultRun(){
        this.start();
        this.alarm();
        this.stop();
    }

}
